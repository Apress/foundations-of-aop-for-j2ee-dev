/*
 * Created on Feb 21, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

package aop.j2ee.business.session.bank;

import java.sql.*;
import javax.sql.*;
import java.util.*;
import java.math.*;
import javax.ejb.*;
import javax.naming.*;
import java.rmi.RemoteException;
import aop.j2ee.business.entity.customer.CustomerHome;
import aop.j2ee.business.entity.customer.Customer;
import aop.j2ee.business.entity.account.AccountHome;
import aop.j2ee.business.entity.account.Account;
import aop.j2ee.commons.exception.*;
import aop.j2ee.commons.util.Debug;
import aop.j2ee.commons.util.DBHelper;
import aop.j2ee.commons.to.AccountDetails;
import aop.j2ee.commons.to.CustomerDetails;
import aop.j2ee.business.util.EJBGetter;
import aop.j2ee.commons.util.CodedNames;

public class BankBean implements SessionBean {

        // The accountTypes array stores the valid account types.

        private static String[] accountTypes =
            {"Checking" , "Savings" , "Credit" , "Money Market" };

        public static String[] getAccountTypes()  {

            return accountTypes;
        } 

        public static void checkAccountType(String type)
            throws IllegalAccountTypeException {

            boolean foundIt = false;

            for (int i = 0; i < accountTypes.length ; i++) {
                if (accountTypes[i].equals(type))
                    foundIt = true;
            }

            if (foundIt == false)
                throw new IllegalAccountTypeException(type);
        }

        public static boolean isCreditAccount(String type) {

            if (type.equals("Credit"))
                return true;
            else
                return false;
        }

    private String accountId;
    private AccountHome accountHome;
    private Account account;
    private Connection con;
    
    // account creation and removal methods

    public String createAccount(String customerId, String type,
        String description, BigDecimal balance, BigDecimal creditLine, 
        BigDecimal beginBalance, java.util.Date beginBalanceTimeStamp)   
        throws IllegalAccountTypeException, CustomerNotFoundException,
        InvalidParameterException {


        // makes a new account and enters it into db,
        // customer for customerId must exist 1st,
        // returns accountId

        Debug.print("AccountControllerBean createAccount");

        if (customerId == null) 
            throw new InvalidParameterException("null customerId" );

        if (type == null)
            throw new InvalidParameterException("null type");

        if (description == null)
            throw new InvalidParameterException("null description");

        if (beginBalanceTimeStamp == null)
            throw new InvalidParameterException("null beginBalanceTimeStamp");

        checkAccountType(type);

        if (customerExists(customerId) == false)
            throw new CustomerNotFoundException(customerId);

        ArrayList customerIds = new ArrayList();
        customerIds.add(customerId);

        try {
            makeConnection();
            accountId = DBHelper.getNextAccountId(con);
            account = accountHome.create(accountId, type,
                description, balance, creditLine, beginBalance,
                beginBalanceTimeStamp, customerIds);   
            insertXref(customerId, accountId);
            releaseConnection();
        } catch (Exception ex) {
             releaseConnection();
             throw new EJBException
             ("createAccount: " + ex.getMessage());
        }

        return accountId;
    } // createAccount

    public void removeAccount(String accountId) 
        throws AccountNotFoundException, InvalidParameterException {

       // removes account from db

        Debug.print("AccountControllerBean removeAccount");

        if (accountId == null)
            throw new InvalidParameterException("null accountId" );

        if (accountExists(accountId) == false)
            throw new AccountNotFoundException(accountId);

        try {
            makeConnection();
            deleteAllAccountInXref(accountId);
            releaseConnection();
            account.remove();
        } catch (Exception ex) {
             releaseConnection();
             throw new EJBException
             ("removeAccount: " + ex.getMessage());
        }

    } // removeAccount


    // customer-account relationship methods

    public void addCustomerToAccount(String customerId, 
        String accountId) throws AccountNotFoundException, 
        CustomerNotFoundException, CustomerInAccountException,
        InvalidParameterException {

        // adds another customer to the account

        Debug.print("AccountControllerBean addCustomerToAccount");

        if (customerId == null)
            throw new InvalidParameterException("null customerId" );

        if (accountId == null)
            throw new InvalidParameterException("null accountId" );

        ArrayList customerIds;
        CustomerHome customerHome;

        if (customerExists(customerId) == false)
            throw new CustomerNotFoundException(customerId);

        if (accountExists(accountId) == false)
            throw new AccountNotFoundException(accountId);

        try {
            makeConnection();
            customerIds = getCustomerIds(accountId);
        } catch (Exception ex) {
             releaseConnection();
             throw new EJBException
             ("addCustomerToAccount: " + ex.getMessage());
        }

        if (customerIds.contains(customerId)) {
            releaseConnection();
            throw new CustomerInAccountException
            ("customer " + customerId + 
             " already assigned to account " + accountId);
        } 

        try {
            insertXref(customerId, accountId);
            releaseConnection();
        } catch (Exception ex) {
             releaseConnection();
             throw new EJBException
             ("addCustomerToAccount: " + ex.getMessage());
        }
    } // addCustomerToAccount

    public void removeCustomerFromAccount(String customerId, 
        String accountId) throws AccountNotFoundException,
        CustomerRequiredException, CustomerNotInAccountException,
        InvalidParameterException {

        // removes a customer from this account, but
        // the customer is not removed from the db

        Debug.print("AccountControllerBean removeCustomerFromAccount");

        ArrayList customerIds;

        if (customerId == null)
            throw new InvalidParameterException("null customerId" );

        if (accountId == null)
            throw new InvalidParameterException("null accountId" );

        if (accountExists(accountId) == false)
            throw new AccountNotFoundException(accountId);

        try {
            makeConnection();
            customerIds = getCustomerIds(accountId);
        } catch (Exception ex) {
             releaseConnection();
             throw new EJBException
             ("removeCustomerFromAccount: " + ex.getMessage());
        }

        if (customerIds.size() == 1) {
            releaseConnection();
            throw new CustomerRequiredException();
        } 

        if (customerIds.contains(customerId) == false) {
            releaseConnection();
            throw new CustomerNotInAccountException
            ("customer " + customerId + 
             " not assigned to account " + accountId);
        } 
        try {
            deleteOneCustomerInXref(customerId, accountId);
            releaseConnection();
        } catch (Exception ex) {
             releaseConnection();
             throw new EJBException
             ("removeCustomerFromAccount: " + ex.getMessage());
        }
    } // removeCustomerFromAccount


    // getters

    public ArrayList getAccountsOfCustomer(String customerId)
        throws AccountNotFoundException, InvalidParameterException {

        // returns an ArrayList of AccountDetails 
        // that correspond to the accounts for the specified
        // customer

        Debug.print("AccountControllerBean getAccountsOfCustomer");

        Collection accountIds;

        if (customerId == null) 
            throw new InvalidParameterException("null customerId");

        try {
            accountIds = accountHome.findByCustomerId(customerId);
            if (accountIds.isEmpty())
                throw new AccountNotFoundException();
        } catch (Exception ex) {
             throw new AccountNotFoundException();
        }

        ArrayList accountList = new ArrayList();

        try {
            Iterator i = accountIds.iterator();
            while (i.hasNext()) {
                Account account = (Account)i.next();
                AccountDetails accountDetails = account.getDetails();
                accountList.add(accountDetails);
            }
        } catch (RemoteException ex) {
             throw new EJBException("getAccountsOfCustomer: " 
                 + ex.getMessage());
        } 

        return accountList;

    } //  getAccountsOfCustomer

    public AccountDetails getAccountDetails(String accountId) 
        throws AccountNotFoundException, InvalidParameterException {

        // returns the AccountDetails for the specified account

        Debug.print("AccountControllerBean getDetails");

        AccountDetails result;

        if (accountId == null)
            throw new InvalidParameterException("null accountId" );

        if (accountExists(accountId) == false)
            throw new AccountNotFoundException(accountId);

        try {
            result = account.getDetails();
        } catch (RemoteException ex) {
             throw new EJBException("getDetails: " + ex.getMessage());
        } 

        return result;

    } // getDetails
     

    // setters

    public void setAccountType(String type, String accountId) 
        throws AccountNotFoundException, IllegalAccountTypeException,
        InvalidParameterException {

        Debug.print("AccountControllerBean setType");

        if (type == null)
            throw new InvalidParameterException("null type");

        if (accountId == null)
            throw new InvalidParameterException("null accountId" );

        checkAccountType(type);
        if (accountExists(accountId) == false)
            throw new AccountNotFoundException(accountId);

        try {
            account.setType(type);
        } catch (RemoteException ex) {
             throw new EJBException("setType: " + ex.getMessage());
        } 

    } // setType

    public void setAccountDescription(String description, String accountId)
        throws AccountNotFoundException,InvalidParameterException {

        Debug.print("AccountControllerBean setDescription");

        if (description == null)
            throw new InvalidParameterException("null description");

        if (accountId == null)
            throw new InvalidParameterException("null accountId" );

        if (accountExists(accountId) == false)
            throw new AccountNotFoundException(accountId);

    } // setDescription

    public void setAccountBalance(BigDecimal balance, String accountId)
        throws AccountNotFoundException, InvalidParameterException {

        Debug.print("AccountControllerBean setBalance");

        if (accountId == null)
            throw new InvalidParameterException("null accountId" );

        if (accountExists(accountId) == false)
            throw new AccountNotFoundException(accountId);

        try {
            account.setBalance(balance);
        } catch (RemoteException ex) {
             throw new EJBException("setBalance: " + ex.getMessage());
        } 

    } // setBalance

    public void setAccountCreditLine(BigDecimal creditLine, String accountId)
        throws EJBException, AccountNotFoundException,
        InvalidParameterException {

        Debug.print("AccountControllerBean setCreditLine");

        if (accountId == null)
            throw new InvalidParameterException("null accountId" );

        if (accountExists(accountId) == false)
            throw new AccountNotFoundException(accountId);

        try {
            account.setCreditLine(creditLine);
        } catch (RemoteException ex) {
             throw new EJBException("setCreditLine: " + ex.getMessage());
        } 

    } // setCreditLine

    public void setAccountBeginBalance(BigDecimal beginBalance, String accountId)
        throws AccountNotFoundException, InvalidParameterException {

        Debug.print("AccountControllerBean setBeginBalance");

        if (accountId == null)
            throw new InvalidParameterException("null accountId" );

        if (accountExists(accountId) == false)
            throw new AccountNotFoundException(accountId);

        try {
            account.setBeginBalance(beginBalance);
        } catch (RemoteException ex) {
             throw new EJBException("setBeginBalance: " + ex.getMessage());
        } 

    } // setBeginBalance

    public void setAccountBeginBalanceTimeStamp(java.util.Date beginBalanceTimeStamp, 
        String accountId)
        throws AccountNotFoundException, InvalidParameterException {

        Debug.print("AccountControllerBean setBeginBalanceTimeStamp");

        if (beginBalanceTimeStamp == null)
            throw new InvalidParameterException("null beginBalanceTimeStamp");

        if (accountId == null)
            throw new InvalidParameterException("null accountId" );

        if (accountExists(accountId) == false)
            throw new AccountNotFoundException(accountId);

        try {
            account.setBeginBalanceTimeStamp(beginBalanceTimeStamp);
        } catch (RemoteException ex) {
             throw new EJBException("setBeginBalanceTimeStamp: " 
                 + ex.getMessage());
        } 

    } // setBeginBalanceTimeStamp

    // ejb methods

    public void ejbCreate() {

        Debug.print("AccountControllerBean ejbCreate");

        try {
            accountHome = EJBGetter.getAccountHome();
            customerHome = EJBGetter.getCustomerHome();
        } catch (Exception ex) {
             throw new EJBException("ejbCreate: " +
                 ex.getMessage());
        }

        account = null;
        accountId = null;
        customer = null;
        customerId = null;
    } // ejbCreate

    public BankBean() {}
    public void ejbRemove() {}
    public void ejbActivate() {}
    public void ejbPassivate() {}
    public void setSessionContext(SessionContext sc) {}

    // private methods

    private boolean accountExists(String accountId) {

        // If a business method has been invoked with
        // a different accountId, then find the new
        // accountId and update the accountId and account 
        // variables.  Return false if the account
        // cannot be found.

        Debug.print("AccountControllerBean accountExists");

        if (accountId.equals(this.accountId) == false) {
            try {
                account = accountHome.findByPrimaryKey(accountId);
                this.accountId = accountId;
            } catch (Exception ex) {
                return false;
            }
        } // if
        return true;

    } // accountExists


    private void insertXref (String customerId, String accountId) 
        throws SQLException {
   
        Debug.print("AccountControllerBean insertXref");         

        String insertStatement =
            "insert into customer_account_xref " +
            "values ( ? , ? )";
        PreparedStatement prepStmt = 
            con.prepareStatement(insertStatement);
   
        prepStmt.setString(1, customerId);
        prepStmt.setString(2, accountId);
        prepStmt.executeUpdate();
        prepStmt.close();
    }

    private void deleteAllAccountInXref (String accountId)
        throws SQLException {
   
        Debug.print("AccountControllerBean deleteAllAccountInXref");         

        String deleteStatement =
            "delete from customer_account_xref " +
            "where account_id  = ? ";
        PreparedStatement prepStmt = 
            con.prepareStatement(deleteStatement);
   
        prepStmt.setString(1, accountId);
        prepStmt.executeUpdate();
        prepStmt.close();
    }


    private ArrayList getCustomerIds(String accountId) 
        throws SQLException {
   
        Debug.print("AccountControllerBean getCustomerIds");

        String selectStatement =
                "select customer_id " +
                "from customer_account_xref " +
                "where account_id = ? ";
        PreparedStatement prepStmt = 
                con.prepareStatement(selectStatement);
   
        prepStmt.setString(1, accountId);
        ResultSet rs = prepStmt.executeQuery();
        ArrayList a = new ArrayList();
   
        while (rs.next()) {
            a.add(rs.getString(1));
        }
   
        prepStmt.close();
        return a;
    } // getCustomerIds


    private void deleteOneCustomerInXref (String customerId, String accountId)
        throws SQLException {
   
        Debug.print("AccountControllerBean deleteOneCustomerInXref");         

        String deleteStatement =
            "delete from customer_account_xref " +
            "where account_id  = ? and customer_id = ? ";
        PreparedStatement prepStmt = 
            con.prepareStatement(deleteStatement);
   
        prepStmt.setString(1, accountId);
        prepStmt.setString(2, customerId);
        prepStmt.executeUpdate();
        prepStmt.close();
    }


    private String customerId;
    private CustomerHome customerHome;
    private Customer customer;
    
    // customer creation and removal methods

    public String createCustomer (String lastName,
        String firstName, String middleInitial, String street,
        String city, String state, String zip, String phone,
        String email) throws InvalidParameterException {

        // makes a new customer and enters it into db,
        // returns customerId

        Debug.print("CustomerControllerBean createCustomer");

        if (lastName == null) 
            throw new InvalidParameterException("null lastName");
 
        if (firstName == null) 
            throw new InvalidParameterException("null firstName");

        try {
            makeConnection();
            customerId = DBHelper.getNextCustomerId(con);
            customer = customerHome.create(customerId,
                lastName, firstName, middleInitial, street,
                city, state, zip, phone, email);
            releaseConnection();
        } catch (Exception ex) {
             releaseConnection();
             throw new EJBException
             ("createCustomer: " + ex.getMessage());
        }

        return customerId;
    } // createCustomer

    public void removeCustomer(String customerId) 
        throws CustomerNotFoundException, InvalidParameterException {

       // removes customer from db

        Debug.print("CustomerControllerBean removeCustomer");

        if (customerId == null)
            throw new InvalidParameterException("null customerId" );

        if (customerExists(customerId) == false)
            throw new CustomerNotFoundException(customerId);

        try {
            makeConnection();
            deleteAllCustomerInXref(customerId);
            customer.remove();
            releaseConnection();
        } catch (Exception ex) {
             releaseConnection();
             throw new EJBException
             ("removeCustomer: " + ex.getMessage());
        }

    } // removeCustomer

    // getters

    public ArrayList getCustomersOfAccount(String accountId) 
        throws CustomerNotFoundException, InvalidParameterException {

        // returns an ArrayList of CustomerDetails 
        // that correspond to the accountId specified

        Debug.print("CustomerControllerBean getCustomersOfAccount");

        Collection customerIds;

        if (accountId == null) 
            throw new InvalidParameterException("null accountId");

        try {
            customerIds = customerHome.findByAccountId(accountId);
            if (customerIds.isEmpty())
                throw new CustomerNotFoundException();
        } catch (Exception ex) {
             throw new CustomerNotFoundException();
        }

        ArrayList customerList = new ArrayList();

        try {
            Iterator i = customerIds.iterator();
            while (i.hasNext()) {
                Customer customer = (Customer)i.next();
                CustomerDetails customerDetail = customer.getDetails();
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

        Debug.print("CustomerControllerBean getDetails");

        CustomerDetails result;

        if (customerId == null)
            throw new InvalidParameterException("null customerId" );

        if (customerExists(customerId) == false)
            throw new CustomerNotFoundException(customerId);

        try {
            result = customer.getDetails();
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

        Debug.print("CustomerControllerBean getCustomersOfCustomer");

        Collection customerIds;
        ArrayList customerList = new ArrayList();

        if (lastName == null) 
            throw new InvalidParameterException("null lastName");

        try {
            customerIds = customerHome.findByLastName(lastName);
        } catch (Exception ex) {
             return customerList;
        }

        try {
            Iterator i = customerIds.iterator();
            while (i.hasNext()) {
                Customer customer = (Customer)i.next();
                CustomerDetails customerDetail = customer.getDetails();
                customerList.add(customerDetail);
            }
        } catch (RemoteException ex) {
             throw new EJBException("getCustomersOfLastName: " 
                 + ex.getMessage());
        } 

        return customerList;

    } //  getCustomersOfLastName

    // setters

    public void setCustomerName(String lastName, String firstName,
        String middleInitial, String customerId)
        throws CustomerNotFoundException, InvalidParameterException {

        Debug.print("CustomerControllerBean setName");

        if (lastName == null) 
            throw new InvalidParameterException("null lastName");
 
        if (firstName == null) 
            throw new InvalidParameterException("null firstName");

        if (customerId == null)
            throw new InvalidParameterException("null customerId" );

        if (customerExists(customerId) == false)
            throw new CustomerNotFoundException(customerId);

        try {
            customer.setLastName(lastName);
            customer.setFirstName(firstName);
            customer.setMiddleInitial(middleInitial);
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }

    } // setName

    public void setCustomerAddress(String street, String city,
        String state, String zip, String phone, String email,
        String customerId)
        throws CustomerNotFoundException, InvalidParameterException {

        Debug.print("CustomerControllerBean setAddress");

        if (street == null) 
            throw new InvalidParameterException("null street");
 
        if (city == null) 
            throw new InvalidParameterException("null city");

        if (state == null) 
            throw new InvalidParameterException("null state");

        if (customerId == null)
            throw new InvalidParameterException("null customerId" );

        if (customerExists(customerId) == false)
            throw new CustomerNotFoundException(customerId);

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
        

    // private methods

    private boolean customerExists(String customerId) {

        // If a business method has been invoked with
        // a different customerId, then update the
        // customerId and customer variables.
        // Return null if the customer is not found.

        Debug.print("CustomerControllerBean customerExists");

        if (customerId.equals(this.customerId) == false) {
            try {
                customer = customerHome.findByPrimaryKey(customerId);
                this.customerId = customerId;
            } catch (Exception ex) {
                return false;
            }
        } // if
        return true;

    } // customerExists

/*********************** Database Routines *************************/

    private void makeConnection() {
   
        Debug.print("AccountControllerBean makeConnection");

        try {
            InitialContext ic = new InitialContext();
            DataSource ds = (DataSource) ic.lookup(CodedNames.BANK_DATABASE);
            con =  ds.getConnection();
        } catch (Exception ex) {
             throw new EJBException("Unable to connect to database. " +
                 ex.getMessage());
        }
    } // makeConnection
 
  
    private void releaseConnection() {
   
        Debug.print("AccountControllerBean releaseConnection");

        try {
            con.close();
        } catch (SQLException ex) {
             throw new EJBException("releaseConnection: " + ex.getMessage());
        }

    } // releaseConnection

    private void deleteAllCustomerInXref (String customerId)
        throws SQLException {
   
        Debug.print("CustomerControllerBean deleteAllCustomerInXref");         

        String deleteStatement =
            "delete from customer_account_xref " +
            "where customer_id  = ? ";
        PreparedStatement prepStmt = 
            con.prepareStatement(deleteStatement);
   
        prepStmt.setString(1, customerId);
        prepStmt.executeUpdate();
        prepStmt.close();
    }




} // AccountControllerBean

