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
 * this aspect traces the calls to the addItem method.
 * 
 * @author Renaud Pawlak
 * @author Jean-Philippe Retaille
 * @author Lionel Seinturier
 */
public aspect TraceAspect {
    
    /**
     * This pointcut picks out calls to the addItem method.
     */
    pointcut toBeTraced():
        call( public void aop.aspectj.Order.addItem(String,int) );
    
    /**
     * Advice code that displays trace messages.
     */
    void around(): toBeTraced() {
        System.out.println("-> Before calling addItem");
        proceed();
        System.out.println("-> After calling addItem");
    }
    
}
