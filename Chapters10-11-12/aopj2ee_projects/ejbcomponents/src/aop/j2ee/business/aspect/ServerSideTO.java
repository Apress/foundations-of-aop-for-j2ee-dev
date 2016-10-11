/*
 * Created on Feb 27, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package aop.j2ee.business.aspect;

import aop.j2ee.commons.aspect.to.*;
import aop.j2ee.business.session.bank.*;
import java.rmi.RemoteException;
import aop.j2ee.commons.exception.*;


/**
 * @author pawlakr
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public aspect ServerSideTO {

/*
	public abstract String Bank.createAccountWithCustomer(
		CustomerAndAccountInfos infos)
		throws
			RemoteException,
			IllegalAccountTypeException,
			CustomerNotFoundException,
			InvalidParameterException;

	public String BankBean.createAccountWithCustomer(
		CustomerAndAccountInfos infos)
		throws
			RemoteException,
			IllegalAccountTypeException,
			CustomerNotFoundException,
			InvalidParameterException
	{
		String customerId =
			createCustomer(
				infos.getLastName(),
				infos.getFirstName(),
				infos.getMiddleInitial(),
				infos.getStreet(),
				infos.getCity(),
				infos.getState(),
				infos.getZip(),
				infos.getPhone(),
				infos.getEmail());

		String accountId =
			createAccount(
				customerId,
				infos.getType(),
				infos.getDescription(),
				infos.getBalance(),
				infos.getCreditLine(),
				infos.getBeginBalance(),
				infos.getBeginBalanceTimeStamp());

		return accountId;

	}

*/
}
