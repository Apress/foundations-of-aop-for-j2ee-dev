// -------------------------------------------------------------------
// - Foundations of Aspect-Oriented Programming for J2EE Development -
// - Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -
// - APress                                                          -
// -                                                                 -
// - Chapter 8: Design Patterns and AOP                              -
// -------------------------------------------------------------------

package aop.patterns.accessproxy;

/**
 * Concrete aspect for a proxy.
 * 
 * @author Renaud Pawlak
 * @author Jean-Philippe Retaille
 * @author Lionel Seinturier
 */
public aspect AccessProxyAspect extends AbstractAccessProxyAspect {
	
	protected boolean isAuthorized() {
		if ("admin".equals(user)&&"admin".equals(password)) {
			return true;
		}
		return false;
	}
	
	protected pointcut accessControl() : call(* Stats.*(..));
}
