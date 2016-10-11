// -------------------------------------------------------------------
// - Foundations of Aspect-Oriented Programming for J2EE Development -
// - Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -
// - APress                                                          -
// -                                                                 -
// - Chapter 9: Quality of Service and AOP                           -
// -------------------------------------------------------------------

package aop.patterns.singleton;

import org.jboss.aop.Interceptor;
import org.jboss.aop.Invocation;
import org.jboss.aop.InvocationResponse;
import org.jboss.aop.InvocationType;

/**
 * Interceptor for the singleton aspect.
 * 
 * @author Renaud Pawlak
 * @author Jean-Philippe Retaille
 * @author Lionel Seinturier
 */
public class SingletonInterceptor implements Interceptor {

	private InvocationResponse singleton;
		
	public String getName() {
		return "SingletonInterceptor";
	}

	public InvocationResponse invoke(Invocation invocation) throws Throwable {
		if (invocation.getType()==InvocationType.CONSTRUCTOR) {
			if (singleton==null) {
				System.out.println("Only once!");
				singleton = invocation.invokeNext();	
			}
			return singleton;
		}
		
		return invocation.invokeNext();
	}
}
