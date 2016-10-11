/*
 * Created on Feb 26, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package aop.j2ee.client.java.regular;

import java.math.BigDecimal;
import aop.j2ee.client.delegate.BankDelegate;
import aop.j2ee.commons.to.AccountDetails;
import java.util.Date;

import aop.j2ee.commons.config.AppInfo;

public class Simple {

	public static void main(String[] args) {

    System.out.println("launching "+AppInfo.getInstance().getApplicationName()+" ("+AppInfo.getInstance().getApplicationConfigurationDirectory());

		try {
			BankDelegate deleguate = new BankDelegate();
			String customerId =
				deleguate.createCustomer(
					"Pawlak",
					"Renaud",
					"P",
					"Frederick St",
					"Hartford",
					"CT",
					"06105",
					"NA",
					"renaud@aopsys.com");
			System.out.println("Created new customer " + customerId);
			String accountId =
				deleguate.createAccount(
					customerId,
					"Debit",
					"This is a test.",
					new BigDecimal(100),
					new BigDecimal(0),
					new BigDecimal(100),
					new Date());
			System.out.println("Created new customer " + accountId);
			deleguate.setAccountBalance(new BigDecimal(200), accountId);
			System.out.println("Changed balance");
			AccountDetails details = deleguate.getAccountDetails(accountId);
			System.out.println("Account details:");
			System.out.println(details);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
