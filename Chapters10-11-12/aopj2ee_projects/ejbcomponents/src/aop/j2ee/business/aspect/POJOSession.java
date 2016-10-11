/*
 * Created on Feb 25, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package aop.j2ee.business.aspect;

import aop.j2ee.business.util.EJBGetter;
import aop.j2ee.business.aspect.marker.SessionBeanProtocol;

import javax.ejb.*;

/**
 * @author pawlakr
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public aspect POJOSession extends EJBResolver {

  // common session bean behavior
  declare parents : SessionBeanProtocol extends javax.ejb.SessionBean;

  private SessionContext SessionBeanProtocol.context;
  public void SessionBeanProtocol.setSessionContext(SessionContext context) {
    this.context= context;
  }
  public void SessionBeanProtocol.ejbRemove() {}
  public void SessionBeanProtocol.ejbActivate() {}
  public void SessionBeanProtocol.ejbPassivate() {}
  public void SessionBeanProtocol.ejbCreate() {

    try {
      if (txHome == null)
        txHome= EJBGetter.getTxHome();
      if (accountHome == null)
        accountHome= EJBGetter.getAccountHome();
      if (customerHome == null)
        customerHome= EJBGetter.getCustomerHome();
    } catch (Exception ex) {
      throw new EJBException("ejbCreate: " + ex.getMessage());
    }
  }

}
