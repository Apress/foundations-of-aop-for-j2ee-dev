// -------------------------------------------------------------------
// - Foundations of Aspect-Oriented Programming for J2EE Development -
// - Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -
// - APress                                                          -
// -                                                                 -
// - Chapter 8: Design Patterns and AOP                              -
// -------------------------------------------------------------------

package aop.patterns.observer;

import java.util.Enumeration;
import java.util.Vector;

/**
 * Abstract aspect for the generic observer.
 * 
 * @author Renaud Pawlak
 * @author Jean-Philippe Retaille
 * @author Lionel Seinturier
 */
public abstract aspect AbstractObserverAspect pertarget (subject()) {
	private Vector observers = new Vector();
	
	public void addObserver(Object o) {
		observers.add(o);
	}
	
	public void removeObserver(Object o) {
		observers.remove(o);
	}
	
	protected abstract pointcut subject();
	
	protected abstract pointcut event();
	
	protected abstract void notifyEvent(Object subject,Object observer);
	
	after(Object s) : event() && target(s) {
		Enumeration elements = observers.elements();
		while (elements.hasMoreElements()) {
			Object o = elements.nextElement();
			notifyEvent(s,o);
		}
	}
}