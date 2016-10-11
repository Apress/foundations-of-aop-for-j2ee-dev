// -------------------------------------------------------------------
// - Foundations of Aspect-Oriented Programming for J2EE Development -
// - Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -
// - APress                                                          -
// -                                                                 -
// - Chapter 9: Quality of Service and AOP                           -
// -------------------------------------------------------------------

package aop.tests.cover;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.jboss.aop.ConstructorInvocation;
import org.jboss.aop.FieldInvocation;
import org.jboss.aop.Interceptor;
import org.jboss.aop.Invocation;
import org.jboss.aop.InvocationResponse;
import org.jboss.aop.InvocationType;
import org.jboss.aop.MethodInvocation;
import org.jboss.util.xml.XmlLoadable;
import org.w3c.dom.Element;

/**
 * Interceptor for the coverage test aspect.
 * 
 * @author Renaud Pawlak
 * @author Jean-Philippe Retaille
 * @author Lionel Seinturier
 */
public class CoverRecorderInterceptor implements Interceptor, XmlLoadable {
	private PrintWriter out;
	
	public String getName() {
		return "CoverRecorderInterceptor";
	}

	public void importXml(Element parameter) {
		Element t =	(Element) parameter.getElementsByTagName("record-file").item(0);
		String fileName = "";
		if (t != null) {
			fileName = t.getAttribute("value");
			if ("".equals(fileName)){
				throw new RuntimeException("Missing value paremeter for record-file tag in jboss-aop.xml");
			}
		} else {
			throw new RuntimeException("Mssing record-file tag in jboss-aop.xml");
		}
		try {
			FileOutputStream stream = new FileOutputStream(fileName);
			out = new PrintWriter(stream);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Error while accessing the output file: "+fileName);
		}
		out.println("Call type,Class,Name,Return / Type,parameters,exceptions");
	}

	public void recordMethodCall(String className,String methodName,Class returnType,Class[] parameters,Class[] exceptions) {
		out.print(className);
		out.print(',');
		out.print(methodName);
		out.print(',');
		if (returnType == null) {
			out.print("N/A"); 
		} else if (returnType.isArray()) {
			out.print(returnType.getComponentType());
			out.print("[]");
		} else {
			out.print(returnType.getName());	
		}
		out.print(',');
		StringBuffer temp = new StringBuffer();
		for (int i = 0; i < parameters.length; i++) {
			if (parameters[i].isArray()) {
				temp.append(parameters[i].getComponentType().getName());
				temp.append("[]");
			} else {
				temp.append(parameters[i].getName());	
			}
			temp.append(';');
		}
		if (temp.length() > 0) {
			temp.deleteCharAt(temp.length()-1);
			out.print(temp);
		}
		out.print(',');
		temp = new StringBuffer();
		for (int i = 0; i < exceptions.length; i++) {
			temp.append(exceptions[i].getName());
			temp.append(';');
		}
		if (temp.length() > 0) {
			temp.deleteCharAt(temp.length()-1);
			out.print(temp);
		}
	}
	
	public InvocationResponse invoke(Invocation invocation) throws Throwable {
		String filter = (String) invocation.getMetaData("cover", "filter");
		if (filter != null && filter.equals("true")) {
			return invocation.invokeNext();
		}

		InvocationResponse rsp = invocation.invokeNext();
		
		InvocationType invocationType = invocation.getType();
			
		if (invocationType == InvocationType.METHOD) {
			MethodInvocation methodInvocation = (MethodInvocation)invocation;
			Method method = methodInvocation.method;
			String className = method.getDeclaringClass().getName();
			String methodName = method.getName();
			Class returnType = method.getReturnType();
			Class[] parameters = method.getParameterTypes();
			Class[] exceptions = method.getExceptionTypes();
			out.print("Method call,");
			recordMethodCall(className,methodName,returnType,parameters,exceptions);
		} else if (invocationType == InvocationType.CONSTRUCTOR) {
			ConstructorInvocation constructorInvocation = (ConstructorInvocation)invocation;
			Constructor constructor = constructorInvocation.constructor;
			String className = constructor.getDeclaringClass().getName();
			String methodName = "N/A";
			Class returnType = null;
			Class[] parameters = constructor.getParameterTypes();
			Class[] exceptions = constructor.getExceptionTypes();
			out.print("Constructor call,");
			recordMethodCall(className,methodName,returnType,parameters,exceptions);
		} else if (invocationType == InvocationType.FIELD_WRITE || invocationType == InvocationType.FIELD_READ) {
			if (invocationType == InvocationType.FIELD_READ) {
				out.print("Field read access,");
			} else {
				out.print("Field write access,");
			}
			FieldInvocation fieldInvocation = (FieldInvocation)invocation;
			Field field = fieldInvocation.field;
			out.print(field.getDeclaringClass().getName());
			out.print(',');
			out.print(field.getName());
			out.print(',');
			out.print(field.getType());
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
