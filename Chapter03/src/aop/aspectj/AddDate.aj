// -------------------------------------------------------------------
// - Foundations of Aspect-Oriented Programming for J2EE Development -
// - Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -
// - APress                                                          -
// -                                                                 -
// - Chapter 3: AspectJ                                              -
// -------------------------------------------------------------------

package aop.aspectj;

import java.util.Date;

/**
 * In the Order management application,
 * this aspect introduces a date for all orders.
 * 
 * @author Renaud Pawlak
 * @author Jean-Philippe Retaille
 * @author Lionel Seinturier
 */
public aspect AddDate {

    /** date field introduced in the Order class. */
    private Date Order.date;
    
    /** 2 methods getDate() and setDate() introduced in Order. */
    public Date Order.getDate() { return date; }
    public void Order.setDate(Date date) { this.date = date; }
    
    /** A constructor introduced in Order. */
    public Order.new (Date date) { this.date = date; }

    /**
     * After creating a new Order instance,
     * we initialize the introduced field date with the current date.
     */
    after() : initialization(Order.new (..)) {
        Order myOrder = (Order) thisJoinPoint.getTarget();
        myOrder.date = new Date();
    }
    
    /**
     * Mock pointcut and advice code to test the adding of the date field.
     * At the end of the main method,
     * an Order instance is created and the date field is displayed.
     */
    after() : execution(void Customer.main(..)) {
    	Order myOrder = new Order();
    	System.out.println("Order created on: "+myOrder.date);
    }
}
