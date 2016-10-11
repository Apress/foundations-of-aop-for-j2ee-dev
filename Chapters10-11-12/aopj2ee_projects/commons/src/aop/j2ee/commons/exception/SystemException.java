/*
* Created on Nov 5, 2003
*
*/
package aop.j2ee.commons.exception;

public class SystemException extends Exception 
{
	public SystemException()
	{
		super();
	}
	
	public SystemException(String aMessage)
	{
		super(aMessage);
	}
	
	public SystemException(String aMessage,Throwable aThrowable)
	{
		super(aMessage,aThrowable);
	}
	
	public SystemException(Throwable aThrowable)
	{
		super(aThrowable);
	}
}