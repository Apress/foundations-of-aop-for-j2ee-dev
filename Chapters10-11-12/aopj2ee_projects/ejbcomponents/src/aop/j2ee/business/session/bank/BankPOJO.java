/*
 * Created on Mar 11, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package aop.j2ee.business.session.bank;

import aop.j2ee.commons.exception.*;
import javax.ejb.EJBException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Date;
import aop.j2ee.business.entity.account.Account;
import aop.j2ee.business.entity.customer.Customer;
import java.rmi.RemoteException;
import aop.j2ee.commons.to.AccountDetails;
import aop.j2ee.commons.to.CustomerDetails;
import aop.j2ee.business.aspect.marker.SessionBeanProtocol;

/**
 * @author pawlakr
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class BankPOJO implements SessionBeanProtocol {

  // Beans resolver (implementation added by aspects) ================

  private Account findAccountByPrimaryKey(String accountID) throws Exception {
    return null;
  }

  private Customer findCustomerByPrimaryKey(String customerID)
    throws Exception {
    return null;
  }

  private void deleteOneCustomerInXref(String customerId, String accountId)
    throws Exception {};
  private void insertXref(String customerId, String accountId)
    throws Exception {};
  private void deleteAllCustomerInXref(String customerId) throws Exception {};

  private Account doCreateAccount(
    String customerId,
    String type,
    String description,
    BigDecimal balance,
    BigDecimal creditLine,
    BigDecimal beginBalance,
    java.util.Date beginBalanceTimeStamp,
    ArrayList customerIds)
    throws Exception {
    return null;
  }

  private Account addCustomerToAccount(
    String customerId,
    String type,
    String description,
    BigDecimal balance,
    BigDecimal creditLine,
    BigDecimal beginBalance,
    java.util.Date beginBalanceTimeStamp,
    ArrayList customerIds)
    throws Exception {
    return null;
  }

  ArrayList getCustomerIds(String accountId) throws Exception {
    return null;
  }

  private Collection findAccountsByCustomerId(String customerId)
    throws Exception {
    return null;
  }

  private Collection findCustomersByAccountId(String accountId)
    throws Exception {
    return null;
  }

  private Collection findCustomerByLastName(String lastName) throws Exception {
    return null;
  }

  private String accountId;

  // account creation and removal methods

  public String createAccount(
    String customerId,
    String type,
    String description,
    BigDecimal balance,
    BigDecimal creditLine,
    BigDecimal beginBalance,
    java.util.Date beginBalanceTimeStamp)
    throws
      IllegalAccountTypeException,
      CustomerNotFoundException,
      InvalidParameterException {

    // makes a new account and enters it into db,
    // customer for customerId must exist 1st,
    // returns accountId

    Customer customer;
    try {
      customer= findCustomerByPrimaryKey(customerId);
    } catch (Exception ex) {
      throw new CustomerNotFoundException(customerId);
    }

    ArrayList customerIds= new ArrayList();
    customerIds.add(customerId);

    try {
      doCreateAccount(
        customerId,
        type,
        description,
        balance,
        creditLine,
        beginBalance,
        beginBalanceTimeStamp,
        customerIds);
      /*makeConnection();
      accountId = DBHelper.getNextAccountId(con);
      account = accountHome.create(accountId, type,
          description, balance, creditLine, beginBalance,
          beginBalanceTimeStamp, customerIds);   
      insertXref(customerId, accountId);
      releaseConnection();*/
    } catch (Exception ex) {
      throw new EJBException("createAccount: " + ex.getMessage());
    }

    return accountId;
  } // createAccount

  public void removeAccount(String accountId)
    throws AccountNotFoundException, InvalidParameterException {

    /*
           // removes account from db
    
              Account account;
              try {
                  account = findAccountByPrimaryKey(accountId);
              } catch (Exception ex) {
                  throw new AccountNotFoundException(accountId);
              }
    
    
            try {
                //doRemoveAccount(accountId);
    
                makeConnection();
                deleteAllAccountInXref(accountId);
                releaseConnection();
                account.remove();
                
            } catch (Exception ex) {
                 releaseConnection();
                 throw new EJBException
                 ("removeAccount: " + ex.getMessage());
            }
    */
  } // removeAccount

  // customer-account relationship methods

  public void addCustomerToAccount(String customerId, String accountId)
    throws
      AccountNotFoundException,
      CustomerNotFoundException,
      CustomerInAccountException,
      InvalidParameterException {

    // adds another customer to the account

    ArrayList customerIds;
    //        CustomerHome customerHome;

    Customer customer;
    try {
      customer= findCustomerByPrimaryKey(customerId);
    } catch (Exception ex) {
      throw new CustomerNotFoundException(customerId);
    }

    Account account;
    try {
      account= findAccountByPrimaryKey(accountId);
    } catch (Exception ex) {
      throw new AccountNotFoundException(accountId);
    }

    try {
      //makeConnection();
      customerIds= getCustomerIds(accountId);
    } catch (Exception ex) {
      //releaseConnection();
      throw new EJBException("addCustomerToAccount: " + ex.getMessage());
    }

    if (customerIds.contains(customerId)) {
      //releaseConnection();
      throw new CustomerInAccountException(
        "customer " + customerId + " already assigned to account " + accountId);
    }

    try {
      insertXref(customerId, accountId);
      //releaseConnection();
    } catch (Exception ex) {
      //releaseConnection();
      throw new EJBException("addCustomerToAccount: " + ex.getMessage());
    }
  } // addCustomerToAccount

  public void removeCustomerFromAccount(String customerId, String accountId)
    throws
      AccountNotFoundException,
      CustomerRequiredException,
      CustomerNotInAccountException,
      InvalidParameterException {

    // removes a customer from this account, but
    // the customer is not removed from the db

    //Debug.print("AccountControllerBean removeCustomerFromAccount");

    ArrayList customerIds;

     try {
      //makeConnection();
      customerIds= getCustomerIds(accountId);
    } catch (Exception ex) {
      //releaseConnection();
      throw new EJBException("removeCustomerFromAccount: " + ex.getMessage());
    }

    if (customerIds.size() == 1) {
      //releaseConnection();
      throw new CustomerRequiredException();
    }

    if (customerIds.contains(customerId) == false) {
      //releaseConnection();
      throw new CustomerNotInAccountException(
        "customer " + customerId + " not assigned to account " + accountId);
    }
    try {
      deleteOneCustomerInXref(customerId, accountId);
      //releaseConnection();
    } catch (Exception ex) {
      //releaseConnection();
      throw new EJBException("removeCustomerFromAccount: " + ex.getMessage());
    }
  } // removeCustomerFromAccount

  // getters

  public ArrayList getAccountsOfCustomer(String customerId)
    throws AccountNotFoundException, InvalidParameterException {

    // returns an ArrayList of AccountDetails 
    // that correspond to the accounts for the specified
    // customer

    //        Debug.print("AccountControllerBean getAccountsOfCustomer");

    Collection accountIds;

     try {
      accountIds= findAccountsByCustomerId(customerId);
      if (accountIds.isEmpty())
        throw new AccountNotFoundException();
    } catch (Exception ex) {
      throw new AccountNotFoundException();
    }

    ArrayList accountList= new ArrayList();

    try {
      Iterator i= accountIds.iterator();
      while (i.hasNext()) {
        Account account= (Account)i.next();
        AccountDetails accountDetails= account.getDetails();
        accountList.add(accountDetails);
      }
    } catch (RemoteException ex) {
      throw new EJBException("getAccountsOfCustomer: " + ex.getMessage());
    }

    return accountList;

  } //  getAccountsOfCustomer

  public AccountDetails getAccountDetails(String accountId)
    throws AccountNotFoundException, InvalidParameterException {

    // returns the AccountDetails for the specified account

    AccountDetails result;

     Account account;
    try {
      account= findAccountByPrimaryKey(accountId);
    } catch (Exception ex) {
      throw new AccountNotFoundException(accountId);
    }

    try {
      result= account.getDetails();
    } catch (RemoteException ex) {
      throw new EJBException("getDetails: " + ex.getMessage());
    }

    return result;

  } // getDetails

  // setters

  public void setAccountType(String type, String accountId)
    throws
      AccountNotFoundException,
      IllegalAccountTypeException,
      InvalidParameterException {

    Account account;
    try {
      account= findAccountByPrimaryKey(accountId);
    } catch (Exception ex) {
      throw new AccountNotFoundException(accountId);
    }

    try {
      account.setType(type);
    } catch (RemoteException ex) {
      throw new EJBException("setType: " + ex.getMessage());
    }

  } // setType

  public void setAccountDescription(String description, String accountId)
    throws AccountNotFoundException, InvalidParameterException {} // setDescription

  public void setAccountBalance(BigDecimal balance, String accountId)
    throws AccountNotFoundException, InvalidParameterException {

    Account account;
    try {
      account= findAccountByPrimaryKey(accountId);
    } catch (Exception ex) {
      throw new AccountNotFoundException(accountId);
    }

    try {
      account.setBalance(balance);
    } catch (RemoteException ex) {
      throw new EJBException("setBalance: " + ex.getMessage());
    }

  } // setBalance

  public void setAccountCreditLine(BigDecimal creditLine, String accountId)
    throws EJBException, AccountNotFoundException, InvalidParameterException {

    //        Debug.print("AccountControllerBean setCreditLine");

    Account account;
    try {
      account= findAccountByPrimaryKey(accountId);
    } catch (Exception ex) {
      throw new AccountNotFoundException(accountId);
    }

    try {
      account.setCreditLine(creditLine);
    } catch (RemoteException ex) {
      throw new EJBException("setCreditLine: " + ex.getMessage());
    }

  } // setCreditLine

  public void setAccountBeginBalance(BigDecimal beginBalance, String accountId)
    throws AccountNotFoundException, InvalidParameterException {

    Account account;
    try {
      account= findAccountByPrimaryKey(accountId);
    } catch (Exception ex) {
      throw new AccountNotFoundException(accountId);
    }
    try {
      account.setBeginBalance(beginBalance);
    } catch (RemoteException ex) {
      throw new EJBException("setBeginBalance: " + ex.getMessage());
    }

  } // setBeginBalance

  public void setAccountBeginBalanceTimeStamp(
    java.util.Date beginBalanceTimeStamp,
    String accountId)
    throws AccountNotFoundException, InvalidParameterException {

    Account account;
    try {
      account= findAccountByPrimaryKey(accountId);
    } catch (Exception ex) {
      throw new AccountNotFoundException(accountId);
    }
    try {
      account.setBeginBalanceTimeStamp(beginBalanceTimeStamp);
    } catch (RemoteException ex) {
      throw new EJBException("setBeginBalanceTimeStamp: " + ex.getMessage());
    }

  } // setBeginBalanceTimeStamp

  // private methods
  // customer creation and removal methods

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
    throws InvalidParameterException {
    return null;
  }

  public void removeCustomer(String customerId)
    throws CustomerNotFoundException, InvalidParameterException {

    // removes customer from db

    //Debug.print("CustomerControllerBean removeCustomer");

    Customer customer;
    try {
      customer= findCustomerByPrimaryKey(customerId);
    } catch (Exception ex) {
      throw new CustomerNotFoundException(customerId);
    }

    try {
      //makeConnection();
      deleteAllCustomerInXref(customerId);
      customer.remove();
      //releaseConnection();
    } catch (Exception ex) {
      //releaseConnection();
      throw new EJBException("removeCustomer: " + ex.getMessage());
    }

  } // removeCustomer

  // getters

  public ArrayList getCustomersOfAccount(String accountId)
    throws CustomerNotFoundException, InvalidParameterException {

    // returns an ArrayList of CustomerDetails 
    // that correspond to the accountId specified

    Collection customerIds;

    try {
      customerIds= findCustomersByAccountId(accountId);
      if (customerIds.isEmpty())
        throw new CustomerNotFoundException();
    } catch (Exception ex) {
      throw new CustomerNotFoundException();
    }

    ArrayList customerList= new ArrayList();

    try {
      Iterator i= customerIds.iterator();
      while (i.hasNext()) {
        Customer customer= (Customer)i.next();
        CustomerDetails customerDetail= customer.getDetails();
        customerList.add(customerDetail);
      }
    } catch (RemoteException ex) {
      throw new EJBException(": " + ex.getMessage());
    }

    return customerList;

  } //  getCustomersOfAccount

  public CustomerDetails getCustomerDetails(String customerId)
    throws CustomerNotFoundException, InvalidParameterException {

    // returns the CustomerDetails for the specified customer

    CustomerDetails result;

    Customer customer;
    try {
      customer= findCustomerByPrimaryKey(customerId);
    } catch (Exception ex) {
      throw new CustomerNotFoundException(customerId);
    }

    try {
      result= customer.getDetails();
    } catch (RemoteException ex) {
      throw new EJBException("getDetails: " + ex.getMessage());
    }

    return result;

  } // getDetails

  public ArrayList getCustomersOfLastName(String lastName)
    throws InvalidParameterException {

    // returns an ArrayList of CustomerDetails 
    // that correspond to the the lastName specified
    // returns null if no customers are found

    Collection customerIds;
    ArrayList customerList= new ArrayList();

    try {
      customerIds= findCustomerByLastName(lastName);
    } catch (Exception ex) {
      return customerList;
    }

    try {
      Iterator i= customerIds.iterator();
      while (i.hasNext()) {
        Customer customer= (Customer)i.next();
        CustomerDetails customerDetail= customer.getDetails();
        customerList.add(customerDetail);
      }
    } catch (RemoteException ex) {
      throw new EJBException("getCustomersOfLastName: " + ex.getMessage());
    }

    return customerList;

  } //  getCustomersOfLastName

  // setters

  public void setCustomerName(
    String lastName,
    String firstName,
    String middleInitial,
    String customerId)
    throws CustomerNotFoundException, InvalidParameterException {

    Customer customer;
    try {
      customer= findCustomerByPrimaryKey(customerId);
    } catch (Exception ex) {
      throw new CustomerNotFoundException(customerId);
    }

    try {
      customer.setLastName(lastName);
      customer.setFirstName(firstName);
      customer.setMiddleInitial(middleInitial);
    } catch (Exception ex) {
      throw new EJBException(ex.getMessage());
    }

  } // setName

  public void setCustomerAddress(
    String street,
    String city,
    String state,
    String zip,
    String phone,
    String email,
    String customerId)
    throws CustomerNotFoundException, InvalidParameterException {

    Customer customer;
    try {
      customer= findCustomerByPrimaryKey(customerId);
    } catch (Exception ex) {
      throw new CustomerNotFoundException(customerId);
    }

    try {
      customer.setStreet(street);
      customer.setCity(city);
      customer.setState(state);
      customer.setZip(zip);
      customer.setPhone(phone);
      customer.setEmail(email);
    } catch (Exception ex) {
      throw new EJBException(ex.getMessage());
    }
  } // setAddress

}
