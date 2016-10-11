/*
 * Created on Feb 24, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package aop.j2ee.business.aspect;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;

import aop.j2ee.business.session.txcontroller.TxControllerPOJO;
import aop.j2ee.commons.exception.*;
import aop.j2ee.commons.util.DomainUtil;
import aop.j2ee.commons.exception.InvalidParameterException;
import aop.j2ee.business.session.bank.BankPOJO;

/**
 * @author pawlakr
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

public aspect CheckArgs {

/*
  before(Date d1, Date d2, String accountId)
    throws
      InvalidParameterException : execution(
      void TxControllerPOJO.* (Date, Date, String))
      && args(d1, d2, accountId) {
    if (d1 == null)
      throw new InvalidParameterException("null startDate");

    if (d2 == null)
      throw new InvalidParameterException("null endDate");

    if (accountId == null)
      throw new InvalidParameterException("null accountId");
  }

  before(String txId)
    throws
      InvalidParameterException : execution(
      void TxControllerPOJO.getDetails(String))
      && args(txId) {
    if (txId == null)
      throw new InvalidParameterException("null txId");
  }

  before(BigDecimal amount, String description, String accountId)
    throws
      InvalidParameterException : execution(
      void TxControllerPOJO.* (BigDecimal, String, String))
      && args(amount, description, accountId) {
    checkAccountArgs(amount, description, accountId);
  }

  before(
    BigDecimal amount,
    String description,
    String fromAccountId,
    String toAccountId)
    throws
      InvalidParameterException : execution(
      void TxControllerPOJO.* (BigDecimal, String, String, String))
      && args(amount, description, fromAccountId, toAccountId) {
    checkAccountArgs(amount, description, fromAccountId);
    checkAccountArgs(amount, description, toAccountId);
  }

  private void checkAccountArgs(
    BigDecimal amount,
    String description,
    String accountId)
    throws InvalidParameterException {
    if (description == null)
      throw new InvalidParameterException("null description");
    if (accountId == null)
      throw new InvalidParameterException("null accountId");
    if (amount.compareTo(DomainUtil.bigZero) != 1)
      throw new InvalidParameterException("amount <= 0");
  }

  before(Object value, String accountId)
    throws
      InvalidParameterException : (
      execution(void BankPOJO.setAccount*(*, String))
        || execution(void BankPOJO.*CustomerFromAccount*(*, String)))
      && args(value, accountId) {
    if (value == null)
      throw new InvalidParameterException("null value");
    if (accountId == null)
      throw new InvalidParameterException("null accountId");

  }
*/

  // This is a generic alternative

  static Set NULL_ALLOWED=new HashSet();
  static {
    NULL_ALLOWED.add("setCustomerName,2"); // middleInitial
    NULL_ALLOWED.add("setCustomerAddress,3"); // zip
    NULL_ALLOWED.add("setCustomerAddress,4"); // phone
    NULL_ALLOWED.add("setCustomerAddress,5"); // email
  }

  before()
    throws InvalidParameterException :
    execution(public void *POJO.* (..)) {
    Object[] args= thisJoinPoint.getArgs();
    for (int i= 0; i < args.length;i++) {
      if (args[i] == null && 
      !NULL_ALLOWED.contains(thisJoinPoint.getSignature().getName()+","+i))
        throw new InvalidParameterException(
          thisJoinPoint.getSignature() + " arg " + i + " is null");
    }
  }

}
