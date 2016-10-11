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
 *  when an illegal parameter is detected.
*/

public class InvalidParameterException extends Exception {

    public InvalidParameterException () { }

    public InvalidParameterException (String msg) {
        super(msg);
    } 
  public InvalidParameterException (String msg,Exception ex) {
      super(msg,ex);
  } 
}

