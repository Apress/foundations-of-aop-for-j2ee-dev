/*
 * Created on Feb 26, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package aop.j2ee.business.aspect;

import java.math.BigDecimal;
import aop.j2ee.business.entity.account.Account;
import aop.j2ee.business.session.txcontroller.TxControllerPOJO;
import aop.j2ee.business.session.bank.BankPOJO;
import aop.j2ee.commons.exception.*;
import java.rmi.RemoteException;
import javax.ejb.EJBException;
import aop.j2ee.commons.util.DomainUtil;

/**
 * @author pawlakr
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public aspect CheckBusinessConditions {

  pointcut setBalance(Account account, BigDecimal amount):
  call(
                  void Account.setBalance(BigDecimal))
                  && args(amount)
                  && target(account) 
          && within(TxControllerPOJO);

	before(Account account, BigDecimal amount)
		throws
			InsufficientFundsException,
			InsufficientCreditException : setBalance(account,amount) {

		try {
			String type = account.getType();

			if (DomainUtil.isCreditAccount(type)) {
				if (amount.compareTo(account.getCreditLine()) == 1)
					throw new InsufficientCreditException();
			} else {
				if (amount.compareTo(DomainUtil.bigZero) == -1)
					throw new InsufficientFundsException();
			}

		} catch (RemoteException ex) {
			throw new EJBException("transferFunds: " + ex.getMessage());
		}

	}

  before(String type)
    throws
      IllegalAccountTypeException: 
      execution(void BankPOJO.setAccountType(String,String))
      && args(type,String) {
  
    DomainUtil.checkAccountType(type);
      }

before(String type)
throws
  IllegalAccountTypeException: 
  execution(public String BankPOJO.createAccount(..))
  && args(String,type,String,BigDecimal,BigDecimal,BigDecimal,java.util.Date) { 
    DomainUtil.checkAccountType(type);
  }

}
