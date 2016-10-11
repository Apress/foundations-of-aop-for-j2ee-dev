// -------------------------------------------------------------------
// - Foundations of Aspect-Oriented Programming for J2EE Development -
// - Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -
// - APress                                                          -
// -                                                                 -
// - Chapter 9: Quality of Service and AOP                           -
// -------------------------------------------------------------------

package aop.contracts.postconditions;

/**
 * Example for postcondition 1.
 * 
 * @author Renaud Pawlak
 * @author Jean-Philippe Retaille
 * @author Lionel Seinturier
 */
public class PostConditionExample1 {
		
	public double sqrt(double p) {
		if (p==0) {
			return -1;
		}
		return Math.sqrt(p);
	}

	public static void main(String[] args) {
		PostConditionExample1 t = new PostConditionExample1();
		System.out.println("Square root (4) : "+t.sqrt(4));
		System.out.println("Square root (0) : "+t.sqrt(0));
		System.out.println("Square root (9) : "+t.sqrt(9));
	}
}
