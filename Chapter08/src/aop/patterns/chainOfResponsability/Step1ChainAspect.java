// -------------------------------------------------------------------
// - Foundations of Aspect-Oriented Programming for J2EE Development -
// - Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -
// - APress                                                          -
// -                                                                 -
// - Chapter 8: Design Patterns and AOP                              -
// -------------------------------------------------------------------

package aop.patterns.chainOfResponsability;

/**
 * Concrete aspect for step #1 in the chain of responsibility.
 * 
 * @author Renaud Pawlak
 * @author Jean-Philippe Retaille
 * @author Lionel Seinturier
 */
public aspect Step1ChainAspect extends AbstractChainAspect {
	
	protected pointcut receiver() : initialization(Stats.new(..));
	protected pointcut execute() : call(void Stats.incOrders());
	
	protected void handle() {
		System.out.println("OrderHandler #1");
	}
}