/*
 * Created on Mar 2, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package aop.j2ee.business.aspect;

import aop.j2ee.business.session.txcontroller.TxControllerPOJO;
import aop.j2ee.business.entity.account.Account;
import javax.ejb.EJBException;

public abstract privileged aspect Transaction {

  javax.transaction.UserTransaction ut;

  after(TxControllerPOJO controller) : execution(
    void TxControllerPOJO.beginTx())
    && withincode(* TxControllerPOJO.transferFunds(..))
    && this(controller) {
    ut= controller.context.getUserTransaction();
    try {
      ut.begin();
    } catch (Exception e) {
      throw new EJBException("transferFunds: " + e.getMessage());
    }
  }

  after() : call(void TxControllerPOJO.endTx())
    && withincode(* TxControllerPOJO.transferFunds(..)) {

    try {
      ut.commit();
      ut= null;
    } catch (Exception ex) {
      try {
        ut.rollback();
      } catch (Exception e) {
        throw new EJBException("transferFunds: " + e.getMessage());
      }
      throw new EJBException("transferFunds: " + ex.getMessage());
    }
  }

  after()throwing(Exception ex)
    throws
      EJBException : call(* Account +.* (..))
      && withincode(* TxControllerPOJO.transferFunds(..)) {
    try {
      if (ut != null)
        ut.rollback();
    } catch (Exception e) {
      throw new EJBException("transferFunds: " + e.getMessage());
    }
    throw new EJBException("transferFunds: " + ex.getMessage());
  }

  /*
  
    Object around(TxControllerPOJO controller) : 
      execution(* TxControllerPOJO.transferFunds(..))
      && this(controller) {
  
    Object result;
  
      try {
        controller.context.getUserTransaction().begin();
      } catch (Exception e) {
        throw new EJBException("transferFunds: " + e.getMessage());
      }
  
      try {
        result=proceed(controller);
        controller.context.getUserTransaction().commit();
  
      } catch (Exception ex) {
        try {
          controller.context.getUserTransaction().rollback();
        } catch (Exception e) {
          throw new EJBException("transferFunds: " + e.getMessage());
        }
        throw new EJBException("transferFunds: " + ex.getMessage());
      }
  
      return result;
  
    }
  
  */

  /*
  
  final static Integer zero= new Integer(0);
  final static Integer one= new Integer(1);
  
  javax.transaction.UserTransaction ut;
  
  ThreadLocal getTypeCCount= new ThreadLocal();
  ThreadLocal createTxCCount= new ThreadLocal();
  
  before() : execution(* TxControllerPOJO.transferFunds(..)) {
    getTypeCCount.set(zero);
    createTxCCount.set(zero);
  }
  before(TxControllerPOJO controller) : call(* Account +.getType(..))
    && withincode(* TxControllerPOJO.transferFunds(..)) && this(controller) {
  
    if (getTypeCCount.get() == zero) {
      ut= controller.context.getUserTransaction();
      try {
        ut.begin();
      } catch (Exception e) {
        throw new EJBException("transferFunds: " + e.getMessage());
      }
    }
    getTypeCCount.set(new Integer(((Integer)getTypeCCount.get()).intValue()+1));
  }
  
  after() : call(* TxControllerPOJO.createTx(..))
    && withincode(* TxControllerPOJO.transferFunds(..)) {
  
    if (createTxCCount.get() == one) {
      try {
        ut.commit();
      } catch (Exception ex) {
        try {
          ut.rollback();
        } catch (Exception e) {
          throw new EJBException("transferFunds: " + e.getMessage());
        }
        throw new EJBException("transferFunds: " + ex.getMessage());
      }
    }
  
  }
  after()throwing(Exception ex)
    throws
      EJBException : call(* Account+.*(..))
      && withincode(* TxControllerPOJO.transferFunds(..)) {
    try {
      ut.rollback();
    } catch (Exception e) {
      throw new EJBException("transferFunds: " + e.getMessage());
    }
    throw new EJBException("transferFunds: " + ex.getMessage());
  }
  
  */
}