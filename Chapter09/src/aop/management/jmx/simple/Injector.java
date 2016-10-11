// -------------------------------------------------------------------
// - Foundations of Aspect-Oriented Programming for J2EE Development -
// - Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaill�        -
// - APress                                                          -
// -                                                                 -
// - Chapter 9: Quality of Service and AOP                           -
// -------------------------------------------------------------------

package aop.management.jmx.simple;

/**
 * Order generator.
 * 
 * @author Renaud Pawlak
 * @author Jean-Philippe Retaille
 * @author Lionel Seinturier
 */
public class Injector extends Thread {
	
	public void run() {
		float amount = 0;
		
		for (int i=1;i<=10;i++) {
			try {
				System.err.println("Order #"+i);
				sleep(Math.round(Math.random() * 5000));
			}
			catch (InterruptedException e) {
			}
			if ((i%5)==0) {
				JMXExample.sendCommand(-1000);
			} else {
				amount = Math.round(Math.random() * 1500);
				JMXExample.sendCommand(amount);
			}
		}
	}
}
