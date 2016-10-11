// -------------------------------------------------------------------
// - Foundations of Aspect-Oriented Programming for J2EE Development -
// - Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -
// - APress                                                          -
// -                                                                 -
// - Chapter 9: Quality of Service and AOP                           -
// -------------------------------------------------------------------

package aop.tests.cover;

/**
 * Entry point for the example.
 * 
 * @author Renaud Pawlak
 * @author Jean-Philippe Retaille
 * @author Lionel Seinturier
 */
public class CoverExample {
	
	private static int myField = 0;
	
	public int increment(int value) {
		return ++value;
	}
	
	public int decrement(int value) {
		return --value;
	}
	
	public static int[] test(Object[] t,Object j) throws Exception,ArrayIndexOutOfBoundsException {
		System.out.println("test: "+myField);
		return null;
	}

	public static void main(String[] args) {
		CoverExample t = new CoverExample();
		System.out.println("Increase by 1: "+t.increment(1));
		System.out.println("Decrease by 1: "+t.decrement(1));
		try {
			test(null,null);
		}
		catch (Exception e) {
		}	
	}
}
