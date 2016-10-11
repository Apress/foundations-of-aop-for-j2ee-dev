// -------------------------------------------------------------------
// - Foundations of Aspect-Oriented Programming for J2EE Development -
// - Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -
// - APress                                                          -
// -                                                                 -
// - Chapter 4: JAC                                                  -
// -------------------------------------------------------------------

package aop.jac;

import org.objectweb.jac.core.AspectComponent;

/**
 * In the Order management application,
 * this aspect component introduces a computeAmountAndPrint method
 * (see RoleMethodWrapper).
 * 
 * @author Renaud Pawlak
 * @author Jean-Philippe Retaille
 * @author Lionel Seinturier
 */
public class RoleMethodAspect extends AspectComponent {
    
    public RoleMethodAspect() {
        /**
         * Pointcut picking out addItem method execution.
         * After executing addItem, RoleMethodWrapper calls the intriduced
         * method computeAmountAndPrint.
         */
        pointcut(
            ".*", "aop.jac.Order", "addItem.*",
            "aop.jac.RoleMethodWrapper",
            null,false);        
    }
}
