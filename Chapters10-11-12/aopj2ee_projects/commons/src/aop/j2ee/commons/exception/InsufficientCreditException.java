/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package aop.j2ee.commons.exception;

/** This application exception indicates that
 *  the credit line of an account is not large
 *  enough to perform an operation.
*/

public class InsufficientCreditException extends RuntimeException {

    public InsufficientCreditException () { }

    public InsufficientCreditException (String msg) {
        super(msg);
    } 
  public InsufficientCreditException (String msg,Exception e) {
      super(msg,e);
  } 
}

