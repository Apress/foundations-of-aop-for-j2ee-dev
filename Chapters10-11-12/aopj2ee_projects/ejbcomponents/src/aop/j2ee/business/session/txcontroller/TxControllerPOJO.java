package aop.j2ee.business.session.txcontroller;

import java.util.*;
import java.math.*;
import javax.ejb.*;
import java.util.Date;

import aop.j2ee.business.entity.tx.Tx;
import java.rmi.RemoteException;
import aop.j2ee.business.entity.account.Account;
import aop.j2ee.commons.exception.*;
import aop.j2ee.commons.util.Debug;
import aop.j2ee.commons.util.DomainUtil;
import aop.j2ee.commons.to.TxDetails;
import aop.j2ee.business.aspect.marker.SessionBeanProtocol;

public class TxControllerPOJO implements SessionBeanProtocol {

   // Beans resolver (implementation added by aspects) ================

    private Collection findTxByAccountId(Date startDate,Date endDate,String accountId) throws Exception {
    return null;}

    private Tx findTxByPrimaryKey(String txId) throws Exception {
    return null;}

    private Account findAccountByPrimaryKey(String accountID) throws Exception {
        return null;
    }
     
   private Tx createTx(String accountId, Date date,
   BigDecimal amount, BigDecimal newBalance, String description) throws Exception {
       return null;
   }

    public ArrayList getTxsOfAccount(
        java.util.Date startDate,
        java.util.Date endDate,
        String accountId)
        throws InvalidParameterException {

        Collection txIds;
        ArrayList txList = new ArrayList();

        try {
            txIds = findTxByAccountId(startDate, endDate, accountId);
        } catch (Exception ex) {
            return txList;
        }

        try {
            Iterator i = txIds.iterator();
            while (i.hasNext()) {
                Tx tx = (Tx) i.next();
                TxDetails txDetail = tx.getDetails();
                txList.add(txDetail);
            }
        } catch (RemoteException ex) {
            throw new EJBException("getTxsOfAccount: " + ex.getMessage());
        }

        return txList;

    } // getTxsOfAccount

    public TxDetails getDetails(String txId)
        throws TxNotFoundException, InvalidParameterException {

        if (txId == null)
            throw new InvalidParameterException("null txId");

        try {
            Tx tx = findTxByPrimaryKey(txId);
            return tx.getDetails();
        } catch (Exception ex) {
            throw new TxNotFoundException(txId);
        }

    } // getDetails

    public void withdraw(
        BigDecimal amount,
        String description,
        String accountId)
        throws
    InvalidParameterException,
            AccountNotFoundException,
            InsufficientFundsException, InsufficientCreditException {

        Account account;
        try {
            account = findAccountByPrimaryKey(accountId);
        } catch (Exception ex) {
            throw new AccountNotFoundException(accountId);
        }
        try {
            BigDecimal newBalance = account.getBalance().subtract(amount);
            account.setBalance(newBalance);
            createTx(
                amount.negate(),
                description,
                accountId,
                newBalance,
                account);
        } catch (RemoteException ex) {
            throw new EJBException("withdraw: " + ex.getMessage());
        }

    } // withdraw


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

        try {
            fromAccount =
                findAccountByPrimaryKey(fromAccountId);
        } catch (Exception ex) {
            throw new AccountNotFoundException(fromAccountId);
        }
        try {
            toAccount =
                findAccountByPrimaryKey(toAccountId);
        } catch (Exception ex) {
            throw new AccountNotFoundException(toAccountId);
        }

        try {
            String fromType = fromAccount.getType();
            BigDecimal fromBalance = fromAccount.getBalance();
            BigDecimal fromAmount=DomainUtil.isCreditAccount(fromType)?
              amount.negate():amount;
            BigDecimal fromNewBalance = fromBalance.subtract(fromAmount);
            
             fromAccount.setBalance(fromNewBalance);
                createTx(
                DomainUtil.isCreditAccount(fromType)?amount:amount.negate(),
                    description,
                    fromAccountId,
                    fromNewBalance,
                    fromAccount);

            String toType = toAccount.getType();
            BigDecimal toBalance = toAccount.getBalance();
            BigDecimal toAmount=DomainUtil.isCreditAccount(fromType)?
              amount.negate():amount;
            BigDecimal toNewBalance = toBalance.subtract(toAmount);

            toAccount.setBalance(toNewBalance);
                createTx(
                    toAmount,
                    description,
                    toAccountId,
                    toNewBalance,
                    toAccount);

        } catch (RemoteException ex) {
            throw new EJBException("transferFunds: " + ex.getMessage());
        }

    } // transferFunds

    public void deposit(
        BigDecimal amount,
        String description,
        String accountId)
        throws
            InvalidParameterException,
            AccountNotFoundException,
            IllegalAccountTypeException {

        Account account;
        try {
            account = findAccountByPrimaryKey(accountId);
        } catch (Exception ex) {
            throw new AccountNotFoundException(accountId);
        }
        try {
            String type = account.getType();
            if (DomainUtil.isCreditAccount(type))
                throw new IllegalAccountTypeException(type);

            BigDecimal newBalance = account.getBalance().add(amount);
            account.setBalance(newBalance);
            createTx(amount, description, accountId, newBalance, account);
            
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

        Account account;
        try {
            account = findAccountByPrimaryKey(accountId);
        } catch (Exception ex) {
            throw new AccountNotFoundException(accountId);
        }
        try {
            String type = account.getType();
            if (DomainUtil.isCreditAccount(type) == false)
                throw new IllegalAccountTypeException(type);

            BigDecimal newBalance = account.getBalance().add(amount);

            if (newBalance.compareTo(account.getCreditLine()) == 1)
                throw new InsufficientCreditException();

            account.setBalance(newBalance);
            createTx(amount, description, accountId, newBalance, account);
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

        Account account;
        try {
            account = findAccountByPrimaryKey(accountId);
        } catch (Exception ex) {
            throw new AccountNotFoundException(accountId);
        }
        try {
            String type = account.getType();
            if (DomainUtil.isCreditAccount(type) == false)
                throw new IllegalAccountTypeException(type);

            BigDecimal newBalance = account.getBalance().subtract(amount);
            account.setBalance(newBalance);
            createTx(
                amount.negate(),
                description,
                accountId,
                newBalance,
                account);
        } catch (RemoteException ex) {
            throw new EJBException("makePayment: " + ex.getMessage());
        }

    } // makePayment


    // private methods

    private void createTx(
        BigDecimal amount,
        String description,
        String accountId,
        BigDecimal newBalance,
        Account account) {

        try {
            Tx tx =
                createTx(
                    accountId,
                    new java.util.Date(),
                    amount,
                    newBalance,
                    description);
        } catch (Exception ex) {
            throw new EJBException("createTx: " + ex.getMessage());
        }
    }

}
