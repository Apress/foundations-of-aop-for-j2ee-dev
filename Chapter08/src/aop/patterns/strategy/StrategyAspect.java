// -------------------------------------------------------------------
// - Foundations of Aspect-Oriented Programming for J2EE Development -
// - Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -
// - APress                                                          -
// -                                                                 -
// - Chapter 8: Design Patterns and AOP                              -
// -------------------------------------------------------------------

package aop.patterns.strategy;

public aspect StrategyAspect extends AbstractStrategyAspect {

	public void Stats.display(Object context){
	}
	
	protected pointcut receiver() : initialization(Stats.new(..));
	
	protected pointcut execute(Object context) : call(void Stats.display(Object)) && args(context);
}