// -------------------------------------------------------------------
// - Foundations of Aspect-Oriented Programming for J2EE Development -
// - Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -
// - APress                                                          -
// -                                                                 -
// - Chapter 8: Design Patterns and AOP                              -
// -------------------------------------------------------------------

package aop.patterns.command;

/**
 * Abstract aspect for the generic command.
 * 
 * @author Renaud Pawlak
 * @author Jean-Philippe Retaille
 * @author Lionel Seinturier
 */
public abstract aspect AbstractCommandAspect pertarget(receiver()){
	
	private Command command = null;
	
	public void setCommand(Command c) {
		command = c;
	}
	
	protected abstract pointcut receiver();
	
	protected abstract pointcut execute();
	
	before(Object receiver) : execute() && target(receiver) {
		command.execute(receiver);
	}
}