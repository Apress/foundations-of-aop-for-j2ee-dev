// -------------------------------------------------------------------
// - Foundations of Aspect-Oriented Programming for J2EE Development -
// - Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -
// - APress                                                          -
// -                                                                 -
// - Chapter 8: Design Patterns and AOP                              -
// -------------------------------------------------------------------

package aop.patterns.singleton;

/**
 * Abstract aspect for the generic singleton.
 * 
 * @author Renaud Pawlak
 * @author Jean-Philippe Retaille
 * @author Lionel Seinturier
 */
public abstract aspect AbstractSingletonAspect {
	
    private Object singleton = null;
    
    abstract pointcut singletonPointcut();

	Object around(): singletonPointcut() {
		if (singleton == null) {
			singleton = proceed();
		}
		return singleton;
	} 
}