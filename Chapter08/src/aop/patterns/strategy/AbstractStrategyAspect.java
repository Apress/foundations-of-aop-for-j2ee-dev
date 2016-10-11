// -------------------------------------------------------------------
// - Foundations of Aspect-Oriented Programming for J2EE Development -
// - Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -
// - APress                                                          -
// -                                                                 -
// - Chapter 8: Design Patterns and AOP                              -
// -------------------------------------------------------------------

package aop.patterns.strategy;

import java.util.Hashtable;

/**
 * Abstract aspect for the generic strategy.
 * 
 * @author Renaud Pawlak
 * @author Jean-Philippe Retaille
 * @author Lionel Seinturier
 */
public abstract aspect AbstractStrategyAspect pertarget(receiver()){
	
	private Hashtable strategies = new Hashtable();
	
	public void setStrategy(Object c,Strategy s) {
		strategies.put(c,s);
	}
	
	protected Strategy getStrategy(Object c) {
		return (Strategy)strategies.get(c);
	}
	
	protected abstract pointcut receiver();
	
	protected abstract pointcut execute(Object c);
	
	before(Object receiver,Object context) : execute(Object) && target(receiver) && args(context) {
		Strategy strategy = getStrategy(context);
		strategy.execute(receiver);
	}
}