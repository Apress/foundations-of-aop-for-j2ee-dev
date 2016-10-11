// -------------------------------------------------------------------
// - Foundations of Aspect-Oriented Programming for J2EE Development -
// - Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -
// - APress                                                          -
// -                                                                 -
// - Chapter 9: Quality of Service and AOP                           -
// -------------------------------------------------------------------

package aop.management.jmx.simple;

import java.lang.reflect.Method;

import mx4j.MBeanDescriptionAdapter;

/**
 * MX4J specific MBean description.
 * 
 * @author Renaud Pawlak
 * @author Jean-Philippe Retaille
 * @author Lionel Seinturier
 */
public class StatsMBeanDescription extends MBeanDescriptionAdapter {
	
	public String getAttributeDescription(String attribute) {
		if (attribute.equals("Orders")) {
			return "Number of processed orders";
		} else if (attribute.equals("Status")) {
			return "Status of the process";
		} else if (attribute.equals("TotalAmount")) {
			return "Total amount";
		} else {
			return "Unknown attribute";
		}
	}
	
	public String getOperationDescription(Method method) {
		if (method.getName().equals("reset")) {
			return "Reseting the attributes";
		} else {
			return "Unknown operation";
		}
	}
}
