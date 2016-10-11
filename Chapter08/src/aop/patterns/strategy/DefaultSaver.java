// -------------------------------------------------------------------
// - Foundations of Aspect-Oriented Programming for J2EE Development -
// - Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -
// - APress                                                          -
// -                                                                 -
// - Chapter 8: Design Patterns and AOP                              -
// -------------------------------------------------------------------

package aop.patterns.strategy;

/**
 * Class implementing the default strategy.
 * 
 * @author Renaud Pawlak
 * @author Jean-Philippe Retaille
 * @author Lionel Seinturier
 */
public class DefaultSaver implements Strategy {
	
	public void execute(Object receiver) {
		Stats stats = (Stats)receiver;
		System.out.println("STATISTICS");
		System.out.println("Total number of orders: "+stats.getOrders());
		System.out.println("Total amount: "+stats.getTotalAmount());
		System.out.println("Statut: "+stats.getStatus());
		System.out.flush();
		System.out.close();
	}
}
