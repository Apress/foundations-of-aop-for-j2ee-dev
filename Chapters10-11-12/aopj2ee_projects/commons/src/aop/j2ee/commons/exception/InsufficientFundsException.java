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
 *  an account has insufficient funds to perform
 *  an operation.
*/

public class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException () { }

    public InsufficientFundsException (String msg) {
        super(msg);
    } 
  public InsufficientFundsException (String msg,Exception e) {
      super(msg,e);
  } 
}

