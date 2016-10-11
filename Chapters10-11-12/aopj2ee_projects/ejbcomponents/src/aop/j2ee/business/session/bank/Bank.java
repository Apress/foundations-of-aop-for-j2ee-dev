/*
 * Created on Feb 21, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package aop.j2ee.business.session.bank;

import javax.ejb.EJBObject;

import java.util.ArrayList;
import java.util.Date;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import aop.j2ee.commons.to.AccountDetails;
import aop.j2ee.commons.to.CustomerDetails;
import aop.j2ee.commons.exception.*;

public interface Bank extends EJBObject {


    // account creation and removal methods

    public String createAccount(String customerId, String type, 
        String description, BigDecimal balance, BigDecimal creditLine, 
        BigDecimal beginBalance, Date beginBalanceTimeStamp) 
        throws RemoteException, IllegalAccountTypeException,
        CustomerNotFoundException, InvalidParameterException;


        // makes a new account and enters it into db,
        // customer for customerId must exist 1st,
        // returns accountId

    public void removeAccount(String accountId)
       throws RemoteException, AccountNotFoundException, 
       InvalidParameterException;

       // removes account from db

    // customer-account relationship methods

    public void addCustomerToAccount(String customerId, 
        String accountId) throws RemoteException,
        AccountNotFoundException, CustomerNotFoundException,
        CustomerInAccountException, InvalidParameterException;

        // adds another customer to the account
        // throws CustomerInAccountException
        //        if the customer is already in the account
        // throws CustomerNotFoundException 
        //        if the customer does not exist

    public void removeCustomerFromAccount(String customerId, 
        String accountId) throws RemoteException,
        AccountNotFoundException, CustomerRequiredException, 
        CustomerNotInAccountException, InvalidParameterException;

        // removes a customer from the account, but
        // the customer is not removed from the db
        // throws CustomerRequiredException
        //        if there is only one customer in the account
        //        (an account must have a least one customer)
        // throws CustomerNotInAccountException
        //        if the customer to be removed is not in the account

    // getters

    public ArrayList getAccountsOfCustomer(String customerId)
        throws RemoteException, AccountNotFoundException,
        InvalidParameterException;

        // returns an ArrayList of AccountDetails objects
        // that correspond to the accounts for the specified
        // customer

    public AccountDetails getAccountDetails(String accountId)
        throws RemoteException, AccountNotFoundException,
        InvalidParameterException;

        // returns the AccountDetails for the specified account

    // setters

    public void setAccountType(String type, String accountId)
        throws RemoteException, AccountNotFoundException, 
        IllegalAccountTypeException, InvalidParameterException;

    public void setAccountDescription(String description, String accountId)
        throws RemoteException, AccountNotFoundException,
        InvalidParameterException;

    public void setAccountBalance(BigDecimal balance, String accountId)
        throws RemoteException, AccountNotFoundException,
        InvalidParameterException;

    public void setAccountCreditLine(BigDecimal creditLine, String accountId)
        throws RemoteException, AccountNotFoundException,
        InvalidParameterException;

    public void setAccountBeginBalance(BigDecimal beginBalance, String accountId)
        throws RemoteException, AccountNotFoundException,
        InvalidParameterException;

    public void setAccountBeginBalanceTimeStamp(Date beginBalanceTimeStamp, 
        String accountId) throws RemoteException, AccountNotFoundException,
        InvalidParameterException;

    public String createCustomer (String lastName,
        String firstName, String middleInitial, String street,
        String city, String state, String zip, String phone,
        String email)
        throws InvalidParameterException, RemoteException;

        // makes a new customer and enters it into db,
        // returns customerId

    public void removeCustomer(String customerId)
       throws RemoteException, CustomerNotFoundException,
       InvalidParameterException;

       // removes customer from db

    // getters

    public ArrayList getCustomersOfAccount(String accountId)
        throws RemoteException, CustomerNotFoundException,
        InvalidParameterException;;

        // returns an ArrayList of CustomerDetails objects
        // that correspond to the customers for the specified
        // account

    public CustomerDetails getCustomerDetails(String customerId)
        throws RemoteException, CustomerNotFoundException,
        InvalidParameterException;

        // returns the CustomerDetails for the specified customer


    public ArrayList getCustomersOfLastName(String lastName)
        throws InvalidParameterException, RemoteException;

        // returns an ArrayList of CustomerDetails objects
        // that correspond to the customers for the specified
        // last name; if now customers are found the ArrayList
        // is empty

    // setters

    public void setCustomerName(String lastName, String firstName,
        String middleInitial, String customerId)
        throws RemoteException, CustomerNotFoundException,
        InvalidParameterException;

    public void setCustomerAddress(String street, String city, 
        String state, String zip, String phone, String email,
        String customerId)
        throws RemoteException, CustomerNotFoundException,
        InvalidParameterException;


}
