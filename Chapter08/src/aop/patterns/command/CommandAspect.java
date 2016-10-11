// -------------------------------------------------------------------
// - Foundations of Aspect-Oriented Programming for J2EE Development -
// - Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -
// - APress                                                          -
// -                                                                 -
// - Chapter 8: Design Patterns and AOP                              -
// -------------------------------------------------------------------

package aop.patterns.command;

/**
 * Concrete aspect for the command.
 * 
 * @author Renaud Pawlak
 * @author Jean-Philippe Retaille
 * @author Lionel Seinturier
 */
public aspect CommandAspect extends AbstractCommandAspect {
	public void Stats.save(){
	}
	
	protected pointcut receiver() : initialization(Stats.new(..));
	
	protected pointcut execute() : call(void Stats.save());
}