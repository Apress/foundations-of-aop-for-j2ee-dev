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
 * Interceptor postcondition 2.
 * 
 * @author Renaud Pawlak
 * @author Jean-Philippe Retaille
 * @author Lionel Seinturier
 */
public class PostConditionInterceptor2 implements Interceptor {

	public String getName() {
		return "PostConditionInterceptor2";
	}

	public InvocationResponse invoke(Invocation invocation) throws Throwable {
		boolean incrementInvocation = false;
		int incrementParameterValue = 0;

		if (invocation.getType() == InvocationType.METHOD) {
			MethodInvocation methodInvocation = (MethodInvocation)invocation;
			Method method = methodInvocation.method;
			if ("increment".equals(method.getName())) {
				incrementInvocation = true;
				incrementParameterValue =
					((Integer) methodInvocation.arguments[0])
						.intValue();
			}
		}

		InvocationResponse rsp = invocation.invokeNext();

		if (incrementInvocation) {
			int result = ((Integer) rsp.getResponse()).intValue();
			if (result != (incrementParameterValue + 1)) {
				StringBuffer errorMsg = new StringBuffer();
				errorMsg.append(
                    "Conflicting postcondition for the increment method. The obtained result (");
				errorMsg.append(result);
				errorMsg.append(
                    ") differs from an increase by 1 unit of the given parameter (");
				errorMsg.append(incrementParameterValue);
				errorMsg.append(").");
				throw new Error(errorMsg.toString());
			}
		}
		return rsp;
	}
}
