/*
 * Created on Feb 26, 2004
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
import aop.j2ee.business.entity.account.AccountPOJO;
import aop.j2ee.business.aspect.POJOEntity;

/**
 * @author pawlakr
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public privileged aspect SQLAccount extends POJOEntity {

	// EJB or EJBHome specific methods ===================

	public String AccountPOJO.ejbCreate(
		String accountId,
		String type,
		String description,
		BigDecimal balance,
		BigDecimal creditLine,
		BigDecimal beginBalance,
		java.util.Date beginBalanceTimeStamp,
		ArrayList customerIds)
		throws CreateException, MissingPrimaryKeyException {

		Debug.print("AccountBean ejbCreate");

		if ((accountId == null) || (accountId.trim().length() == 0)) {
			throw new MissingPrimaryKeyException("ejbCreate: accountId arg is null or empty");
		}

		this.accountId = accountId;
		this.type = type;
		this.description = description;
		this.balance = balance;
		this.creditLine = creditLine;
		this.beginBalance = beginBalance;
		this.beginBalanceTimeStamp = beginBalanceTimeStamp;
		this.customerIds = customerIds;

		try {
			insertRow();
		} catch (Exception ex) {
			throw new EJBException("ejbCreate: " + ex.getMessage());
		}

		return accountId;
	}

	public void AccountPOJO.ejbPostCreate(
		String accountId,
		String type,
		String description,
		BigDecimal balance,
		BigDecimal creditLine,
		BigDecimal beginBalance,
		java.util.Date beginBalanceTimeStamp,
		ArrayList customerIds) {
	}

	public Collection AccountPOJO.ejbFindByCustomerId(String customerId)
		throws FinderException {

		Debug.print("AccountBean ejbFindByCustomerId");

		Collection result;

		try {
			result = selectByCustomerId(customerId);
		} catch (Exception ex) {
			throw new EJBException("ejbFindByCustomerId " + ex.getMessage());
		}
		return result;
	}

	private void AccountPOJO.setExtraContext() {
		customerIds = new ArrayList();
	}

	private void AccountPOJO.setEntityId(String id) {
		this.accountId = id;
	}

	private String AccountPOJO.getEntityId(String id) {
		return this.accountId;
	}

	// SQL methods ========================================

	private Connection AccountPOJO.con;

	private void AccountPOJO.makeConnection() {

		Debug.print("AccountBean makeConnection");
    con=DBUtil.makeConnection();

	} // makeConnection

	private void AccountPOJO.releaseConnection() {

		Debug.print("AccountBean releaseConnection");
    DBUtil.releaseConnection(con);
	} // releaseConnection

	private void AccountPOJO.insertRow() throws SQLException {

		Debug.print("AccountBean insertRow");

		makeConnection();
		String insertStatement =
			"insert into account values ( ? , ? , ? , ? , ? , ? , ? )";
		PreparedStatement prepStmt = con.prepareStatement(insertStatement);

		prepStmt.setString(1, accountId);
		prepStmt.setString(2, type);
		prepStmt.setString(3, description);
		prepStmt.setBigDecimal(4, balance);
		prepStmt.setBigDecimal(5, creditLine);
		prepStmt.setBigDecimal(6, beginBalance);
		prepStmt.setDate(7, DBUtil.toSQLDate(beginBalanceTimeStamp));

		prepStmt.executeUpdate();
		prepStmt.close();
		releaseConnection();
	}

	private void AccountPOJO.deleteRow(String id) throws SQLException {

		Debug.print("AccountBean deleteRow");

		makeConnection();
		String deleteStatement = "delete from account where account_id = ? ";
		PreparedStatement prepStmt = con.prepareStatement(deleteStatement);

		prepStmt.setString(1, id);
		prepStmt.executeUpdate();
		prepStmt.close();
		releaseConnection();
	}

	private boolean AccountPOJO.selectByPrimaryKey(String primaryKey)
		throws SQLException {

		Debug.print("AccountBean selectByPrimaryKey");

		makeConnection();
		String selectStatement =
			"select account_id " + "from account where account_id = ? ";
		PreparedStatement prepStmt = con.prepareStatement(selectStatement);
		prepStmt.setString(1, primaryKey);

		ResultSet rs = prepStmt.executeQuery();
		boolean result = rs.next();
		prepStmt.close();
		releaseConnection();
		return result;
	}

	private Collection AccountPOJO.selectByCustomerId(String customerId)
		throws SQLException {

		Debug.print("AccountBean selectByCustomerId");

		makeConnection();
		String selectStatement =
			"select account_id "
				+ "from customer_account_xref "
				+ "where customer_id = ? ";
		PreparedStatement prepStmt = con.prepareStatement(selectStatement);

		prepStmt.setString(1, customerId);
		ResultSet rs = prepStmt.executeQuery();
		ArrayList a = new ArrayList();

		while (rs.next()) {
			a.add(rs.getString(1));
		}

		prepStmt.close();
		releaseConnection();
		return a;
	}

	private void AccountPOJO.loadEntity() throws SQLException {

		Debug.print("AccountBean loadAccount");

		makeConnection();
		String selectStatement =
			"select type, description, balance, credit_line, "
				+ "begin_balance, begin_balance_time_stamp "
				+ "from account where account_id = ? ";
		PreparedStatement prepStmt = con.prepareStatement(selectStatement);

		prepStmt.setString(1, accountId);

		ResultSet rs = prepStmt.executeQuery();

		if (rs.next()) {
			type = rs.getString(1);
			description = rs.getString(2);
			balance = rs.getBigDecimal(3);
			creditLine = rs.getBigDecimal(4);
			beginBalance = rs.getBigDecimal(5);
			beginBalanceTimeStamp = rs.getDate(6);
			prepStmt.close();
			releaseConnection();
		} else {
			prepStmt.close();
			releaseConnection();
			throw new NoSuchEntityException(
				"Row for id " + accountId + " not found in database.");
		}
	}

	private void AccountPOJO.storeEntity() throws SQLException {

		Debug.print("AccountBean storeAccount");

		makeConnection();
		String updateStatement =
			"update account set type =  ? , description = ? , "
				+ "balance = ? , credit_line = ? , "
				+ "begin_balance = ? , begin_balance_time_stamp = ? "
				+ "where account_id = ?";
		PreparedStatement prepStmt = con.prepareStatement(updateStatement);

		prepStmt.setString(1, type);
		prepStmt.setString(2, description);
		prepStmt.setBigDecimal(3, balance);
		prepStmt.setBigDecimal(4, creditLine);
		prepStmt.setBigDecimal(5, beginBalance);
		prepStmt.setDate(6, DBUtil.toSQLDate(beginBalanceTimeStamp));
		prepStmt.setString(7, accountId);

		int rowCount = prepStmt.executeUpdate();
		prepStmt.close();
		releaseConnection();

		if (rowCount == 0) {
			throw new EJBException(
				"Storing row for id " + accountId + " failed.");
		}
	}

	// SQL implementation of reference or collection objects
	void around(AccountPOJO account)
		throws
			Exception : execution(private void AccountPOJO.loadCustomerIds())
			&& this(account) {
		Debug.print("AccountBean loadCustomerIds");

		account.makeConnection();
		String selectStatement =
			"select customer_id "
				+ "from customer_account_xref where account_id = ? ";
		PreparedStatement prepStmt =
			account.con.prepareStatement(selectStatement);

		prepStmt.setString(1, account.accountId);
		ResultSet rs = prepStmt.executeQuery();
		account.customerIds.clear();

		while (rs.next()) {
			account.customerIds.add(rs.getString(1));
		}
		prepStmt.close();
		account.releaseConnection();
	}

}
