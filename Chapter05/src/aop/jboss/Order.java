// -------------------------------------------------------------------
// - Foundations of Aspect-Oriented Programming for J2EE Development -
// - Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -
// - APress                                                          -
// -                                                                 -
// - Chapter 5: JBoss AOP                                            -
// -------------------------------------------------------------------

package aop.jboss;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * In the Order management application,
 * this class corresponds to an order.
 * Ordered items are added into a map.
 * 
 * @author Renaud Pawlak
 * @author Jean-Philippe Retaille
 * @author Lionel Seinturier
 */
public class Order {
    
    /** Ordered items. */
    private Map items = new HashMap();
    
    /**
     * Add an item to the order.
     * 
     * @param reference  the item reference.
     * @param quantity   the quantity ordered.
     */
    public void addItem( String reference, int quantity ) {
        items.put(reference,new Integer(quantity));
        System.out.println(
            quantity+" item(s) "+reference+" added to the order");
    }
    
    /**
     * Compute the order amount.
     * 
     * @return  the order amount.
     */
    public double computeAmount() {
        double amount = 0.0;
        for (Iterator iter = items.entrySet().iterator(); iter.hasNext();) {
            Map.Entry entry = (Map.Entry) iter.next();
            String item = (String) entry.getKey();
            Integer quantity = (Integer) entry.getValue();
            double prix = Catalog.getPrice(item);
            amount += prix*quantity.intValue();
        }
        return amount;
    }

}
