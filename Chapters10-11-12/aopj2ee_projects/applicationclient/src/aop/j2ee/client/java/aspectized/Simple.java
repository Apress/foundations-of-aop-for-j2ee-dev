/*
 * Created on Feb 26, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package aop.j2ee.client.java.aspectized;

import java.math.BigDecimal;
import aop.j2ee.business.session.bank.Bank;
import aop.j2ee.commons.to.AccountDetails;
import aop.j2ee.commons.exception.SystemException;
import java.util.Date;

public class Simple {

/*
	public static void main(String[] args) {

		try {
			Bank bank = (Bank) getServiceFacade(Bank.class);
			String customerId =
				bank.createCustomer(
					"Pawlak",
					"Renaud",
					"",
					"Frederick St",
					"Hartford",
					"CT",
					"06105",
					"NA",
					"renaud@aopsys.com");
			System.out.println("Created new customer " + customerId);
			String accountId =
				bank.createAccount(
					customerId,
					"Debit",
					"This is a test.",
					new BigDecimal(100),
					new BigDecimal(0),
					new BigDecimal(100),
					new Date());
			System.out.println("Created new customer " + accountId);
			bank.setAccountBalance(new BigDecimal(200), accountId);
			System.out.println("Changed balance");
			AccountDetails details = bank.getAccountDetails(accountId);
			System.out.println("Account details:");
			System.out.println(details);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}

	static Object getServiceFacade(Class cl) throws SystemException {
		return null;
	}
*/
}
