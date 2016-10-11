/*
 * Created on Feb 29, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package aop.j2ee.client.java.aspect;

import java.math.BigDecimal;
import java.util.Date;
import aop.j2ee.business.session.bank.Bank;
import aop.j2ee.commons.exception.*;
import java.rmi.RemoteException;
import aop.j2ee.client.java.aspectized.Simple;

/**
 * @author pawlakr
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public aspect Retry {

/*

  String around(String customerId,
  String type,
  String description,
  BigDecimal balance,
  BigDecimal creditLine,
  BigDecimal beginBalance,
  Date beginBalanceTimeStamp) throws
  RemoteException,
  IllegalAccountTypeException,
  CustomerNotFoundException,
  InvalidParameterException: 
  call(public String Bank+.createAccount(..))
  && within(Simple) && args(customerId,type,description,
  balance,creditLine,beginBalance,beginBalanceTimeStamp) {
    String result=null;
    try {
      result = proceed(customerId,type,description,
        balance,creditLine,beginBalance,beginBalanceTimeStamp);
    } catch (RemoteException ex) {
      try {
        Thread.sleep(1000);
        result = proceed(customerId,type,description,
          balance,creditLine,beginBalance,beginBalanceTimeStamp);
      } catch (InterruptedException ex2) {
        ex2.printStackTrace();
      }
    }
    return result;
  }
*/

  // generic alternative

  Object around(): 
  call(public * Bank+.*(..))
  && within(Simple) {
    Object result=null;
    try {
      result = proceed();
    } catch (RemoteException ex) {
      try {
        Thread.sleep(1000);
        result = proceed();
      } catch (InterruptedException ex2) {
        ex2.printStackTrace();
      }
    }
    return result;
  }


}
