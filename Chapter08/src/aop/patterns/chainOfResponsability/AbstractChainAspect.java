// -------------------------------------------------------------------
// - Foundations of Aspect-Oriented Programming for J2EE Development -
// - Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -
// - APress                                                          -
// -                                                                 -
// - Chapter 8: Design Patterns and AOP                              -
// -------------------------------------------------------------------

package aop.patterns.chainOfResponsability;

/**
 * Abstract aspect for the generic chain of responsibility.
 * 
 * @author Renaud Pawlak
 * @author Jean-Philippe Retaille
 * @author Lionel Seinturier
 */
public abstract aspect AbstractChainAspect pertarget(receiver()){
	
	protected abstract pointcut receiver();
	
	protected abstract pointcut execute();
	
	protected abstract void handle();
	
	after() : execute() {
		handle();
	}
}