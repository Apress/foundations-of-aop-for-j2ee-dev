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
public class TraceInterceptor implements Interceptor {
    public String getName() {
        return "TraceInterceptor";
    }

    /**
     * Intercepting method.
     */
    public InvocationResponse invoke(Invocation invocation) throws Throwable {

        MethodInvocation mi = (MethodInvocation) invocation;
        String methodName = mi.method.getName();

        System.out.println("Before "+methodName);
        InvocationResponse rsp = invocation.invokeNext();
        System.out.println("After "+methodName);
        return rsp;
    }
}
