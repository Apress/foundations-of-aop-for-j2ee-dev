// -------------------------------------------------------------------
// - Foundations of Aspect-Oriented Programming for J2EE Development -
// - Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -
// - APress                                                          -
// -                                                                 -
// - Chapter 9: Quality of Service and AOP                           -
// -------------------------------------------------------------------

package aop.management.jmx.mixin;

/**
 * JMX accessible interface.
 * 
 * @author Renaud Pawlak
 * @author Jean-Philippe Retaille
 * @author Lionel Seinturier
 */
public interface StatsMBean {
	public int getMOrders();
	public float getMTotalAmount();
	public float getMeanOrderAmount();
	public String getMStatus();
	public void mReset();
}
