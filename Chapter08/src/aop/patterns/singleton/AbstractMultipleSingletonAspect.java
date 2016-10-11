// -------------------------------------------------------------------
// - Foundations of Aspect-Oriented Programming for J2EE Development -
// - Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -
// - APress                                                          -
// -                                                                 -
// - Chapter 8: Design Patterns and AOP                              -
// -------------------------------------------------------------------

package aop.patterns.singleton;

import java.util.Hashtable;

/**
 * Asbtract aspect for the generic singleton supporting multiple constructors.
 * 
 * @author Renaud Pawlak
 * @author Jean-Philippe Retaille
 * @author Lionel Seinturier
 */
public abstract aspect AbstractMultipleSingletonAspect {
	
    private Hashtable singletons = new Hashtable();
    
    abstract pointcut singletonPointcut();
    
	private String getValue(Object o) {
		if (!o.getClass().isArray()) {
			return Integer.toString(o.hashCode());
		} else {
			StringBuffer value = new StringBuffer();
			Object[] temp = (Object[]) o;
			for (int i=0;i<temp.length;i++) {
				value.append(getValue(temp[i]));
				value.append('|');
			}
			return value.toString();
		}
	}

	Object around(): singletonPointcut() {
		String arguments;
		String signature = thisJoinPoint.getSignature().toString();
		if(thisJoinPoint.getArgs().length>0) {
			arguments = getValue(thisJoinPoint.getArgs());
		} else {
			arguments = "";
		}
		
		Object singleton = singletons.get(signature+arguments);
		if (singleton == null) {
			singleton = proceed();
			singletons.put(signature+arguments,singleton);
		}
		return singleton;
	} 
}