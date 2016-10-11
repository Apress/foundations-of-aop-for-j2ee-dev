/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package aop.j2ee.business.util;

import aop.j2ee.commons.exception.*;
import java.math.BigDecimal;

/**
 * This helper class methods for getting and checking
 * the domains of business entity variables.
 */

public final class DomainUtil {

    public static BigDecimal bigZero = new BigDecimal("0.00");

    // The accountTypes array stores the valid account types.

    private static String[] accountTypes =
        {"Checking" , "Savings" , "Credit" , "Money Market" };

    public static String[] getAccountTypes()  {

        return accountTypes;
    } 

    public static void checkAccountType(String type)
        throws IllegalAccountTypeException {

        boolean foundIt = false;

        for (int i = 0; i < accountTypes.length ; i++) {
            if (accountTypes[i].equals(type))
                foundIt = true;
        }

        if (foundIt == false)
            throw new IllegalAccountTypeException(type);
    }

    public static boolean isCreditAccount(String type) {

        if (type.equals("Credit"))
            return true;
        else
            return false;
    }

} // DomainUtil
