// -------------------------------------------------------------------
// - Foundations of Aspect-Oriented Programming for J2EE Development -
// - Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -
// - APress                                                          -
// -                                                                 -
// - Chapter 8: Design Patterns and AOP                              -
// -------------------------------------------------------------------

package aop.patterns.strategy;

/**
 * Entry point for the strategy example.
 * 
 * @author Renaud Pawlak
 * @author Jean-Philippe Retaille
 * @author Lionel Seinturier
 */
public class StrategyExample {
	public static void main(String[] args) {
		Stats stats = new Stats();
		
		StrategyAspect.aspectOf(stats).setStrategy("file",new FileSaver("c://temp/statistics.txt"));
		StrategyAspect.aspectOf(stats).setStrategy("default",new DefaultSaver());
		
		stats.incOrders();
		stats.addAmount(10);
		stats.incOrders();
		stats.addAmount(10);
		stats.incOrders();
		stats.addAmount(10);
		
		stats.display("default");
	}
}
