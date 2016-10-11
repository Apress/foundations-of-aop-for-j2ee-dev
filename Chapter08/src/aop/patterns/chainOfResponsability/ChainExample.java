// -------------------------------------------------------------------
// - Foundations of Aspect-Oriented Programming for J2EE Development -
// - Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -
// - APress                                                          -
// -                                                                 -
// - Chapter 8: Design Patterns and AOP                              -
// -------------------------------------------------------------------

package aop.patterns.chainOfResponsability;

/**
 * Entry point for the chain of responsibility example.
 * 
 * @author Renaud Pawlak
 * @author Jean-Philippe Retaille
 * @author Lionel Seinturier
 */
public class ChainExample {
	public static void main(String[] args) {
		Stats stats = new Stats();
		
		stats.incOrders();
		stats.addAmount(10);
		stats.incOrders();
		stats.addAmount(10);
		stats.incOrders();
		stats.addAmount(10);
	}
}
