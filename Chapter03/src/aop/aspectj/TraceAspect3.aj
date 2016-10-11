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
 * this aspect traces the calls to all the methods defined in the Order class
 * and displays information about every encountered joinpoint.
 * 
 * @author Renaud Pawlak
 * @author Jean-Philippe Retaille
 * @author Lionel Seinturier
 */
public aspect TraceAspect3 {
    
    /**
     * This pointcut picks out calls to methods defined in the Order class.
     */
    pointcut toBeTraced(): call( * aop.aspectj.Order.*(..) );
    
    /**
     * Advice code that displays trace messages and
     * information about encountered joinpoints.
     */
    Object around(): toBeTraced() {
        
        String methodName = thisJoinPoint.getSignature().getName();
        Object[] args = thisJoinPoint.getArgs();
        Object caller = thisJoinPoint.getThis();
        Object callee = thisJoinPoint.getTarget();
                
        System.out.println("-> Method "+methodName+" begins");
        System.out.print("-> "+args.length+" parameter(s) ");
        for (int i = 0; i < args.length; i++) {
            System.out.print( args[i]+" " );
        }
        System.out.println();
        System.out.println("-> "+caller+" to "+callee);
        Object ret = proceed();
        System.out.println("<- Method "+methodName+" ends");
        return ret;
    }

}
