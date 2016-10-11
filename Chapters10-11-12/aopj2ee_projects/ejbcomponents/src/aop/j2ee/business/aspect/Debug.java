/*
 * Created on Mar 12, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package aop.j2ee.business.aspect;

/**
 * @author pawlakr
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public aspect Debug {

  declare precedence: *, Debug;

  pointcut toTrace():
  execution(public * aop.j2ee.business.entity.account.AccountPOJO.*(..)) ||
  execution(public * aop.j2ee.business.entity.customer.CustomerPOJO.*(..)) ||
  execution(public * aop.j2ee.business.entity.tx.TxPOJO.*(..)) ||
  execution(public * aop.j2ee.business.session.bank.BankPOJO.*(..)) ||
  execution(public * aop.j2ee.business.session.txcontroller.TxControllerPOJO.*(..));

  before(): toTrace() {
    aop.j2ee.commons.util.Debug.print(thisJoinPoint.getClass().getName()+" "+
    thisJoinPoint.getSignature().getName());
  }

}
