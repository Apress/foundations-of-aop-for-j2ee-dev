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
 * In the Order management application,
 * this wrapper displays traces.
 * 
 * @author Renaud Pawlak
 * @author Jean-Philippe Retaille
 * @author Lionel Seinturier
 */
public class TraceWrapper extends Wrapper {

    public TraceWrapper(AspectComponent ac) {
        super(ac);
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
        System.out.println("-> Before addItem");
        Object ret = proceed(mi);
        System.out.println("-> After addItem");
        return ret;
    }
    
}
