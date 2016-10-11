package edu.rh.cs.j2ee.business;

/** 
*	This type is used to validate the Id of a person
*	@author T Murali
*	@version 1.0
*/

public class IdVerifier 
{
    /**
     *Validate the argument ID
     *@param aID the ID of the person 
     *@return <code>true</code> if the ID is valid
     */
    public boolean isValid(String aID) 
    {
		boolean result = true;
       	try
       	{
       		Integer.parseInt(aID);
       	}
       	catch(NumberFormatException ne)
       	{
       		result = false;
       	}
       return result;
    }
}