// -------------------------------------------------------------------
// - Foundations of Aspect-Oriented Programming for J2EE Development -
// - Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -
// - APress                                                          -
// -                                                                 -
// - Chapter 3: AspectJ                                              -
// -------------------------------------------------------------------

package aop.aspectj;

import java.util.Date;
import java.util.Map;

/**
 * In the Order management application,
 * this aspect traces the calls to all the methods defined in the Order class.
 * 
 * @author Renaud Pawlak
 * @author Jean-Philippe Retaille
 * @author Lionel Seinturier
 */
public aspect TraceAspect2 {
    
    /**
     * This pointcut picks out calls to methods defined in the Order class.
     */
    pointcut toBeTraced(): call( * aop.aspectj.Order.*(..) );
    
    /**
     * Advice code that displays trace messages.
     */
    Object around(): toBeTraced() {
        System.out.println("-> Before call");
        Object ret = proceed();
        System.out.println("-> After call");
        return ret;
    }
        
}
