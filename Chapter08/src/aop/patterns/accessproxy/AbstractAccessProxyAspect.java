// -------------------------------------------------------------------
// - Foundations of Aspect-Oriented Programming for J2EE Development -
// - Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -
// - APress                                                          -
// -                                                                 -
// - Chapter 8: Design Patterns and AOP                              -
// -------------------------------------------------------------------

package aop.patterns.accessproxy;

/**
 * Abstract aspect for a generic proxy.
 * 
 * @author Renaud Pawlak
 * @author Jean-Philippe Retaille
 * @author Lionel Seinturier
 */
public abstract aspect AbstractAccessProxyAspect {
	protected String user;
	protected String password;
		
	public void setAuthentication(String user,String password) {
		this.user = user;
		this.password = password;
	}
	
	protected abstract boolean isAuthorized();
	
	protected abstract pointcut accessControl();
	
	before() : accessControl() {
		if (!isAuthorized()) {
			throw new RuntimeException("Acces denied to method: "+thisJoinPoint.getSignature().getName());
		}
	}
}
