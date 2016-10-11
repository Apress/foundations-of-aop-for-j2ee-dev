/*
 * Created on Mar 12, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package aop.j2ee.business.aspect.sql;

/**
 * @author pawlakr
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public aspect SQLBank {

/*

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


*/

}
