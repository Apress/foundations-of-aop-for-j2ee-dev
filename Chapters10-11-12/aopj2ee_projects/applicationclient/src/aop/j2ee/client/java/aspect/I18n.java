/*
 * Created on Feb 26, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package aop.j2ee.client.java.aspect;

import aop.j2ee.commons.exception.*;
import java.rmi.RemoteException;
import aop.j2ee.client.java.aspectized.BankAdmin;

/**
 * @author pawlakr
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public privileged aspect I18n {


  // Translate the messages of the exceptions
  Object around(String accountId) throws AccountNotFoundException: 
  call(* aop.j2ee.business.session.bank.Bank+.getAccountDetails(String) throws *Exception)
  && args(accountId)
  && within(aop.j2ee.client.java.aspectized.*+) {
    Object value = null;
    try {
      value = proceed(accountId);
    } catch (AccountNotFoundException ex) {
        throw new AccountNotFoundException(
        BankAdmin.messages.getString("AccountException")
          + " "
          + accountId
          + " "
          + BankAdmin.messages.getString("NotFoundException"),ex);
    }
    return value;
  }

  // Translate the messages of the common exceptions
  Object around() throws RemoteException, InvalidParameterException:
  call(* aop.j2ee.business.session.bank.Bank+.*(..) throws *Exception)
  && within(aop.j2ee.client.java.aspectized.*+) {
    Object value = null;
    try {
      value = proceed();
    } catch (RemoteException ex) {
      throw new RemoteException(BankAdmin.messages.getString("Remote Exception"),ex);
    } catch (InvalidParameterException ex) {
      throw new InvalidParameterException(BankAdmin.messages.getString("InvalidParameterException"),ex);
    }
    return value;
  }    

}
