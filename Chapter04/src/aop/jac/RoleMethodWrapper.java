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
import org.objectweb.jac.core.Wrappee;
import org.objectweb.jac.core.Wrapper;
import org.objectweb.jac.core.Wrapping;

/**
 * In the Order management application,
 * this wrapper introduces the computeAmountAndPrint method.
 * 
 * @author Renaud Pawlak
 * @author Jean-Philippe Retaille
 * @author Lionel Seinturier
 */
public class RoleMethodWrapper extends Wrapper {

    public RoleMethodWrapper(AspectComponent ac) {
        super(ac);
    }
    
    /**
     * Introducing a method.
     */
    public void computeAmountAndPrint( Wrappee o, String header ) {
        double amount = ((Order)o).computeAmount();
        System.out.println(
            header+"Order amount: US$"+amount);
    }

    /**
     * Intercepting constructor executions.
     */
    public Object construct(ConstructorInvocation ci) throws Throwable {
        return proceed(ci);
    }

    /**
     * Intercepting method executions.
     * The pointuct defined in RoleMethodAspect picks out addItem method
     * executions. After each execution, the introduced method is called.
     */
    public Object invoke(MethodInvocation mi) throws Throwable {
        Object ret = proceed(mi);
        Wrapping.invokeRoleMethod(
            (Wrappee) mi.getThis(), "computeAmountAndPrint",
            new Object[]{">> "}         
        );
        return ret;
    }
    
}
