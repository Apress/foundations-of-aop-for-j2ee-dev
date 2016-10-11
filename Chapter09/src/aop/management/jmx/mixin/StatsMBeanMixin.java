// -------------------------------------------------------------------
// - Foundations of Aspect-Oriented Programming for J2EE Development -
// - Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -
// - APress                                                          -
// -                                                                 -
// - Chapter 9: Quality of Service and AOP                           -
// -------------------------------------------------------------------

package aop.management.jmx.mixin;

/**
 * Mixin implementing the StatsMBean interface.
 * 
 * @author Renaud Pawlak
 * @author Jean-Philippe Retaille
 * @author Lionel Seinturier
 */
public class StatsMBeanMixin implements StatsMBean {
	private Stats advised;
	
	public StatsMBeanMixin(Object p) {
		advised = (Stats)p;
	}
	
	public int getMOrders() {
		return advised.getOrders();
	}
	
	public float getMTotalAmount() {
		return advised.getTotalAmount();
	}
	
	public float getMeanOrderAmount() {
		if (advised.getOrders() > 0) {
			return advised.getTotalAmount()/advised.getOrders();
		} else {
			return 0;
		}
	}
	
	public String getMStatus() {
		return advised.getStatus();
	}
	
	public void mReset() {
		advised.reset();
	}
}
