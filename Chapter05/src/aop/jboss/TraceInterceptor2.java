// -------------------------------------------------------------------
// - Foundations of Aspect-Oriented Programming for J2EE Development -
// - Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -
// - APress                                                          -
// -                                                                 -
// - Chapter 5: JBoss AOP                                            -
// -------------------------------------------------------------------

package aop.jboss;

import org.jboss.aop.Invocation;
import org.jboss.aop.InvocationResponse;
import org.jboss.aop.Interceptor;
import org.jboss.aop.MethodInvocation;

/**
 * In the Order management application,
 * this interceptor displays trace messages and introspect joinpoints.
 * 
 * @author Renaud Pawlak
 * @author Jean-Philippe Retaille
 * @author Lionel Seinturier
 */
public class TraceInterceptor2 implements Interceptor {
    public String getName() { return "TraceInterceptor2"; }

   /**
     * Intercepting method.
    */
   public InvocationResponse invoke(Invocation invocation) throws Throwable {
    
      MethodInvocation mi = (MethodInvocation) invocation;
      String methodName = mi.method.getName();
      Object[] args = mi.arguments;
      Object callee = mi.targetObject;
      
      System.out.println("-> Method "+methodName+" begins");
      if ( args == null ) {
        System.out.println("-> 0 parameter ");
      }
      else {
        System.out.print("-> "+args.length+" parameter(s) ");
        for (int i = 0; i < args.length; i++)
           System.out.print( args[i]+" " );
        System.out.println();
      }
      System.out.println("-> to "+callee);

      InvocationResponse rsp = invocation.invokeNext();
      System.out.println("<- Method "+methodName+" ends");
      return rsp;   
   }
}
