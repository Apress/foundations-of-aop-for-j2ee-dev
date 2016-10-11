/*
* Created on Nov 12, 2003
*
*/

package aop.j2ee.commons.util.locator;

/**
 * @author T Murali
 *
 * Defines the interface for the lookup service
 */

public interface IServiceLocator 
{
	/**
	*  Obtains the initial context of a naming service if one exists.
	*/
	public Object getInitialContext();
	
	/**
	*  Locates and retrieves the object
	*  @param aClassInstance the object containing the details for the lookup service
	*  @return the instance of the requested object
	*/
	public  Object lookup(Object aClassInstance) throws Exception;

}
