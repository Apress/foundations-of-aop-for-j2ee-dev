/*
 * Created on Mar 12, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package aop.j2ee.business.aspect.sql;

import java.sql.*;
import java.util.*;
import java.math.*;
import javax.ejb.*;
import aop.j2ee.commons.exception.*;
import aop.j2ee.commons.util.Debug;
import aop.j2ee.business.entity.customer.CustomerPOJO;
import aop.j2ee.business.entity.customer.CustomerHome;
import aop.j2ee.business.aspect.POJOEntity;
import aop.j2ee.business.util.EJBGetter;


//import javax.naming.DataSource;

/**
 * @author pawlakr
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public privileged aspect SQLCustomer extends POJOEntity {

  // ejb methods 

  public String CustomerPOJO.ejbCreate(
    String customerId,
    String lastName,
    String firstName,
    String middleInitial,
    String street,
    String city,
    String state,
    String zip,
    String phone,
    String email)
    throws CreateException, MissingPrimaryKeyException {

    Debug.print("CustomerBean ejbCreate");

    if ((customerId == null) || (customerId.trim().length() == 0)) {
      throw new MissingPrimaryKeyException("ejbCreate: customerId arg is null or empty");
    }

    this.customerId= customerId;
    this.lastName= lastName;
    this.firstName= firstName;
    this.middleInitial= middleInitial;
    this.street= street;
    this.city= city;
    this.state= state;
    this.zip= zip;
    this.phone= phone;
    this.email= email;

    try {
      if (txHome == null)
        txHome= EJBGetter.getTxHome();
      if (accountHome == null)
        accountHome= EJBGetter.getAccountHome();
      if (customerHome == null)
        customerHome= EJBGetter.getCustomerHome();
    } catch (Exception ex) {
      throw new EJBException("ejbCreate: " + ex.getMessage());
    }


    try {
      insertRow();
    } catch (Exception ex) {
      throw new EJBException("ejbCreate: " + ex.getMessage());
    }

    return customerId;
  }

  public Collection CustomerPOJO.ejbFindByAccountId(String accountId)
    throws FinderException {

    Debug.print("CustomerBean ejbFindByAccountId");

    Collection result;

    try {
      result= selectByAccountId(accountId);
    } catch (Exception ex) {
      throw new EJBException("ejbFindByAccountId " + ex.getMessage());
    }
    return result;
  }

  public Collection CustomerPOJO.ejbFindByLastName(String lastName)
    throws FinderException {

    Debug.print("CustomerBean ejbFindByLastName");

    Collection result;

    try {
      result= selectByLastName(lastName);
    } catch (Exception ex) {
      throw new EJBException("ejbFindByLastName " + ex.getMessage());
    }
    return result;
  }

  /*********************** Database Routines *************************/

  private Connection CustomerPOJO.con;

  private void CustomerPOJO.makeConnection() {

    Debug.print("AccountBean makeConnection");
    con=DBUtil.makeConnection();

  } // makeConnection

  private void CustomerPOJO.releaseConnection() {

    Debug.print("AccountBean releaseConnection");
    DBUtil.releaseConnection(con);
  } // releaseConnection

/*
  Object around(
    CustomerPOJO cust,
    String lastName,
    String firstName,
    String middleInitial,
    String street,
    String city,
    String state,
    String zip,
    String phone,
    String email) throw InvalidParameterException : execution(
    String CustomerPOJO.createCustomer(
      String,
      String,
      String,
      String,
      String,
      String,
      String,
      String,
      String))
    && args(
      lastName,
      firstName,
      middleInitial,
      street,
      city,
      state,
      zip,
      phone,
      email) && this(cust) {
    if (lastName == null)
      throw new InvalidParameterException("null lastName");

    if (firstName == null)
      throw new InvalidParameterException("null firstName");
      String customerId=null;
    try {
      cust.makeConnection();
      customerId= DBUtil.getNextCustomerId();
      //cust.customer=
      cust.customerHome.create(
          customerId,
          lastName,
          firstName,
          middleInitial,
          street,
          city,
          state,
          zip,
          phone,
          email);
      cust.releaseConnection();
    } catch (Exception ex) {
      cust.releaseConnection();
      throw new EJBException("createCustomer: " + ex.getMessage());
    }

    return customerId;

  }
*/

  private void CustomerPOJO.insertRow() throws SQLException {

    Debug.print("CustomerBean insertRow");

    makeConnection();
    String insertStatement=
      "insert into customer values ( ? , ? , ? , ? , ? , "
        + " ? , ? , ? , ? , ? )";
    PreparedStatement prepStmt= con.prepareStatement(insertStatement);

    prepStmt.setString(1, customerId);
    prepStmt.setString(2, lastName);
    prepStmt.setString(3, firstName);
    prepStmt.setString(4, middleInitial);
    prepStmt.setString(5, street);
    prepStmt.setString(6, city);
    prepStmt.setString(7, state);
    prepStmt.setString(8, zip);
    prepStmt.setString(9, phone);
    prepStmt.setString(10, email);

    prepStmt.executeUpdate();
    prepStmt.close();
    releaseConnection();
  }

  private void CustomerPOJO.deleteRow(String id) throws SQLException {

    Debug.print("CustomerBean deleteRow");

    makeConnection();
    String deleteStatement= "delete from customer where customer_id = ? ";
    PreparedStatement prepStmt= con.prepareStatement(deleteStatement);

    prepStmt.setString(1, id);
    prepStmt.executeUpdate();
    prepStmt.close();
    releaseConnection();
  }

  private boolean CustomerPOJO.selectByPrimaryKey(String primaryKey)
    throws SQLException {

    Debug.print("CustomerBean selectByPrimaryKey");

    makeConnection();
    String selectStatement=
      "select customer_id " + "from customer where customer_id = ? ";
    PreparedStatement prepStmt= con.prepareStatement(selectStatement);
    prepStmt.setString(1, primaryKey);

    ResultSet rs= prepStmt.executeQuery();
    boolean result= rs.next();
    prepStmt.close();
    releaseConnection();
    return result;
  }

  private Collection CustomerPOJO.selectByAccountId(String accountId)
    throws SQLException {

    Debug.print("CustomerBean selectByAccountId");

    makeConnection();
    String selectStatement=
      "select customer_id "
        + "from customer_account_xref "
        + "where account_id = ? ";
    PreparedStatement prepStmt= con.prepareStatement(selectStatement);

    prepStmt.setString(1, accountId);
    ResultSet rs= prepStmt.executeQuery();
    ArrayList a= new ArrayList();

    while (rs.next()) {
      a.add(rs.getString(1));
    }

    prepStmt.close();
    releaseConnection();
    return a;
  }

  private Collection CustomerPOJO.selectByLastName(String lastName)
    throws SQLException {

    Debug.print("CustomerBean selectByLastName");

    makeConnection();
    String selectStatement=
      "select customer_id " + "from customer " + "where last_name = ? ";
    PreparedStatement prepStmt= con.prepareStatement(selectStatement);

    prepStmt.setString(1, lastName);
    ResultSet rs= prepStmt.executeQuery();
    ArrayList a= new ArrayList();

    while (rs.next()) {
      a.add(rs.getString(1));
    }

    prepStmt.close();
    releaseConnection();
    return a;
  }

  private void CustomerPOJO.loadEntity() throws SQLException {

    Debug.print("CustomerBean loadCustomer");

    makeConnection();
    String selectStatement=
      "select last_name, first_name, middle_initial, "
        + "street, city, state, zip, phone, email "
        + "from customer where customer_id = ? ";
    PreparedStatement prepStmt= con.prepareStatement(selectStatement);

    prepStmt.setString(1, customerId);

    ResultSet rs= prepStmt.executeQuery();

    if (rs.next()) {
      lastName= rs.getString(1);
      firstName= rs.getString(2);
      middleInitial= rs.getString(3);
      street= rs.getString(4);
      city= rs.getString(5);
      state= rs.getString(6);
      zip= rs.getString(7);
      phone= rs.getString(8);
      email= rs.getString(9);
      prepStmt.close();
      releaseConnection();
    } else {
      prepStmt.close();
      releaseConnection();
      throw new NoSuchEntityException(
        "Row for id " + customerId + " not found in database.");
    }
  }

  private void CustomerPOJO.storeEntity() throws SQLException {

    Debug.print("CustomerBean storeCustomer");

    makeConnection();
    String updateStatement=
      "update customer "
        + "set last_name = ? , first_name = ? , "
        + "middle_initial = ? , street = ? , city = ? , "
        + "state = ? , zip = ? , phone = ? , email = ? "
        + "where customer_id = ? ";
    PreparedStatement prepStmt= con.prepareStatement(updateStatement);

    prepStmt.setString(1, lastName);
    prepStmt.setString(2, firstName);
    prepStmt.setString(3, middleInitial);
    prepStmt.setString(4, street);
    prepStmt.setString(5, city);
    prepStmt.setString(6, state);
    prepStmt.setString(7, zip);
    prepStmt.setString(8, phone);
    prepStmt.setString(9, email);
    prepStmt.setString(10, customerId);

    int rowCount= prepStmt.executeUpdate();
    prepStmt.close();
    releaseConnection();

    if (rowCount == 0) {
      throw new EJBException("Storing row for id " + customerId + " failed.");
    }
  }



}
