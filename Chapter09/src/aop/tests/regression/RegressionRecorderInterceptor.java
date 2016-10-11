// -------------------------------------------------------------------
// - Foundations of Aspect-Oriented Programming for J2EE Development -
// - Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -
// - APress                                                          -
// -                                                                 -
// - Chapter 9: Quality of Service and AOP                           -
// -------------------------------------------------------------------

package aop.tests.regression;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;

import org.jboss.aop.Interceptor;
import org.jboss.aop.Invocation;
import org.jboss.aop.InvocationResponse;
import org.jboss.aop.InvocationType;
import org.jboss.aop.MethodInvocation;
import org.jboss.util.xml.XmlLoadable;
import org.w3c.dom.Element;

/**
 * Interceptor implementing the non regression test aspect.
 * 
 * @author Renaud Pawlak
 * @author Jean-Philippe Retaille
 * @author Lionel Seinturier
 */
public class RegressionRecorderInterceptor implements Interceptor, XmlLoadable {
	private PrintWriter out;
	private String version;

	public String getName() {
		return "RegressionRecorderInterceptor";
	}

	public void importXml(Element parameter) {
		Element t =
			(Element) parameter.getElementsByTagName("record-file").item(0);
		String fileName = "";
		if (t != null) {
			fileName = t.getAttribute("value");
			if ("".equals(fileName)){
				throw new RuntimeException("Missing value paremeter for record-file tag in jboss-aop.xml");
			}
		} else {
			throw new RuntimeException("Mssing record-file tag in jboss-aop.xml");
		}
		t = (Element) parameter.getElementsByTagName("version").item(0);
		if (t!=null) {
			version = t.getAttribute("value");
			if ("".equals(version)) {
				throw new RuntimeException("Missing value paremeter for version tag in jboss-aop.xml");
			}
		} else {
			throw new RuntimeException("Mssing version tag in jboss-aop.xml");
		}
		try {
			FileOutputStream stream = new FileOutputStream(fileName,true);
			out = new PrintWriter(stream);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Error while accessing the output file");
		}
		if (version.equals("1")) {
			out.println("Version,Class Name,Method Name,Response,Parameters,Exception");
		}
	}

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
	
	public InvocationResponse invoke(Invocation invocation) throws Throwable {
		String filter = (String) invocation.getMetaData("regression", "filter");
		if ((invocation.getType() != InvocationType.METHOD)
			|| (filter != null && filter.equals("true"))) {
			return invocation.invokeNext();
		}

		InvocationResponse rsp = null;
		Object response = null;
		Throwable exception = null;
		
		try {
			rsp = invocation.invokeNext();
			response = rsp.getResponse();
		}
		catch (Throwable e){
			exception = e;
		}

		MethodInvocation methodInvocation = (MethodInvocation)invocation;
		Method method = methodInvocation.method;
	
		String className = method.getDeclaringClass().getName();
		String methodName = method.getName();
		Object[] parameters = methodInvocation.arguments;
				
		out.print(version);
		out.print(',');
		out.print(className);
		out.print(',');
		out.print(methodName);
		out.print(',');
		if (response!=null) {
			out.print(getValue(response));
		} else if (method.getReturnType().isAssignableFrom(java.lang.Void.TYPE)) {
			out.print("void");
		} else {
			out.print("null");
		}
		out.print(',');
		StringBuffer temp = new StringBuffer();
		for (int i = 0; i < parameters.length; i++) {
			if (parameters[i]!=null) {
				temp.append(getValue(parameters[i]));
			} else {
				temp.append("null");
			}
			temp.append(';');
		}
		if (temp.length()>0) {
			temp.deleteCharAt(temp.length()-1);
			out.print(temp);
		}
		if (exception!=null){
			out.print(',');
			out.print("throws ");
			out.print(exception.getClass().getName());
		}
		out.println();
		out.flush();
		
		return rsp;
	}
	
	protected void finalize() throws Throwable {
		super.finalize();
		out.close();
	}	
}
