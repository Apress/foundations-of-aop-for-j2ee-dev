// -------------------------------------------------------------------
// - Foundations of Aspect-Oriented Programming for J2EE Development -
// - Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -
// - APress                                                          -
// -                                                                 -
// - Chapter 8: Design Patterns and AOP                              -
// -------------------------------------------------------------------

package aop.patterns.observer;

/**
 * Concrete aspect for observing the total amount.
 * 
 * @author Renaud Pawlak
 * @author Jean-Philippe Retaille
 * @author Lionel Seinturier
 */
public aspect AmountObserverAspect extends AbstractObserverAspect {
	
	protected pointcut event() : set(* Stats.totalAmount);

	protected pointcut subject() : initialization(Stats.new(..));

	protected void notifyEvent(Object s,Object o) {
		Stats statistics = (Stats)s;
		AmountObserver observer = (AmountObserver)o;
		observer.eventHandler(statistics.getTotalAmount());
	}
}
