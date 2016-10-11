package aop.j2ee.commons.config;

import java.net.*;

/**
 * AppInfo is a singleton which delivers information about the host and the JVM instance the application is 
 * running under. It encapsulates the application configuration properties specific to the type of JVM the 
 * application is using.  For example, the default application configuration properties class is 
 * edu.rh.cs.base.j2ee.config.AppConfigProperties. 
 * This can be overriden by specifying a -D option on the command line as shown below 
 *
 * <pre>
 * java ... -Daop.j2ee.config.appPropertiesClass=edu.rh.cs.base.j2ee.config.WebLogicConfigProperties ...
 * 			-Daop.j2ee.config.applicationconfigdir=c:/temp/j2eepatterns
 * 			-Daop.j2ee.config.applicationpropertyfile=j2eepatterns.properties
 * </pre>
 *
 * and the subclass of AppConfigProperties named aop.j2ee.config.WebLogicConfigProperties
 * will kick in, which can override any of the methods within the AppConfigProperties class.
 *
 * @see AppConfigProperties
*/

public class AppInfo
{
    /**
    * The string constant used to delimit strings
    */
    public static final String DELIMITER = "-";

    /**
    * The System property name (one of the -D options on the command line) which contains the name of the 
    * AppConfigProperties class.  If this property is not found, the default below is used.
    */
    public static final String DEFAULT_APPLICATION_PROPERTY_NAME = "aop.j2ee.commons.config.appPropertiesClass";

    /**
    *  The default AppConfigProperties subclass if the above application property is not specified on the command line.
    */
	public static final String DEFAULT_APPLICATION_PROPERTIES_CLASS = "aop.j2ee.commons.config.AppConfigProperties";

	/**
	* The default IP Address
	*/
	public static final String DEFAULT_IP_ADDRESS = "127.0.0.1";
	
    /**
    * Holds the lazy-initialized instance of a subclass of AppConfigProperties which responds with all 
    * container-specific information.
    */
	protected AppConfigProperties appConfigPropertiesObject;

	/**
	* Holds the lazy-initialized host name of the local host.
	*/
	protected String localHostName;

	/**
	* Holds the lazy-initialized IP address of the local host.
	*/
	protected String localHostAddress;

    /**
    * Singleton instance of this class.
    */
	private static final AppInfo singleton = new AppInfo();

	/**
	* Control instance-creation by privatizing the constructor.
	*/
	private AppInfo()
	{
		makeAppConfigPropertiesObject();
		appConfigPropertiesObject.loadProperties();
	}

	/**
	* Returns the the single instance of this class
	*/
	public static AppInfo getInstance()
	{
		return singleton;
	}

    /**
    * Return the string for the subclass of AppConfigProperties which should be
    * used to retrieve container-specific application properties.
    */
    protected String getAppConfigPropertiesClass()
    {
        return System.getProperty(DEFAULT_APPLICATION_PROPERTY_NAME,DEFAULT_APPLICATION_PROPERTIES_CLASS);
    }

    /**
    * Return the subclass of AppConfigProperties which responds all container-specific
    * application information.
    */
    protected synchronized void makeAppConfigPropertiesObject()
    {
	    // If not initialized yet, do so
		if (appConfigPropertiesObject == null)
		{
            String appConfigPropertiesClassName = getAppConfigPropertiesClass();
		    try
		    {
		        appConfigPropertiesObject = (AppConfigProperties)Class.forName(getAppConfigPropertiesClass()).newInstance();
		    }
		    catch (Throwable e)
		    {
		        System.err.println("ERROR: AppInfo.getAppConfigPropertiesObject() caught exception " + e + ", trying to get an instance of AppConfigProperties class " + appConfigPropertiesClassName);
		        appConfigPropertiesObject = new AppConfigProperties();
		    }
		}
    }

	/**
	* Retrieves the main application configuration directory.  This directory should be the same directory as where 
	* the fully-qualified main application property file is located.  This is a passthrough to the AppConfigProperties 
	* object.
	*/
    public String getApplicationConfigurationDirectory()
    {
        return appConfigPropertiesObject.getApplicationConfigurationDirectory();
    }

    /**
    * Retrieves the name of the main application property file.  This is a
    * passthrough to the AppConfigProperties object.
    */
    public String getApplicationPropertyFileName()
    {
        return appConfigPropertiesObject.getApplicationPropertyFileName();
    }

    /**
    * Retrieves the name for the application to uniquely identify it from other
    * Java virtual machine instances running on the network.  This is a
    * passthrough to the AppConfigProperties object.
    */
    public String getApplicationName()
    {
        return appConfigPropertiesObject.getApplicationName();
    }

	/**
	* Return the machine name of the local host
	*/
	public synchronized String getLocalHostAddress()
	{
        // If not initialized yet, do so
        if (localHostAddress == null)
		{
            try
		    {
                localHostAddress = InetAddress.getLocalHost().getHostAddress();
            }
	        catch (UnknownHostException e)
	        {
	            localHostAddress = DEFAULT_IP_ADDRESS;
	        }
		}
		return localHostAddress;
	}

	/**
	* Return the machine name of the local host
	*/
	public synchronized String getLocalHostName()
	{
        // If not initialized yet, do so
        if (localHostName == null)
		{
            try
		    {
                localHostName = InetAddress.getLocalHost().getHostName();
            }
	        catch (UnknownHostException e)
	        {
	            localHostName = "unknown";
	        }
		}
		return localHostName;
	}
}
