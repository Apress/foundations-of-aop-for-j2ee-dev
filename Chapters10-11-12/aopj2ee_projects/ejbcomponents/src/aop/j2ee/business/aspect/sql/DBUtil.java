/*
 * Created on Feb 21, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package aop.j2ee.business.aspect.sql;


import java.sql.*;
//import com.sun.ebank.util.*;
import aop.j2ee.commons.exception.*;
import javax.sql.*;
import javax.ejb.*;
import javax.naming.*;
import aop.j2ee.commons.util.Debug;
import aop.j2ee.commons.util.CodedNames;

/**
 * This class contains helper methods for components
 * that access Cloudscape databases.
 */

public final class DBUtil {

    // The getNext<name>Id methods retrieve the next unique
    // primary key.

    public static final String getNextAccountId() 
        throws SQLException, MissingPrimaryKeyException {

        Debug.print("DBHelper getNextAccountId");
        return getNextId("next_account_id");
    } // getNextAccountId

    public static final String getNextCustomerId() 
        throws SQLException, MissingPrimaryKeyException {

        Debug.print("DBHelper getNextCustomerId");
        return getNextId("next_customer_id");
    } // getNextCustomerId


    public static Connection makeConnection() {

    Connection con;
    try {
        InitialContext ic = new InitialContext();
        DataSource ds = (DataSource) ic.lookup(CodedNames.BANK_DATABASE);
        con = ds.getConnection();
    } catch (Exception ex) {
        throw new EJBException(
            "Unable to connect to database. " + ex.getMessage());
    }
        return con;
    }

    public static void releaseConnection(Connection con) {

        try {
            con.close();
        } catch (SQLException ex) {
            throw new EJBException("releaseConnection: " + ex.getMessage());
        }

    }

    public static final String getNextTxId()
        throws SQLException, MissingPrimaryKeyException {

        Debug.print("DBHelper getNextTxId");
        return getNextId("next_tx_id");
    } // getNextTxId

    private static final String getNextId(String table)
        throws SQLException, MissingPrimaryKeyException {

        Debug.print("DBHelper getNextId");
        int i;
        Connection con=null;
        
        try {
            con=makeConnection();

        String selectStatement =
                "select max(id) from " + table;
        String updateStatement =
                "update " + table + " " +
                "set id = id + 1 ";

        PreparedStatement prepSelect =
                con.prepareStatement(selectStatement);
        PreparedStatement prepUpdate =
                con.prepareStatement(updateStatement);
 
        prepUpdate.executeUpdate();
        ResultSet rs = prepSelect.executeQuery();
        rs.next();
        i = rs.getInt(1);
        rs.close();
        prepSelect.close();
        prepUpdate.close();

        if (i <= 0) {
            throw new MissingPrimaryKeyException
            ("Table " + table + " is empty.");
        }

        } catch (Exception ex) {
            throw new EJBException("executeTx: " + ex.getMessage());
        } finally {
            releaseConnection(con);
        }

        return Integer.toString(i);

    } // getNextId


    public static final java.sql.Date toSQLDate(java.util.Date inDate) {

        // This method returns a sql.Date version of the util.Date arg.

        return new java.sql.Date(inDate.getTime());
    }


} // class 
