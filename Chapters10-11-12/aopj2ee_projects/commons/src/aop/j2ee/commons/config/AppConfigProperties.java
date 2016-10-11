package aop.j2ee.commons.config;

/**
 * <code>AppConfigProperties</code> is a concrete class which delivers container-specific configuration information 
 * about the Java Virtual Machine instance the application is running under.This is the default configuration class,
 * which tries to pick up -D options on the command line to locate the main application property file and the 
 * application configuration directory.  The rest of the components in the application use this class to pick up 
 * configuration options in property files<p>
 *
 * The current directory (.) where the java start command is invoked is the default for application configuration 
 * directory.
 * 
 * The main application property file is App.properties in that directory.  
 * 
 * These can be overridden by something like:
 * <pre>
 *    java 	-Daop.j2ee.commons.config.applicationConfigDir=/home/user/aopj2ee 
 * 			-Daop.j2ee.commons.config.applicationPropertyFile=aopj2ee.properties 
 * 			...
 * </pre>
 *
 * This class is package private and is wrapped by the AppInfo class.This class should have concrete subclasses 
 * which are either web-container-specific or ejb-container-specific(for example,<code>eCREJBConfigProperties</code>).
 *
 * @see AppInfo
*/

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AppConfigProperties
{
	/**
	* The System property name (one of the -D options on the java command line
	* start directive) which contains the current application name
	*/
	public static final String APPLICATION_NAME_PROPERTY = "aop.j2ee.commons.config.applicationname";
		
	/**
    * The System property name which contains the location of the main property file.
    * This is one of the -D options on the java command line.  
    */
    public static final String APPLICATION_PROPERTY_FILE_PROPERTY = "aop.j2ee.commons.config.applicationpropertyfile";

	/**
	* The System property name (one of the -D options on the java command line
	* start directive) which contains the directory location of property files
	* used throughout the framework.
	*/
	public static final String APPLICATION_CONFIGURATION_DIRECTORY_PROPERTY = "aop.j2ee.commons.config.applicationconfigdir";

	/**
	* The application name
	*/
    public static final String DEFAULT_APPLICATION_NAME = "App";
    
    /**
    * The default property file name if none can be found by using the above setting.
    */
	public static final String DEFAULT_APPLICATION_PROPERTY_FILE = DEFAULT_APPLICATION_NAME + ".properties";

    /**
    * The default value for the application configuration directory.
    */
    public static final String DEFAULT_APPLICATION_CONFIGURATION_DIRECTORY = ".";

	/**
	* Retrieves the main application configuration directory.  This directory should be the same directory as 
	* where the fully-qualified main application property file is located.
	*/
    public String getApplicationConfigurationDirectory()
    {
        return System.getProperty(APPLICATION_CONFIGURATION_DIRECTORY_PROPERTY,DEFAULT_APPLICATION_CONFIGURATION_DIRECTORY);
    }

    /**
    * Retrieves the name of the main application property file
    */
    public String getApplicationPropertyFileName()
    {
		return System.getProperty(APPLICATION_PROPERTY_FILE_PROPERTY,DEFAULT_APPLICATION_PROPERTY_FILE);
    }

    /**
    * Retrieves the name for the application to uniquely identify it from other
    * Java virtual machine instances running on the network.
    *
    * The default implementation at this level is host name concatenated with
    */
    public String getApplicationName()
    {
		return System.getProperty(APPLICATION_NAME_PROPERTY,DEFAULT_APPLICATION_NAME);
    }
    
	/**
	* Retrieves the configured application properties from the application-propeties
	* file and loads it into the runtime environment. The method is package-private
	* so that only the AppInfo can access it. 
	*
	*/
    void loadProperties()
    {
		try
		{
			Properties props = System.getProperties();
			String fileName = 	getApplicationConfigurationDirectory() + 
								System.getProperty("file.separator","\\") +
								getApplicationPropertyFileName();
			props.load(new FileInputStream(fileName));
			System.setProperties(props);
		}
		catch(IOException ioe)
		{
			throw new RuntimeException("Unable to load Application Properties");	
		}
    }
}