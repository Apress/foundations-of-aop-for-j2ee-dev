// -------------------------------------------------------------------
// - Foundations of Aspect-Oriented Programming for J2EE Development -
// - Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -
// - APress                                                          -
// -                                                                 -
// - Chapter 8: Design Patterns and AOP                              -
// -------------------------------------------------------------------

package aop.patterns.strategy;

/**
 * Statistics class.
 * 
 * @author Renaud Pawlak
 * @author Jean-Philippe Retaille
 * @author Lionel Seinturier
 */
public class Stats {
	private int orders = 0;
	private float totalAmount = 0;
	private String status = "OK";
	
	public Stats() {
	}
	
	public Stats(String status) {
		this.status = status;
	}
	
	public int getOrders() {
		return orders;
	}

	public void incOrders() {
		orders++;
	}
	
	public float getTotalAmount() {
		return totalAmount;
	}
	
	public void addAmount(float p) {
		totalAmount+=p;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String p) {
		status = p;
	}
	
	public void reset() {
		orders = 0;
		totalAmount = 0;
		status = "OK";
	}
}
