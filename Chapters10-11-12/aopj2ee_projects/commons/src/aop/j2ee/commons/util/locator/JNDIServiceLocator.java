/*
 * Created on Nov 12, 2003
*
*/
package aop.j2ee.commons.util.locator;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

//import org.apache.log4j.Logger;

/**
* @author T Murali
* @version 1.0
*/

public abstract class JNDIServiceLocator implements IServiceLocator
{
	/** reference to the JNDI provider URL */
	protected static final String PROVIDER_URL = "providerurl";
	
	/** reference to the JNDI NAMING SERVICE */
	protected static final String INITIAL_CONTEXT_FACTORY = "initialcontextfactory";
	
	/** reference to the initial JNDI context */
	protected static InitialContext initContext;
    
	/** reference to the Logging Framework */
	//private static final Logger m_logger = Logger.getLogger(JNDIServiceLocator.class.getName());
	
	/**
	*  Constructs the ServiceLocator and obtains the initial JNDI context.
	*/
	public JNDIServiceLocator() throws Exception
	{
		if(initContext == null)
			initializeJNDIContext();
	}
	
	/**
	*  Obtains the initial JNDI context.
	*/
	public Object getInitialContext()
	{
		return initContext;
	}
	
	public abstract Object lookup(Object aService) throws Exception;
	
			
	/**
	*  Creates the JNDI Initial Context needed to perform future JNDI lookups.
	*/
	protected void initializeJNDIContext() throws NamingException
	{
		Properties props = new Properties();	

		// Get the location of the name service
		String providerUrl = System.getProperty(PROVIDER_URL);
		if (providerUrl != null && !providerUrl.equals("")) 
			props.put(Context.PROVIDER_URL, providerUrl);
		//m_logger.debug(Context.PROVIDER_URL +  " = " + providerUrl);
		
		// Get the name of the intial context factory
		String contextFactory = System.getProperty(INITIAL_CONTEXT_FACTORY);
		if (contextFactory != null && !contextFactory.equals("")) 
			props.put(Context.INITIAL_CONTEXT_FACTORY, contextFactory);
		//m_logger.debug(Context.INITIAL_CONTEXT_FACTORY + " = " + contextFactory);		
		
		// 	if no values supplied were for the provider and naming service, 
		//	call the default constructor.	
		if (props.size() == 0) 
			initContext = new InitialContext();
		else 
			initContext = new InitialContext(props);

	} // end of method initializeJNDIContext
	
} // end of class JNDIServiceLocator