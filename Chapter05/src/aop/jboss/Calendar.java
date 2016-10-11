// -------------------------------------------------------------------
// - Foundations of Aspect-Oriented Programming for J2EE Development -
// - Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -
// - APress                                                          -
// -                                                                 -
// - Chapter 5: JBoss AOP                                            -
// -------------------------------------------------------------------

package aop.jboss;

import java.util.Date;

/**
 * In the Order management application,
 * this class contains elements introduced in the Order class.
 * 
 * @author Renaud Pawlak
 * @author Jean-Philippe Retaille
 * @author Lionel Seinturier
 */
public class Calendar implements CalendarItf {
    
    /** Reference towards the object where the elements are introduced. */
    private Object initial;
    
    /** Introduced field. */
    private Date date;
    
    public Calendar( Object initial ) {
        this.initial = initial;
        date = new Date();
    }
    
    /*
     * Introduced methods.
     */
    public void setDate(Date date) {
        this.date = date;
    }
    public Date getDate() {
        return date;
    }
    
}
