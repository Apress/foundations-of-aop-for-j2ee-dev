// -------------------------------------------------------------------
// - Foundations of Aspect-Oriented Programming for J2EE Development -
// - Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -
// - APress                                                          -
// -                                                                 -
// - Chapter 8: Design Patterns and AOP                              -
// -------------------------------------------------------------------

package aop.patterns.observer;

/**
 * Entry point for the observer example.
 * 
 * @author Renaud Pawlak
 * @author Jean-Philippe Retaille
 * @author Lionel Seinturier
 */
public class ObserverExample {
	public static void main(String[] args) {
		Stats stats = new Stats();
		OrdersObserver observer1 = new OrdersObserver();
		AmountObserver observer2 = new AmountObserver();

		OrdersObserverAspect.aspectOf(stats).addObserver(observer1);
		AmountObserverAspect.aspectOf(stats).addObserver(observer2);
		
		stats.incOrders();
		stats.addAmount(10);
		stats.incOrders();
		stats.addAmount(10);
		OrdersObserverAspect.aspectOf(stats).removeObserver(observer1);
		stats.incOrders();
		stats.addAmount(10);
	}
}
