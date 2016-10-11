/*
 * Created on Mar 12, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package aop.j2ee.business.aspect.sql;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.ejb.*;
import java.sql.PreparedStatement;
import aop.j2ee.business.entity.tx.TxPOJO;
import aop.j2ee.business.entity.customer.CustomerPOJO;
import aop.j2ee.commons.exception.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.ArrayList;
import aop.j2ee.commons.util.Debug;
import aop.j2ee.business.aspect.POJOEntity;
import aop.j2ee.business.util.EJBGetter;

/**
 * @author pawlakr
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public privileged aspect SQLTx extends POJOEntity {


  public String TxPOJO.ejbCreate (String txID, String accountId,
      java.util.Date timeStamp, BigDecimal amount, BigDecimal balance,
      String description) 
      throws CreateException, MissingPrimaryKeyException {

      Debug.print("TxBean ejbCreate");

      if ((txId == null) || (txId.trim().length() == 0)) {
          throw new MissingPrimaryKeyException
          ("ejbCreate: txId arg is null or empty");
      }

      this.txId = txId;
      this.accountId = accountId;
      this.timeStamp = timeStamp;
      this.amount = amount;
      this.balance = balance;
      this.description = description;

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
      return txId;
}

public Collection TxPOJO.ejbFindByAccountId(java.util.Date startDate, 
    java.util.Date endDate, String accountId) 
    throws FinderException {
   
    Debug.print("TxBean ejbFindByAccountId");

    Collection result;

    try {
        result = selectByAccountId(accountId, startDate, endDate);
    } catch (Exception ex) {
          throw new EJBException("ejbFindByAccountId " + 
              ex.getMessage());
    }
    return result;
}

/*********************** Database Routines *************************/

private Connection TxPOJO.con;

private void TxPOJO.makeConnection() {

  Debug.print("AccountBean makeConnection");
  con=DBUtil.makeConnection();

} // makeConnection

private void TxPOJO.releaseConnection() {

  Debug.print("AccountBean releaseConnection");
  DBUtil.releaseConnection(con);
} // releaseConnection

  private void TxPOJO.insertRow () throws SQLException {
   
      Debug.print("TxBean insertRow");         

      makeConnection();
      String insertStatement =
          "insert into tx values ( ? , ? , ? , ? , ? , ? )" ;
      PreparedStatement prepStmt = 
          con.prepareStatement(insertStatement);
   
      prepStmt.setString(1, txId);
      prepStmt.setString(2, accountId);
      prepStmt.setDate(3, DBUtil.toSQLDate(timeStamp));
      prepStmt.setBigDecimal(4, amount);
      prepStmt.setBigDecimal(5, balance);
      prepStmt.setString(6, description);

      prepStmt.executeUpdate();
      prepStmt.close();
      releaseConnection();
  }
   
  private void TxPOJO.deleteRow(String id) throws SQLException {
   
      Debug.print("TxBean deleteRow");

      makeConnection();
      String deleteStatement =
              "delete from tx where tx_id = ? ";
      PreparedStatement prepStmt =
              con.prepareStatement(deleteStatement);
   
      prepStmt.setString(1, id);
      prepStmt.executeUpdate();
      prepStmt.close();
      releaseConnection();
  }
   
  private boolean TxPOJO.selectByPrimaryKey(String primaryKey) 
      throws SQLException {
   
      Debug.print("TxBean selectByPrimaryKey");

      makeConnection();
      String selectStatement =
              "select tx_id " +
              "from tx where tx_id = ? ";
      PreparedStatement prepStmt =
              con.prepareStatement(selectStatement);
      prepStmt.setString(1, primaryKey);
   
      ResultSet rs = prepStmt.executeQuery();
      boolean result = rs.next();
      prepStmt.close();
      releaseConnection();
      return result;
  }
   
  private Collection TxPOJO.selectByAccountId(String accountId, 
      java.util.Date startDate, java.util.Date endDate) 
      throws SQLException {
   
      Debug.print("TxBean selectByAccountId");

      makeConnection();
      String selectStatement =
              "select tx_id " +
              "from tx " +
              "where (account_id = ?) " +
              "and ((time_stamp >= ?) and (time_stamp <= ?)) ";
      PreparedStatement prepStmt = 
              con.prepareStatement(selectStatement);
   
      prepStmt.setString(1, accountId);
      prepStmt.setDate(2, DBUtil.toSQLDate(startDate));
      prepStmt.setDate(3, DBUtil.toSQLDate(endDate));
      ResultSet rs = prepStmt.executeQuery();
      ArrayList a = new ArrayList();
   
      while (rs.next()) {
          a.add(rs.getString(1));
      }
   
      prepStmt.close();
      releaseConnection();
      return a;
  }
   
  private void TxPOJO.loadEntity() throws SQLException {
   
      Debug.print("TxBean loadTx");

      makeConnection();
      String selectStatement =
              "select account_id, time_stamp, amount, balance, description " +
              "from tx where tx_id = ? ";
      PreparedStatement prepStmt = 
              con.prepareStatement(selectStatement);
   
      prepStmt.setString(1, txId);

      ResultSet rs = prepStmt.executeQuery();
   
      if (rs.next()) {
          accountId = rs.getString(1);
          timeStamp = rs.getDate(2);
          amount = rs.getBigDecimal(3);
          balance = rs.getBigDecimal(4);
          description = rs.getString(5);
          prepStmt.close();
          releaseConnection();
      }
      else {
          prepStmt.close();
          releaseConnection();
          throw new NoSuchEntityException("Row for id " + 
              txId + " not found in database.");
      }
  }
   

  private void TxPOJO.storeEntity() throws SQLException {
   
      Debug.print("TxBean storeTx");

      makeConnection();
      String updateStatement =
              "update tx " +
              "set account_id = ? , time_stamp = ? , " +
              "amount = ? , balance = ? , description = ?  " +
              "where tx_id = ? ";
      PreparedStatement prepStmt = 
              con.prepareStatement(updateStatement);
   
      prepStmt.setString(1, accountId);
      prepStmt.setDate(2, DBUtil.toSQLDate(timeStamp));
      prepStmt.setBigDecimal(3, amount);
      prepStmt.setBigDecimal(4, balance);
      prepStmt.setString(5, description);
      prepStmt.setString(6, txId);

      int rowCount = prepStmt.executeUpdate();
      prepStmt.close();
      releaseConnection();
   
      if (rowCount == 0) {
          throw new EJBException("Storing row for id " + txId + " failed.");
      }
  }


}
