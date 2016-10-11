// -------------------------------------------------------------------
// - Foundations of Aspect-Oriented Programming for J2EE Development -
// - Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -
// - APress                                                          -
// -                                                                 -
// - Chapter 9: Quality of Service and AOP                           -
// -------------------------------------------------------------------

package aop.contracts.preconditions;

/**
 * Example for precondition.
 * 
 * @author Renaud Pawlak
 * @author Jean-Philippe Retaille
 * @author Lionel Seinturier
 */
public class PreConditionExample {
	public double sqrt(double p) {
		return Math.sqrt(p);
	}

	public static void main(String[] args) {
		PreConditionExample t = new PreConditionExample();
		System.out.println("racine carrée de -4 : "+t.sqrt(-4));
		System.out.println("racine carrée de 9 : "+t.sqrt(9));
	}
}
