// -------------------------------------------------------------------
// - Foundations of Aspect-Oriented Programming for J2EE Development -
// - Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -
// - APress                                                          -
// -                                                                 -
// - Chapter 9: Quality of Service and AOP                           -
// -------------------------------------------------------------------

package aop.patterns.singleton;

/**
 * Programme exemple
 * 
 * @author Renaud Pawlak
 * @author Jean-Philippe Retaille
 * @author Lionel Seinturier
 */
public class SingletonExample {

	public static void main(String[] args) {
		Stats stats1 = new Stats();
		Stats stats2 = new Stats();
		
		if (stats1==stats2) {
			System.out.println("These instances are the same!");
		} else {
			System.out.println("These instances are not the same!");
		}
	}
}
