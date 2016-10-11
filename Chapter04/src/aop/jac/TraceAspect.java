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
 * this aspect component traces the addItem method executions.
 * 
 * @author Renaud Pawlak
 * @author Jean-Philippe Retaille
 * @author Lionel Seinturier
 */
public class TraceAspect extends AspectComponent {
    
    public TraceAspect() {
        /**
         * This pointcut picks out addItem method executions.
         */
        pointcut(
            ".*","aop.jac.Order","addItem.*",
            "aop.jac.TraceWrapper",
            null,false);
    }

}
