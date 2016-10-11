/*
 * Created on Feb 21, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package aop.j2ee.business.util;

import javax.rmi.PortableRemoteObject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import aop.j2ee.business.entity.account.*;
import aop.j2ee.business.entity.customer.*;
import aop.j2ee.business.entity.tx.*;
import aop.j2ee.business.session.bank.*;
import aop.j2ee.commons.util.CodedNames;

/**
 * This helper class fetches EJB home references.
 */

public final class EJBGetter {

    public static AccountHome getAccountHome() throws NamingException {

        InitialContext initial = new InitialContext();
        Object objref = initial.lookup(CodedNames.ACCOUNT_EJBHOME);
        return (AccountHome)
            PortableRemoteObject.narrow(objref, AccountHome.class);
    } 

    public static BankHome getBankHome() 
        throws NamingException {

        InitialContext initial = new InitialContext();
        Object objref = initial.lookup(CodedNames.ACCOUNT_CONTROLLER_EJBHOME);
        return (BankHome)
            PortableRemoteObject.narrow(objref, BankHome.class);
    } 

    public static CustomerHome getCustomerHome() throws NamingException {

        InitialContext initial = new InitialContext();
        Object objref = initial.lookup(CodedNames.CUSTOMER_EJBHOME);
        return (CustomerHome)
            PortableRemoteObject.narrow(objref, CustomerHome.class);
    }

/*    public static CustomerControllerHome getCustomerControllerHome() 
        throws NamingException {

        InitialContext initial = new InitialContext();
        Object objref = initial.lookup(CodedNames.CUSTOMER_CONTROLLER_EJBHOME);
        return (CustomerControllerHome)
            PortableRemoteObject.narrow(objref, CustomerControllerHome.class);
    }
*/
    public static TxHome getTxHome() throws NamingException {

        InitialContext initial = new InitialContext();
        Object objref = initial.lookup(CodedNames.TX_EJBHOME);
        return (TxHome)
            PortableRemoteObject.narrow(objref, TxHome.class);
    }

} // EJBGetter
