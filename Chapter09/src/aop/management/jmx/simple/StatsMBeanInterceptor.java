// -------------------------------------------------------------------
// - Foundations of Aspect-Oriented Programming for J2EE Development -
// - Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -
// - APress                                                          -
// -                                                                 -
// - Chapter 9: Quality of Service and AOP                           -
// -------------------------------------------------------------------

package aop.management.jmx.simple;

import org.jboss.aop.Interceptor;
import org.jboss.aop.Invocation;
import org.jboss.aop.InvocationResponse;

/**
 * Interceptor needed by the mixin.
 * 
 * @author Renaud Pawlak
 * @author Jean-Philippe Retaille
 * @author Lionel Seinturier
 */
public class StatsMBeanInterceptor implements Interceptor {
	public String getName() {
		return "StatsMBeanInterceptor";
	}

	public InvocationResponse invoke(Invocation invocation) throws Throwable {
		return invocation.invokeNext();
	}
}
