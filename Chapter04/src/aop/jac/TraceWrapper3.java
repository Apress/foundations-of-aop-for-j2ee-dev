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
 * this wrapper displays trace messages and introspects joinpoints.
 * 
 * @author Renaud Pawlak
 * @author Jean-Philippe Retaille
 * @author Lionel Seinturier
 */
public class TraceWrapper3 extends Wrapper {

    public TraceWrapper3(AspectComponent ac) {
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
        
        String methodName = mi.getMethod().getName();
        Object[] args = mi.getArguments();
        Object callee = mi.getThis();
                
        System.out.println("-> Method "+methodName+" begins");
        System.out.print("-> "+args.length+" parameter(s) ");
        for (int i = 0; i < args.length; i++)
        System.out.print( args[i]+" " );
        System.out.println();
        System.out.println("-> to "+callee);

        Object ret = proceed(mi);

        System.out.println("<- Method "+methodName+" ends");
        return ret;
    }
    
}
