/*
 * Created on Feb 21, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package aop.j2ee.client.delegate;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import javax.ejb.CreateException;
import javax.naming.NamingException;

//import org.apache.log4j.Logger;

import aop.j2ee.commons.exception.*;
import aop.j2ee.commons.to.AccountDetails;
import aop.j2ee.commons.to.CustomerDetails;
import aop.j2ee.commons.util.locator.ServiceLocator;

import aop.j2ee.business.session.bank.BankHome;
import aop.j2ee.business.session.bank.Bank;

public class BankDelegate {

  private ResourceBundle messages;
  private static ServiceLocator locator;
  Bank bank;

  private void init() throws SystemException {
    try {
      locator= ServiceLocator.getInstance();
    } catch (NamingException ne) {
      throw new SystemException(ne.getMessage());
    }
  }

  private Bank getServiceFacade() throws SystemException {
    if (bank != null)
      return bank;
    try {
      BankHome home= (BankHome)locator.lookupHome(Bank.class);
      bank= home.create();
    } catch (ClassNotFoundException cne) {
      cne.printStackTrace();
      throw new SystemException(cne.getMessage());
    } catch (NamingException ne) {
      ne.printStackTrace();
      throw new SystemException(ne.getMessage());
    } catch (CreateException ce) {
      ce.printStackTrace();
      throw new SystemException(ce.getMessage());
    } catch (RemoteException re) {
      re.printStackTrace();
      throw new SystemException(re.getMessage());
    }
    return bank;
  }

  public BankDelegate() throws SystemException {
    if (locator == null)
      init();
  }

  public void addCustomerToAccount(String customerId, String accountId)
    throws
      RemoteException,
      AccountNotFoundException,
      CustomerNotFoundException,
      CustomerInAccountException,
      InvalidParameterException {
    Bank bank;
    try {
      bank= getServiceFacade();
    } catch (SystemException ex) {
      ex.printStackTrace();
      return;
    }
    bank.addCustomerToAccount(customerId, accountId);

  }

  public String createAccount(
    String customerId,
    String type,
    String description,
    BigDecimal balance,
    BigDecimal creditLine,
    BigDecimal beginBalance,
    Date beginBalanceTimeStamp)
    throws
      RemoteException,
      IllegalAccountTypeException,
      CustomerNotFoundException,
      InvalidParameterException {

    Bank bank;
    try {
      bank= getServiceFacade();
    } catch (SystemException ex) {
      try {
        Thread.sleep(1000);
        bank= getServiceFacade();
      } catch (SystemException ex2) {
        ex2.printStackTrace();
        return null;
      } catch (InterruptedException ex2) {
        ex2.printStackTrace();
        return null;
      }
    }
    String result=null;
    try {
      result = bank.createAccount(
        customerId,
        type,
        description,
        balance,
        creditLine,
        beginBalance,
        beginBalanceTimeStamp);
    } catch (RemoteException ex) {
      try {
        Thread.sleep(1000);
        bank= null;
        createAccount(
          customerId,
          type,
          description,
          balance,
          creditLine,
          beginBalance,
          beginBalanceTimeStamp);
      } catch (InterruptedException ex2) {
        ex2.printStackTrace();
      }
    }
    return result;
  }

  public String createCustomer(
    String lastName,
    String firstName,
    String middleInitial,
    String street,
    String city,
    String state,
    String zip,
    String phone,
    String email)
    throws InvalidParameterException, RemoteException {
    Bank bank;
    try {
      bank= getServiceFacade();
    } catch (SystemException ex) {
      ex.printStackTrace();
      return null;
    }
    return bank.createCustomer(
      lastName,
      firstName,
      middleInitial,
      street,
      city,
      state,
      zip,
      phone,
      email);
  }

  public AccountDetails getAccountDetails(String accountId)
    throws RemoteException, AccountNotFoundException, InvalidParameterException {

    Bank bank;
    try {
      bank= getServiceFacade();
    } catch (SystemException ex) {
      ex.printStackTrace();
      return null;
    }
    return bank.getAccountDetails(accountId);
  }

  public ArrayList getAccountsOfCustomer(String customerId)
    throws RemoteException, AccountNotFoundException, InvalidParameterException {
    Bank bank;
    try {
      bank= getServiceFacade();
    } catch (SystemException ex) {
      ex.printStackTrace();
      return null;
    }
    return bank.getAccountsOfCustomer(customerId);
  }

  public CustomerDetails getCustomerDetails(String customerId)
    throws RemoteException, CustomerNotFoundException, InvalidParameterException {
    Bank bank;
    try {
      bank= getServiceFacade();
    } catch (SystemException ex) {
      ex.printStackTrace();
      return null;
    }
    return bank.getCustomerDetails(customerId);
  }

  public ArrayList getCustomersOfAccount(String accountId)
    throws RemoteException, CustomerNotFoundException, InvalidParameterException {
    Bank bank;
    try {
      bank= getServiceFacade();
    } catch (SystemException ex) {
      ex.printStackTrace();
      return null;
    }
    return bank.getCustomersOfAccount(accountId);
  }

  public ArrayList getCustomersOfLastName(String lastName)
    throws InvalidParameterException, RemoteException {

    Bank bank;
    try {
      bank= getServiceFacade();
    } catch (SystemException ex) {
      ex.printStackTrace();
      return null;
    }
    return bank.getCustomersOfLastName(lastName);
  }

  public void removeAccount(String accountId)
    throws RemoteException, AccountNotFoundException, InvalidParameterException {

    Bank bank;
    try {
      bank= getServiceFacade();
    } catch (SystemException ex) {
      ex.printStackTrace();
      return;
    }
    bank.removeAccount(accountId);

  }

  public void removeCustomer(String customerId)
    throws RemoteException, CustomerNotFoundException, InvalidParameterException {

    Bank bank;
    try {
      bank= getServiceFacade();
    } catch (SystemException ex) {
      ex.printStackTrace();
      return;
    }
    bank.removeCustomer(customerId);

  }

  public void removeCustomerFromAccount(String customerId, String accountId)
    throws
      RemoteException,
      AccountNotFoundException,
      CustomerRequiredException,
      CustomerNotInAccountException,
      InvalidParameterException {

    Bank bank;
    try {
      bank= getServiceFacade();
    } catch (SystemException ex) {
      ex.printStackTrace();
      return;
    }
    bank.removeCustomerFromAccount(customerId, accountId);

  }

  public void setAccountBalance(BigDecimal balance, String accountId)
    throws RemoteException, AccountNotFoundException, InvalidParameterException {

    Bank bank;
    try {
      bank= getServiceFacade();
    } catch (SystemException ex) {
      ex.printStackTrace();
      return;
    }
    bank.setAccountBalance(balance, accountId);

  }

  public void setAccountBeginBalance(BigDecimal beginBalance, String accountId)
    throws RemoteException, AccountNotFoundException, InvalidParameterException {

    Bank bank;
    try {
      bank= getServiceFacade();
    } catch (SystemException ex) {
      ex.printStackTrace();
      return;
    }
    bank.setAccountBeginBalance(beginBalance, accountId);

  }

  public void setAccountBeginBalanceTimeStamp(
    Date beginBalanceTimeStamp,
    String accountId)
    throws RemoteException, AccountNotFoundException, InvalidParameterException {

    Bank bank;
    try {
      bank= getServiceFacade();
    } catch (SystemException ex) {
      ex.printStackTrace();
      return;
    }
    bank.setAccountBeginBalanceTimeStamp(beginBalanceTimeStamp, accountId);

  }

  public void setAccountCreditLine(BigDecimal creditLine, String accountId)
    throws RemoteException, AccountNotFoundException, InvalidParameterException {
    Bank bank;
    try {
      bank= getServiceFacade();
    } catch (SystemException ex) {
      ex.printStackTrace();
      return;
    }
    bank.setAccountCreditLine(creditLine, accountId);
  }

  /* (non-Javadoc)
   * @see aop.j2ee.commons.facade.Bank#setAccountDescription(java.lang.String, java.lang.String)
   */
  public void setAccountDescription(String description, String accountId)
    throws RemoteException, AccountNotFoundException, InvalidParameterException {
    Bank bank;
    try {
      bank= getServiceFacade();
    } catch (SystemException ex) {
      ex.printStackTrace();
      return;
    }
    bank.setAccountDescription(description, accountId);

  }

  public void setAccountType(String type, String accountId)
    throws
      RemoteException,
      AccountNotFoundException,
      IllegalAccountTypeException,
      InvalidParameterException {
    Bank bank;
    try {
      bank= getServiceFacade();
    } catch (SystemException ex) {
      ex.printStackTrace();
      return;
    }
    bank.setAccountType(type, accountId);
  }

  public void setCustomerAddress(
    String street,
    String city,
    String state,
    String zip,
    String phone,
    String email,
    String customerId)
    throws RemoteException, CustomerNotFoundException, InvalidParameterException {

    Bank bank;
    try {
      bank= getServiceFacade();
    } catch (SystemException ex) {
      ex.printStackTrace();
      return;
    }
    bank.setCustomerAddress(street, city, state, zip, phone, email, customerId);
  }

  public void setCustomerName(
    String lastName,
    String firstName,
    String middleInitial,
    String customerId)
    throws RemoteException, CustomerNotFoundException, InvalidParameterException {
    Bank bank;
    try {
      bank= getServiceFacade();
    } catch (SystemException ex) {
      ex.printStackTrace();
      return;
    }
    bank.setCustomerName(lastName, firstName, middleInitial, customerId);
  }
}
