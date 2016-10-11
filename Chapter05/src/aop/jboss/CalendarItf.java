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
 * this interface is introduced in the Order class.
 * 
 * @author Renaud Pawlak
 * @author Jean-Philippe Retaille
 * @author Lionel Seinturier
 */
public interface CalendarItf {
    public void setDate(Date date);
    public Date getDate();
}
