/*
 * Created on Feb 21, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package aop.j2ee.commons.util;


import java.sql.*;
//import com.sun.ebank.util.*;
import aop.j2ee.commons.exception.*;

/**
 * This class contains helper methods for components
 * that access Cloudscape databases.
 */

public final class DBHelper {

    // The getNext<name>Id methods retrieve the next unique
    // primary key.

    public static final String getNextAccountId(Connection con) 
        throws SQLException, MissingPrimaryKeyException {

        Debug.print("DBHelper getNextAccountId");
        return getNextId(con, "next_account_id");
    } // getNextAccountId

    public static final String getNextCustomerId(Connection con) 
        throws SQLException, MissingPrimaryKeyException {

        Debug.print("DBHelper getNextCustomerId");
        return getNextId(con, "next_customer_id");
    } // getNextCustomerId

    public static final String getNextTxId(Connection con)
        throws SQLException, MissingPrimaryKeyException {

        Debug.print("DBHelper getNextTxId");
        return getNextId(con, "next_tx_id");
    } // getNextTxId

    private static final String getNextId(Connection con, String table)
        throws SQLException, MissingPrimaryKeyException {

        Debug.print("DBHelper getNextId");

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
        int i = rs.getInt(1);
        rs.close();
        prepSelect.close();
        prepUpdate.close();

        if (i <= 0) {
            throw new MissingPrimaryKeyException
            ("Table " + table + " is empty.");
        }

        return Integer.toString(i);

    } // getNextId


    public static final java.sql.Date toSQLDate(java.util.Date inDate) {

        // This method returns a sql.Date version of the util.Date arg.

        return new java.sql.Date(inDate.getTime());
    }


} // class 
