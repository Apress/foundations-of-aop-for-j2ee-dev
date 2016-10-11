package aop.j2ee.commons.util.locator;

import java.util.Hashtable;
import java.util.Properties;

import javax.ejb.EJBHome;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

/**
*  This class implements the J2EE Service Locator pattern.  It abstracts JNDI
*  usage and hides the complexities of initial context creation,the EJB home object
*  lookup and the DataSource lookup.  Multiple clients can use this object to reduce 
*  code complexity, provide  single point of control, and improve performance by 
*  providing a caching facility.
*/

public class ServiceLocator 
{
	/** reference to the JNDI provider URL */
	private static final String PROVIDER_URL = "providerurl";
	
	/** reference to the JNDI NAMING SERVICE */
	private static final String INITIAL_CONTEXT_FACTORY = "initialcontextfactory";
	
	/** reference to the single instance of the ServiceLocator */
	private static ServiceLocator singleton = null;	
    
	/** reference to the initial JNDI context */
	private static InitialContext initContext;
    
	/** cached EJB Home interfaces */
	private static Hashtable hEjbHomes;	
	
	/** reference to the Logging Framework */
	private static final Logger m_logger = Logger.getLogger(ServiceLocator.class.getName());
	
   	/**
   	*  Constructs the ServiceLocator and obtains the initial JNDI context.
   	*/
	private ServiceLocator() throws NamingException
	{
		initializeJNDIContext();
		hEjbHomes = new Hashtable();	
	}
	
	/**
   	*  Obtains the initial JNDI context.
   	*/
	public InitialContext getInitialContext() throws NamingException
	{
		if(initContext == null)
			initializeJNDIContext();
		return initContext;
	}
	
	/**
   	*  Retrieves the single instance of the ServiceLocator.
   	*  @return the ServiceLocator singleton
   	*/
	public static ServiceLocator getInstance() throws NamingException
	{
		m_logger.debug("Enter method");
		if (singleton == null) 
		{
			synchronized (ServiceLocator.class) 
			{
				singleton = new ServiceLocator();
			}
		}
		m_logger.debug("Exit method");
		return singleton;
	}

	/**
   	*  Retrieves the DataSource from the JNDI namespace
	*  @param aDataSource the DataSource Name
   	*  @return the DataSource instance
   	*/
	public javax.sql.DataSource getDataSource(String aDataSource) 
	throws NamingException
	{
		m_logger.debug("Enter method");
		DataSource connectionPoolDS = (DataSource)initContext.lookup(aDataSource);
		m_logger.debug("Exit method");
		return connectionPoolDS;  
	}

	/**
   	*  Retrieves the JMS QueueConnectionFactory from the JNDI namespace
	*  @param aQueueConnectionFactoryName the QueueConnectionFactoryName	
   	*  @return the javax.jms.QueueConnectionFactory instance
   	*/
	public javax.jms.QueueConnectionFactory getQueueConnectionFactory(String aQueueConnectionFactoryName)
	throws javax.naming.NamingException
	{
		m_logger.debug("Enter method");
		
		QueueConnectionFactory queueConnectionFactory = (QueueConnectionFactory)javax.rmi.PortableRemoteObject.narrow(
							initContext.lookup(aQueueConnectionFactoryName),
							QueueConnectionFactory.class);
		m_logger.debug("Exit method");
		return queueConnectionFactory;  
	}
	
	/**
   	*  Retrieves the JMS Queue from the JNDI namespace
	*  @param aQueueName the QueueName
   	*  @return the javax.jms.Queue instance
   	*/
	public javax.jms.Queue getQueue(String aQueueName) throws NamingException
	{
		m_logger.debug("Enter method");
		Queue queue = (Queue)javax.rmi.PortableRemoteObject.narrow(initContext.lookup(aQueueName),Queue.class);
		m_logger.debug("Exit method");
		return queue;  
	}
	
   	/**
   	*  Finds and retrieves the EJB Home interface for the specified class.
   	*  @param aClass class object of the EJB Home to be retrieved
   	*  @return EJBHome object belonging to the specified class
   	*/
	public EJBHome lookupHome(Class aClass) throws ClassNotFoundException,NamingException
	{
		m_logger.debug("Enter method");
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
			if (homeClassName == null || homeClassName.equals("")) 
				throw new RuntimeException("Unable to locate HomeClass for " + beanClassName);
			Class homeClass = Class.forName(homeClassName);
		
			// Get the home
      m_logger.debug("gets the home for "+homeClass.getName()+", name="+jndiName);
      Object jndiObject;
      //try { jndiObject = initContext.lookup("java:comp/env/ejb/bank"); } catch(Exception e) { e.printStackTrace(); }
      //try { jndiObject = initContext.lookup("bank"); } catch(Exception e) { e.printStackTrace(); }

      java.util.Enumeration e=initContext.list("");
      m_logger.debug(" ===> root");
      
      while(e.hasMoreElements()) {
        //jndiObject=e.nextElement();
        m_logger.debug(" ===> "+e.nextElement());
      }
  
      m_logger.debug(" ===> root/ejb");
      e=initContext.list(new javax.naming.CompositeName("ejb"));
      
      while(e.hasMoreElements()) {
        m_logger.debug(" ===> "+e.nextElement());
      }
      //e=initContext.list("*");
      //m_logger.debug(" ===> "+e.toString());
      //while(e.hasMoreElements()) {
      //  m_logger.debug(" ===> "+e.nextElement());
      //}
      //initContext=new InitialContext();
			jndiObject = initContext.lookup(jndiName);
      m_logger.debug(" ===> "+jndiObject);      
			home = (EJBHome)javax.rmi.PortableRemoteObject.narrow((org.omg.CORBA.Object)jndiObject,homeClass);
	
			// put the home object in the home cache and return
			hEjbHomes.put(aClass,home);			
		}
		m_logger.debug("Exit method");
		return home;
	} // end of method lookupHome
	
	/**
	*  Creates the JNDI Initial Context needed to perform future JNDI lookups.
	*/
	private void initializeJNDIContext() throws NamingException
	{
		m_logger.debug("Enter method");

		Properties props = new Properties();	

		// Get the location of the name service
		String providerUrl = System.getProperty(PROVIDER_URL);
		if (providerUrl != null && !providerUrl.equals("")) 
			props.put(Context.PROVIDER_URL, providerUrl);
		m_logger.debug(Context.PROVIDER_URL +  " = " + providerUrl);
		
		// Get the name of the intial context factory
		String contextFactory = System.getProperty(INITIAL_CONTEXT_FACTORY);
		if (contextFactory != null && !contextFactory.equals("")) 
			props.put(Context.INITIAL_CONTEXT_FACTORY, contextFactory);
		m_logger.debug(Context.INITIAL_CONTEXT_FACTORY + " = " + contextFactory);		
		
		// 	if no values supplied were for the provider and naming service, 
		//	call the default constructor.	
		if (props.size() == 0) 
			initContext = new InitialContext();
		else 
			initContext = new InitialContext(props);
		m_logger.debug("Exit method");
		
	} // end of method initializeJNDIContext
} // end of class ServiceLocator