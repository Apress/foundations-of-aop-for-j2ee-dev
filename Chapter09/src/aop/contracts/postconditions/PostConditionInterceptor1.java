// -------------------------------------------------------------------
// - Foundations of Aspect-Oriented Programming for J2EE Development -
// - Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -
// - APress                                                          -
// -                                                                 -
// - Chapter 9: Quality of Service and AOP                           -
// -------------------------------------------------------------------

package aop.contracts.postconditions;

import java.lang.reflect.Method;

import org.jboss.aop.Interceptor;
import org.jboss.aop.Invocation;
import org.jboss.aop.InvocationResponse;
import org.jboss.aop.InvocationType;
import org.jboss.aop.MethodInvocation;

/**
 * Interceptor postcondition 1.
 * 
 * @author Renaud Pawlak
 * @author Jean-Philippe Retaille
 * @author Lionel Seinturier
 */
public class PostConditionInterceptor1 implements Interceptor {

	public String getName() {
		return "PostConditionInterceptor1";
	}

	public InvocationResponse invoke(Invocation invocation) throws Throwable {
		InvocationResponse rsp = invocation.invokeNext();

		if (invocation.getType() == InvocationType.METHOD) {
			MethodInvocation methodInvocation = (MethodInvocation)invocation;
			Method method = methodInvocation.method;
			if ("sqrt".equals(method.getName())) {
				Double result = (Double) rsp.getResponse();
				if (result.doubleValue() < 0) {
					StringBuffer errorMsg = new StringBuffer();
					errorMsg.append(
                        "Conflicting postcondition for the sqrt method. Result is negative (");
					errorMsg.append(result);
					errorMsg.append(").");
					throw new Error(errorMsg.toString());
				}
			}
		}
		return rsp;
	}
}
