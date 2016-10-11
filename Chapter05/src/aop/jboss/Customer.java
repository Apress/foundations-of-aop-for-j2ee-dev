// -------------------------------------------------------------------
// - Foundations of Aspect-Oriented Programming for J2EE Development -
// - Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -
// - APress                                                          -
// -                                                                 -
// - Chapter 5: JBoss AOP                                            -
// -------------------------------------------------------------------

package aop.jboss;

/**
 * In the Order management application,
 * this class simulates a client which buys 1 DVD and 2 CD,
 * and ask for the order amount.
 * 
 * @author Renaud Pawlak
 * @author Jean-Philippe Retaille
 * @author Lionel Seinturier
 */
public class Customer {
    
    public void run() {
        Order myOrder = new Order();
        myOrder.addItem("DVD",1);
        myOrder.addItem("CD",2);
        double amount = myOrder.computeAmount();
        System.out.println("Order amount: US$"+amount);
    }
    
    public static void main(String[] args) {
        new Customer().run();
    }

}
