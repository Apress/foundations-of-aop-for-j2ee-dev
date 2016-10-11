// -------------------------------------------------------------------
// - Foundations of Aspect-Oriented Programming for J2EE Development -
// - Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -
// - APress                                                          -
// -                                                                 -
// - Chapter 8: Design Patterns and AOP                              -
// -------------------------------------------------------------------

package aop.patterns.accessproxy;

/**
 * Try to access the Stats class taking the login and password given as
 * command line parameters.
 * 
 * @author Renaud Pawlak
 * @author Jean-Philippe Retaille
 * @author Lionel Seinturier
 */
public class ProxyExample {

	public static void main(String[] args) {
		Stats stats = new Stats();
		if (args.length==2) {
			AccessProxyAspect.aspectOf().setAuthentication(args[0],args[1]);
		
			stats.incOrders();
			stats.addAmount(10);
			stats.incOrders();
			stats.addAmount(10);
			stats.incOrders();
			stats.addAmount(10);
			
			System.out.println("Total number of orders: "+stats.getOrders());
			System.out.println("TOtal amount: "+stats.getTotalAmount());
			System.out.println("Statut: "+stats.getStatus());
		} else {
			System.out.println("Wrong command line parameters: login password expected.");
		}
	}
}
