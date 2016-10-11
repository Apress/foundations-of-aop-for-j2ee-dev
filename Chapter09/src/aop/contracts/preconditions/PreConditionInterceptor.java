// -------------------------------------------------------------------
// - Foundations of Aspect-Oriented Programming for J2EE Development -
// - Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -
// - APress                                                          -
// -                                                                 -
// - Chapter 9: Quality of Service and AOP                           -
// -------------------------------------------------------------------

package aop.contracts.preconditions;

import java.lang.reflect.Method;

import org.jboss.aop.Interceptor;
import org.jboss.aop.Invocation;
import org.jboss.aop.InvocationResponse;
import org.jboss.aop.InvocationType;
import org.jboss.aop.MethodInvocation;

/**
 * Interceptor precondition.
 * 
 * @author Renaud Pawlak
 * @author Jean-Philippe Retaille
 * @author Lionel Seinturier
 */
public class PreConditionInterceptor implements Interceptor {

	public String getName() {
		return "PreConditionInterceptor";
	}

	public InvocationResponse invoke(Invocation invocation) throws Throwable {
		
		if (invocation.getType() == InvocationType.METHOD) {
			MethodInvocation methodInvocation = (MethodInvocation)invocation;
			Method method = methodInvocation.method;
			if ("sqrt".equals(method.getName())) {
				Double parameter =
					(Double) methodInvocation.arguments[0];
				if (parameter.doubleValue() < 0) {
					StringBuffer errorMsg = new StringBuffer();
					errorMsg.append(
                        "Conflicting precondition for the sqrt method. The parameter is negative (");
					errorMsg.append(parameter);
					errorMsg.append(").");
					throw new Error(errorMsg.toString());
				}
			}
		}
		
		InvocationResponse rsp = invocation.invokeNext();
		return rsp;
	}
}
