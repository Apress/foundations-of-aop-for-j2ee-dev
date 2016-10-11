// -------------------------------------------------------------------
// - Foundations of Aspect-Oriented Programming for J2EE Development -
// - Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -
// - APress                                                          -
// -                                                                 -
// - Chapter 8: Design Patterns and AOP                              -
// -------------------------------------------------------------------

package aop.patterns.singleton;

/**
 * Entry point for the singleton example.
 * 
 * @author Renaud Pawlak
 * @author Jean-Philippe Retaille
 * @author Lionel Seinturier
 */
public class SingletonExample {

	public static void main(String[] args) {
		Stats stats1 = new Stats("OK");
		Stats stats2 = new Stats("OK");
		
		if (stats1==stats2) {
			System.out.println("These instances are the same!");
		} else {
			System.out.println("These instances are not the same!");
		}
	}
}
