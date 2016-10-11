// -------------------------------------------------------------------
// - Foundations of Aspect-Oriented Programming for J2EE Development -
// - Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -
// - APress                                                          -
// -                                                                 -
// - Chapter 5: JBoss AOP                                            -
// -------------------------------------------------------------------

package aop.jboss;

import org.jboss.aop.Invocation;
import org.jboss.aop.InvocationResponse;
import org.jboss.aop.Interceptor;
import org.jboss.aop.MethodInvocation;

/**
 * In the Order management application,
 * this interceptor displays trace messages.
 * 
 * @author Renaud Pawlak
 * @author Jean-Philippe Retaille
 * @author Lionel Seinturier
 */
public class MetadataInterceptor implements Interceptor {
    public String getName() {
        return "MetadataInterceptor";
    }

    /**
     * Intercepting method.
     */
    public InvocationResponse invoke(Invocation invocation) throws Throwable {
        
        MethodInvocation mi = (MethodInvocation) invocation;
        String methodName = mi.method.getName();

        if ( methodName.equals("addItem") ) {
            String data = (String) invocation.getMetaData("toBeTraced","tracedMethod");        
            System.out.println(
                "-> Method "+methodName+" metadata toBeTraced tracedMethod = "+data);
        }
        
        return invocation.invokeNext();
    }
}
