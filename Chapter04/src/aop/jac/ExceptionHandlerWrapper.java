// -------------------------------------------------------------------
// - Foundations of Aspect-Oriented Programming for J2EE Development -
// - Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -
// - APress                                                          -
// -                                                                 -
// - Chapter 4: JAC                                                  -
// -------------------------------------------------------------------

package aop.jac;

import org.aopalliance.intercept.ConstructorInvocation;
import org.aopalliance.intercept.MethodInvocation;
import org.objectweb.jac.core.AspectComponent;
import org.objectweb.jac.core.Wrapper;

/**
 * Defining an exception handler.
 * 
 * @author Renaud Pawlak
 * @author Jean-Philippe Retaille
 * @author Lionel Seinturier
 */
public class ExceptionHandlerWrapper extends Wrapper {

    public ExceptionHandlerWrapper(AspectComponent ac) {
        super(ac);
    }
    
    /**
     * Exception handler.
     */
    public Object myHandler(Exception e) throws Exception {
        System.out.println(">> Exception caught: "+e);
        return null;
    }

    /**
     * Intercepting constructor executions.
     */
    public Object construct(ConstructorInvocation ci) throws Throwable {
        return proceed(ci);
    }

    /**
     * Intercepting method executions.
     */
    public Object invoke(MethodInvocation mi) throws Throwable {
        return proceed(mi);
    }
    
}
