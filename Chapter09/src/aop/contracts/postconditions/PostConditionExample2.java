// -------------------------------------------------------------------
// - Foundations of Aspect-Oriented Programming for J2EE Development -
// - Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -
// - APress                                                          -
// -                                                                 -
// - Chapter 9: Quality of Service and AOP                           -
// -------------------------------------------------------------------

package aop.contracts.postconditions;

/**
 * Example for postcondition 2.
 * 
 * @author Renaud Pawlak
 * @author Jean-Philippe Retaille
 * @author Lionel Seinturier
 */
public class PostConditionExample2 {
		
	public int increment(int p) {
		return p++;
	}

	public static void main(String[] args) {
		PostConditionExample2 t = new PostConditionExample2();
		System.out.println("incrémente 1 : "+t.increment(1));
	}
}
