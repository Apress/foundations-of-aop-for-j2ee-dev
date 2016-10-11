/*
* Created on Nov 12, 2003
*/
 
package aop.j2ee.commons.util.locator;

import javax.sql.DataSource;

/**
* @author T Murali
* @version 1.0
*
*/

public class JDBCServiceLocator extends JNDIServiceLocator
{
	public JDBCServiceLocator() throws Exception
	{
		super();
	}
	
	/**
	*  Retrieves the DataSource Connection from the JNDI namespace
	*  @param aDataSource the DataSource Name
	*  @return the Connection instance
	*/
	public Object lookup(Object aDataSource) throws Exception
	{
		DataSource dataSource = (DataSource)initContext.lookup((String)aDataSource);
		return dataSource.getConnection();  
	}
}