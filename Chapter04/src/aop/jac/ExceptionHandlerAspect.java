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
 * This aspect component introduces an exception handler
 * (see ExceptionHandlerWrapper).
 * 
 * @author Renaud Pawlak
 * @author Jean-Philippe Retaille
 * @author Lionel Seinturier
 */
public class ExceptionHandlerAspect extends AspectComponent {
    
    public ExceptionHandlerAspect() {
        /**
         * Defining an exception handler
         * (method myHandler in class ExceptionHandlerWrapper)
         * for this pointcut.
         */
        pointcut(
            ".*", "aop.jac.GenerateExcept", "run():void",
            "aop.jac.ExceptionHandlerWrapper",
            "myHandler",false);
    }
}
