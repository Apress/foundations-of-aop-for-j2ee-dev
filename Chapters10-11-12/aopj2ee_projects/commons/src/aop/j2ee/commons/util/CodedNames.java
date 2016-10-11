/*
 * Created on Feb 21, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package aop.j2ee.commons.util;

/**
 * @author pawlakr
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface CodedNames {

        public static final String BANK_DATABASE =
        "java:comp/env/jdbc/BankDB"; 

        public static final String ACCOUNT_EJBHOME =
            "java:comp/env/ejb/account";

        public static final String ACCOUNT_CONTROLLER_EJBHOME =
            "java:comp/env/ejb/accountController";

        public static final String CUSTOMER_EJBHOME =
            "java:comp/env/ejb/customer";

        public static final String CUSTOMER_CONTROLLER_EJBHOME =
            "java:comp/env/ejb/customerController";

        public static final String TX_EJBHOME =
            "java:comp/env/ejb/tx";

        public static final String TX_CONTROLLER_EJBHOME =
            "java:comp/env/ejb/txController";


}
