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
 * this aspect component defines a pointcut, traces method executions picked
 * out by this pointcut and instrospects joinpoints.
 * 
 * @author Renaud Pawlak
 * @author Jean-Philippe Retaille
 * @author Lionel Seinturier
 */
public class TraceAspect3 extends AspectComponent {
    
    /**
     * Calling the trace method from the aspect configuration file defines a
     * pointcut with the three given expressions. This pointcut will be
     * associated to the TraceWrapper3 wrapper.
     */
    public void trace( String objectPE, String classPE, String methodPE ) {
        pointcut(
            objectPE, classPE, methodPE,
            "aop.jac.TraceWrapper3",
            null,false);        
    }
}
