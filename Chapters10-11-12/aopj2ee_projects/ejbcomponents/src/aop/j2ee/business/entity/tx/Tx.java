/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package aop.j2ee.business.entity.tx;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;
import aop.j2ee.commons.to.TxDetails;

public interface Tx extends EJBObject {

    public TxDetails getDetails() 
        throws RemoteException;

} // Tx
