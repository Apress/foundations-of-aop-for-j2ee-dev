/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package aop.j2ee.business.session.txcontroller;

import java.sql.*;
import javax.sql.*;
import java.util.*;
import java.math.*;
import javax.ejb.*;
import javax.naming.*;

import aop.j2ee.business.entity.tx.Tx;
import aop.j2ee.business.entity.tx.TxHome;

import java.rmi.RemoteException;
import aop.j2ee.business.entity.account.AccountHome;
import aop.j2ee.business.entity.account.Account;
import aop.j2ee.commons.exception.*;
import aop.j2ee.commons.util.*;
import aop.j2ee.commons.to.*;
import aop.j2ee.business.util.EJBGetter;

public class TxControllerBean implements SessionBean {

  private TxHome txHome;
  private AccountHome accountHome;
  private Connection con;
  private SessionContext context;
  private BigDecimal bigZero= new BigDecimal("0.00");

  public ArrayList getTxsOfAccount(
    java.util.Date startDate,
    java.util.Date endDate,
    String accountId)
    throws InvalidParameterException {

    Debug.print("TxControllerBean  getTxsOfAccount");

    Collection txIds;
    ArrayList txList= new ArrayList();

    if (startDate == null)
      throw new InvalidParameterException("null startDate");

    if (endDate == null)
      throw new InvalidParameterException("null endDate");

    if (accountId == null)
      throw new InvalidParameterException("null accountId");

    try {
      txIds= txHome.findByAccountId(startDate, endDate, accountId);
    } catch (Exception ex) {
      return txList;
    }

    try {
      Iterator i= txIds.iterator();
      while (i.hasNext()) {
        Tx tx= (Tx)i.next();
        TxDetails txDetail= tx.getDetails();
        txList.add(txDetail);
      }
    } catch (RemoteException ex) {
      throw new EJBException("getTxsOfAccount: " + ex.getMessage());
    }

    return txList;

  } // getTxsOfAccount

  public TxDetails getDetails(String txId)
    throws TxNotFoundException, InvalidParameterException {

    Debug.print("TxControllerBean getDetails");

    if (txId == null)
      throw new InvalidParameterException("null txId");

    try {
      Tx tx= txHome.findByPrimaryKey(txId);
      return tx.getDetails();
    } catch (Exception ex) {
      throw new TxNotFoundException(txId);
    }

  } // getDetails

  public void withdraw(BigDecimal amount, String description, String accountId)
    throws
      InvalidParameterException,
      AccountNotFoundException,
      IllegalAccountTypeException,
      InsufficientFundsException {

    Debug.print("TxControllerBean withdraw");

    // a changer!!!
    // check independant de la resolution d'un compte
    Account account= checkAccountArgsAndResolve(amount, description, accountId);

    try {

      String type= account.getType();
      if (DomainUtil.isCreditAccount(type))
        throw new IllegalAccountTypeException(type);

      BigDecimal newBalance= account.getBalance().subtract(amount);

      if (newBalance.compareTo(bigZero) == -1)
        throw new InsufficientFundsException();

      //account.setBalance(newBalance);

      executeTx(amount.negate(), description, accountId, newBalance, account);
    } catch (RemoteException ex) {
      throw new EJBException("withdraw: " + ex.getMessage());
    }

  } // withdraw

  public void deposit(BigDecimal amount, String description, String accountId)
    throws
      InvalidParameterException,
      AccountNotFoundException,
      IllegalAccountTypeException {

    Debug.print("TxControllerBean deposit");

    Account account= checkAccountArgsAndResolve(amount, description, accountId);

    try {
      String type= account.getType();
      if (DomainUtil.isCreditAccount(type))
        throw new IllegalAccountTypeException(type);

      BigDecimal newBalance= account.getBalance().add(amount);
      executeTx(amount, description, accountId, newBalance, account);
    } catch (RemoteException ex) {
      throw new EJBException("deposit: " + ex.getMessage());
    }

  } // deposit

  public void makeCharge(
    BigDecimal amount,
    String description,
    String accountId)
    throws
      InvalidParameterException,
      AccountNotFoundException,
      IllegalAccountTypeException,
      InsufficientCreditException {

    Debug.print("TxControllerBean charge");

    Account account= checkAccountArgsAndResolve(amount, description, accountId);

    try {
      String type= account.getType();
      if (DomainUtil.isCreditAccount(type) == false)
        throw new IllegalAccountTypeException(type);

      BigDecimal newBalance= account.getBalance().add(amount);

      if (newBalance.compareTo(account.getCreditLine()) == 1)
        throw new InsufficientCreditException();

      executeTx(amount, description, accountId, newBalance, account);
    } catch (RemoteException ex) {
      throw new EJBException("makeCharge: " + ex.getMessage());
    }

  } // charge

  public void makePayment(
    BigDecimal amount,
    String description,
    String accountId)
    throws
      InvalidParameterException,
      AccountNotFoundException,
      IllegalAccountTypeException {

    Debug.print("TxControllerBean makePayment");

    Account account= checkAccountArgsAndResolve(amount, description, accountId);

    try {
      String type= account.getType();
      if (DomainUtil.isCreditAccount(type) == false)
        throw new IllegalAccountTypeException(type);

      BigDecimal newBalance= account.getBalance().subtract(amount);
      executeTx(amount.negate(), description, accountId, newBalance, account);
    } catch (RemoteException ex) {
      throw new EJBException("makePayment: " + ex.getMessage());
    }

  } // makePayment

  javax.transaction.UserTransaction ut;

  public void transferFunds(
    BigDecimal amount,
    String description,
    String fromAccountId,
    String toAccountId)
    throws
      InvalidParameterException,
      AccountNotFoundException,
      InsufficientFundsException,
      InsufficientCreditException {

    Account fromAccount;
    Account toAccount;

    fromAccount= checkAccountArgsAndResolve(amount, description, fromAccountId);
    toAccount= checkAccountArgsAndResolve(amount, description, toAccountId);

    ut= context.getUserTransaction();

    try {
      ut.begin();
    } catch (Exception e) {
      throw new EJBException("transferFunds: " + e.getMessage());
    }

    try {

      String fromType= fromAccount.getType();
      BigDecimal fromBalance= fromAccount.getBalance();

      if (DomainUtil.isCreditAccount(fromType)) {
        BigDecimal fromNewBalance= fromBalance.add(amount);
        if (fromNewBalance.compareTo(fromAccount.getCreditLine()) == 1)
          throw new InsufficientCreditException();
        executeTx(
          amount,
          description,
          fromAccountId,
          fromNewBalance,
          fromAccount);
      } else {
        BigDecimal fromNewBalance= fromBalance.subtract(amount);
        if (fromNewBalance.compareTo(bigZero) == -1)
          throw new InsufficientFundsException();
        executeTx(
          amount.negate(),
          description,
          fromAccountId,
          fromNewBalance,
          fromAccount);
      }

      String toType= toAccount.getType();
      BigDecimal toBalance= toAccount.getBalance();

      if (DomainUtil.isCreditAccount(toType)) {
        BigDecimal toNewBalance= toBalance.subtract(amount);
        executeTx(
          amount.negate(),
          description,
          toAccountId,
          toNewBalance,
          toAccount);
      } else {
        BigDecimal toNewBalance= toBalance.add(amount);
        executeTx(amount, description, toAccountId, toNewBalance, toAccount);
      }

      ut.commit();

    } catch (Exception ex) {
      try {
        ut.rollback();
      } catch (Exception e) {
        throw new EJBException("transferFunds: " + e.getMessage());
      }
      throw new EJBException("transferFunds: " + ex.getMessage());
    }

  } // transferFunds

  // private methods

  private void executeTx(
    BigDecimal amount,
    String description,
    String accountId,
    BigDecimal newBalance,
    Account account) {

    Debug.print("TxControllerBean executeTx");

    try {
      makeConnection();
      String txId= DBHelper.getNextTxId(con);
      account.setBalance(newBalance);
      Tx tx=
        txHome.create(
          txId,
          accountId,
          new java.util.Date(),
          amount,
          newBalance,
          description);
    } catch (Exception ex) {
      throw new EJBException("executeTx: " + ex.getMessage());
    } finally {
      releaseConnection();
    }

  } // executeTx

  private void createTx(
    BigDecimal amount,
    String description,
    String accountId,
    BigDecimal newBalance,
    Account account) {

    try {
      makeConnection();
      String txId= DBHelper.getNextTxId(con);
      Tx tx=
        txHome.create(
          txId,
          accountId,
          new java.util.Date(),
          amount,
          newBalance,
          description);
    } catch (Exception ex) {
      throw new EJBException("createTx: " + ex.getMessage());
    } finally {
      releaseConnection();
    }
  }

  private Account checkAccountArgsAndResolve(
    BigDecimal amount,
    String description,
    String accountId)
    throws InvalidParameterException, AccountNotFoundException {

    Account account= null;

    if (description == null)
      throw new InvalidParameterException("null description");

    if (accountId == null)
      throw new InvalidParameterException("null accountId");

    if (amount.compareTo(bigZero) != 1)
      throw new InvalidParameterException("amount <= 0");

    try {
      account= accountHome.findByPrimaryKey(accountId);
    } catch (Exception ex) {
      throw new AccountNotFoundException(accountId);
    }

    return account;

  } // checkAccountArgs

  // ejb methods

  public void ejbCreate() {

    Debug.print("TxControllerBean ejbCreate");

    try {
      txHome= EJBGetter.getTxHome();
      accountHome= EJBGetter.getAccountHome();
    } catch (Exception ex) {
      throw new EJBException("ejbCreate: " + ex.getMessage());
    }
  } // ejbCreate

  public void setSessionContext(SessionContext context) {
    this.context= context;
  }

  public TxControllerBean() {}
  public void ejbRemove() {}
  public void ejbActivate() {}
  public void ejbPassivate() {}

  /*********************** Database Routines *************************/

  private void makeConnection() {

    Debug.print("TxControllerBean makeConnection");

    try {
      InitialContext ic= new InitialContext();
      DataSource ds= (DataSource)ic.lookup(CodedNames.BANK_DATABASE);
      con= ds.getConnection();
    } catch (Exception ex) {
      throw new EJBException(
        "Unable to connect to database. " + ex.getMessage());
    }
  } // makeConnection

  private void releaseConnection() {

    Debug.print("TxControllerBean releaseConnection");

    try {
      con.close();
    } catch (SQLException ex) {
      throw new EJBException("releaseConnection: " + ex.getMessage());
    }

  } // releaseConnection

} // TxControllerBean
