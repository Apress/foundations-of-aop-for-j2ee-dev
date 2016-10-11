// -------------------------------------------------------------------
// - Foundations of Aspect-Oriented Programming for J2EE Development -
// - Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -
// - APress                                                          -
// -                                                                 -
// - Chapter 3: AspectJ                                              -
// -------------------------------------------------------------------

package aop.aspectj;

import java.util.HashMap;
import java.util.Map;

/**
 * In the Order management application,
 * this class manages the catalog of available items.
 * 
 * @author Renaud Pawlak
 * @author Jean-Philippe Retaille
 * @author Lionel Seinturier
 */
public class Catalog {
    
    /**
     * The list of price for every available item.
     * Map: the key is the item reference and the value is the item price.
     */ 
    private static Map priceList = new HashMap();
    
    /** Price of available items. */
    static {
        priceList.put( "CD", new Double(15.0) );
        priceList.put( "DVD", new Double(20.0) );
    }
    
    /**
     * Compute the price of an item.
     * 
     * @param reference  the item reference.
     * @return           the item price.
     */
    public static double getPrice( String reference ) {
        Double price = (Double) priceList.get(reference);
        return price.doubleValue();
    }

}
