/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package aop.j2ee.commons.exception;

/** This application exception 
 *  indicates that a primary key is missing.
*/

public class MissingPrimaryKeyException extends Exception {

    public MissingPrimaryKeyException () { }

    public MissingPrimaryKeyException (String msg) {
        super(msg);
    } 
   public MissingPrimaryKeyException (String msg,Exception e) {
        super(msg,e);
    } 
}

