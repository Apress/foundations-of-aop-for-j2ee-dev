// -------------------------------------------------------------------
// - Foundations of Aspect-Oriented Programming for J2EE Development -
// - Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -
// - APress                                                          -
// -                                                                 -
// - Chapter 5: JBoss AOP                                            -
// -------------------------------------------------------------------

package aop.jboss;

import java.util.Date;

import org.jboss.aop.Invocation;
import org.jboss.aop.InvocationResponse;
import org.jboss.aop.Interceptor;
import org.jboss.aop.MethodInvocation;


/**
 * In the Order management application,
 * this interceptor uses the introduced interface CalendarItf.
 * 
 * @author Renaud Pawlak
 * @author Jean-Philippe Retaille
 * @author Lionel Seinturier
 */
public class CalendarInterceptor implements Interceptor {
    
    public String getName() { return "CalendarInterceptor"; }
    
    /**
     * Intercepting method.
     */
    public InvocationResponse invoke(Invocation invocation) throws Throwable {

        InvocationResponse rsp = invocation.invokeNext();

        if ( invocation instanceof MethodInvocation ) {

            MethodInvocation mi = (MethodInvocation) invocation;
            if ( mi.method.getName().equals("computeAmount") ) {

                Object target = invocation.targetObject;
                Date date = ((CalendarItf)target).getDate();
                System.out.println("Order creation date: "+date);        
            }
        }
        
        return rsp;
    }

}
