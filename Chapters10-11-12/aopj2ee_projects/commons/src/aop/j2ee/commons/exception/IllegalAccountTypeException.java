/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package aop.j2ee.commons.exception;

/** This an application exception is thrown
 *  when an illegal account type is detected.
*/

public class IllegalAccountTypeException extends Exception {

    public IllegalAccountTypeException () { }

    public IllegalAccountTypeException (String msg) {
        super(msg);
    } 
  public IllegalAccountTypeException (String msg,Exception e) {
      super(msg,e);
  } 
}

