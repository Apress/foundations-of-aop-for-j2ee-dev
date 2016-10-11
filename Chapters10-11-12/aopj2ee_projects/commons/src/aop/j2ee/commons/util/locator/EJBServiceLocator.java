/*
 * Created on Nov 12, 2003
*
*/
package aop.j2ee.commons.util.locator;

import java.util.Hashtable;

import javax.ejb.EJBHome;
import javax.naming.NamingException;

/**
* @author T Murali
* @version 1.0
*/

public class EJBServiceLocator extends JNDIServiceLocator
{
	/** cached EJB Home interfaces */
	private static Hashtable hEjbHomes;	
	
	public EJBServiceLocator() throws Exception
	{
		super();
		hEjbHomes = new Hashtable();
	}
	
	/**
	*  Finds and retrieves the EJB Home interface for the specified class.
	*  @param aClass class object of the EJB Home to be retrieved
	*  @return EJBHome object belonging to the specified class
	*/
	public Object lookup(Object aClassInstance) throws ClassNotFoundException,NamingException
	{
		// Type cast
		Class aClass = (Class)aClassInstance;
		 
		EJBHome home = null;
		
		// first check the cache for the home object
		home = (EJBHome)hEjbHomes.get(aClass);
		if (home == null) 
		{
			// get the bean class name
			String beanClassName = aClass.getName();
	
			// Get the JNDI name for the bean class
			String jndiName = System.getProperty(beanClassName + ".jndiname");
			if (jndiName == null || jndiName.equals("")) 
				throw new RuntimeException("Unable to locate JNDI name for " + beanClassName);
		
			// Get the class name for the Home object
			String homeClassName = System.getProperty(beanClassName + ".homeclass");
			//m_logger.debug("HOME CLASS NAME " + homeClassName);
			if (homeClassName == null || homeClassName.equals("")) 
				throw new RuntimeException("Unable to locate HomeClass for " + beanClassName);
			Class homeClass = Class.forName(homeClassName);
	
			// Get the home
			Object jndiObject = initContext.lookup(jndiName);
			//m_logger.debug("JNDI CLASS NAME " + jndiObject.getClass().getName());
			home = (EJBHome)javax.rmi.PortableRemoteObject.narrow((org.omg.CORBA.Object)jndiObject,homeClass);
	
			// put the home object in the home cache and return
			hEjbHomes.put(aClass,home);			
		}
		return home;
	} // end of method lookupHome
	
} // end of class EJBServiceLocator
