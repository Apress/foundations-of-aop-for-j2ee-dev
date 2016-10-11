/*
 * Created on Feb 26, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package aop.j2ee.commons.aspect;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.naming.NamingException;

import aop.j2ee.commons.exception.SystemException;

import aop.j2ee.commons.util.locator.*;

public abstract aspect AbstractLocator {

	private EJBServiceLocator ejbLocator;
	private JDBCServiceLocator jdbcConnectionLocator;
	private JMSServiceLocator jmsObjectLocator;


    // Pointcut to capture calls made to getServiceFacade.
    abstract protected pointcut ejbservice(Class aClass);

    // Pointcut to capture calls made to JDBCServiceLocator 
    // from within a getDatabaseConnection
    abstract protected pointcut connectionservice(String aDataSource);

    // Pointcut to capture calls made to JMSServiceLocator 
    // from within a getJMSObject method
    abstract protected pointcut jmsservice(String aJMSObject);

	abstract protected Object createService(Class aClass,Object home) throws Exception;

	abstract protected pointcut exception();
    
	// Soften all thrown exceptions
	declare soft : RemoteException : exception();
	//declare soft : TooLargeValueException : exception();

	// Extract the error message and relay them to the calling client
	Object around() : exception() {
		Object value = null;
		try {
			value = proceed();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return value;
	}

  Object around(Class aClass) throws SystemException : ejbservice(aClass) {
    System.out.println("entering service facade locator...");
        Object service = null;
    try {
      if (ejbLocator == null)
        ejbLocator = new EJBServiceLocator();
      Object home = ejbLocator.lookup(aClass);
      service = createService(aClass,home);
    } catch (NamingException ne) {
      throw new SystemException(ne.getMessage());
    } catch (ClassNotFoundException cne) {
      throw new SystemException(cne.getMessage());
    } catch (CreateException ce) {
      throw new SystemException(ce.getMessage());
    } catch (RemoteException re) {
      throw new SystemException(re.getMessage());
    } catch (Exception e) {
      throw new SystemException(e.getMessage());
    }
    return service;
  }

  Object around(String aDataSource)
    throws SystemException : connectionservice(aDataSource) {
    Object connection = null;
    try {
      if (jdbcConnectionLocator == null)
        jdbcConnectionLocator = new JDBCServiceLocator();
      connection = jdbcConnectionLocator.lookup(aDataSource);
    } catch (Exception ne) {
      throw new SystemException(ne.getMessage());
    }
    return connection;
  }

  Object around(String aName) throws SystemException : jmsservice(aName) {
    Object jmsObject = null;
    try {
      if (jmsObjectLocator == null)
        jmsObjectLocator = new JMSServiceLocator();
      jmsObject = jmsObjectLocator.lookup(aName);
    } catch (Exception ne) {
      throw new SystemException(ne.getMessage());
    }
    return jmsObject;
  }




}
