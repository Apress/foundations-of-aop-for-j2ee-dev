// -------------------------------------------------------------------
// - Foundations of Aspect-Oriented Programming for J2EE Development -
// - Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -
// - APress                                                          -
// -                                                                 -
// - Chapter 9: Quality of Service and AOP                           -
// -------------------------------------------------------------------

package aop.tests.regression;

import java.util.Vector;

/**
 * Entry point for the non regression test example.
 * 
 * @author Renaud Pawlak
 * @author Jean-Philippe Retaille
 * @author Lionel Seinturier
 */
public class RegressionExample {
	
	public int increment(int value) {
		return value++;
	}
	
	public int decrement(int value) {
		return value--;
	}
	
	public static void test(Object[] t,Object j) throws Exception,ArrayIndexOutOfBoundsException {
		System.out.println("test called");
		throw new ArrayIndexOutOfBoundsException("Error in test");
	}

	public static void main(String[] args) {
		RegressionExample t = new RegressionExample();
		System.out.println("Increase by 1 : "+t.increment(1));
		System.out.println("Decrease by 1 : "+t.decrement(1));
		try {
			String[] array = {"str1","str2"};
			Object[] arrayOfArray = {array,"str3"};
			Vector v = new Vector();
			v.add("str4");
			v.add("str5");
			test(arrayOfArray,v);
		}
		catch (Exception e) {
		}
	}
}
