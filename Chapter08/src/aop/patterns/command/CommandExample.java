// -------------------------------------------------------------------
// - Foundations of Aspect-Oriented Programming for J2EE Development -
// - Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -
// - APress                                                          -
// -                                                                 -
// - Chapter 8: Design Patterns and AOP                              -
// -------------------------------------------------------------------

package aop.patterns.command;

/**
 * Entry point for the command example.
 * 
 * @author Renaud Pawlak
 * @author Jean-Philippe Retaille
 * @author Lionel Seinturier
 */
public class CommandExample {
	public static void main(String[] args) {
		Stats stats = new Stats();
		
		CommandAspect.aspectOf(stats).setCommand(new FileSaver("c://temp/statistics.txt"));
		
		stats.incOrders();
		stats.addAmount(10);
		stats.incOrders();
		stats.addAmount(10);
		stats.incOrders();
		stats.addAmount(10);
		
		stats.save();
	}
}
