// -------------------------------------------------------------------
// - Foundations of Aspect-Oriented Programming for J2EE Development -
// - Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -
// - APress                                                          -
// -                                                                 -
// - Chapter 9: Quality of Service and AOP                           -
// -------------------------------------------------------------------

package aop.management.jmx.simple;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import javax.management.JMException;
import javax.management.Attribute;
import javax.management.monitor.GaugeMonitor;
import javax.management.monitor.StringMonitor;
import javax.management.monitor.CounterMonitor;
import javax.management.NotificationListener;
import javax.management.Notification;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * Programme exemple
 * 
 * @author Renaud Pawlak
 * @author Jean-Philippe Retaille
 * @author Lionel Seinturier
 */
public class JMXExample {
	private int port = 8080;
	private String host = "localhost";
	private static Stats statistics = new Stats();
	
	public static void sendCommand(float amount) {
		if (amount>0) {
			statistics.incOrders();
			statistics.addAmount(amount);
		} else {
			statistics.setStatus("KO");
			try {
				Thread.sleep(200);
			}
			catch (InterruptedException e) {
			}
			statistics.setStatus("OK");
		}
	}

	public void start() throws JMException, MalformedURLException {
		MBeanServer server = MBeanServerFactory.createMBeanServer("OrderProcess");
		ObjectName serverName = new ObjectName("Http:name=HttpAdaptor");
		server.createMBean("mx4j.adaptor.http.HttpAdaptor",serverName,null);
		server.setAttribute(serverName,new Attribute("Port",new Integer(port)));
		server.setAttribute(serverName,new Attribute("Host",host));
		ObjectName processorName = new ObjectName("Http:name=XSLTProcessor");
		server.createMBean("mx4j.adaptor.http.XSLTProcessor",processorName,null);
		server.setAttribute(processorName,new Attribute("UseCache",new Boolean(false)));
		server.setAttribute(serverName,new Attribute("ProcessorName",processorName));
		
		server.registerMBean(statistics,new ObjectName("OrderProcess:name=stats"));

		CounterMonitor ordersCounter = new CounterMonitor();
		ObjectName ordersCounterName = new ObjectName("OrderProcess","monitor","ordersCounter");
		server.registerMBean(ordersCounter, ordersCounterName);
		ordersCounter.setThreshold(new Integer(5));
		ordersCounter.setOffset(new Integer(5));
		ordersCounter.setNotify(true);
		ordersCounter.setDifferenceMode(false);
		ordersCounter.setObservedObject(new ObjectName("OrderProcess:name=stats"));
		ordersCounter.setObservedAttribute("Orders");
		ordersCounter.setGranularityPeriod(100L);
		ordersCounter.addNotificationListener(new NotificationListener() {
			public void handleNotification(Notification notification,Object handback) {
				System.out.println("JMX notification - Number of orders: exceeding threshold");
			}
		}, null, null);
		ordersCounter.start();
		
		StringMonitor statusMonitor = new StringMonitor();
		ObjectName statusMonitorName = new ObjectName("OrderProcess","monitor","statusMonitor");
		server.registerMBean(statusMonitor,statusMonitorName);
		statusMonitor.setNotifyDiffer(true);
		statusMonitor.setNotifyMatch(true);
		statusMonitor.setStringToCompare("OK");
		statusMonitor.setObservedObject(new ObjectName("OrderProcess:name=stats"));
		statusMonitor.setObservedAttribute("Status");
		statusMonitor.setGranularityPeriod(100L);
		statusMonitor.addNotificationListener(new NotificationListener() {
			public void handleNotification(Notification notification,Object handback) {
				if (notification.getType().equals("jmx.monitor.string.differs")) {
					System.out.println("JMX notification - Wrong process");
				} else {
					System.out.println("JMX notification - Process OK");
				}
			}
		}, null, null);
		statusMonitor.start();

		server.invoke(serverName, "start", null, null);
	}

	public static void main(String[] str) throws Exception {
		JMXExample t = new JMXExample();
		t.start();
		Injector injection = new Injector();
		injection.start();
	}
}
