/*
* Created on Nov 12, 2003
*/

package aop.j2ee.commons.util.locator;

import javax.jms.*;
import javax.naming.NamingException;

/**
* @author T Murali
* @version 1.0
*/

public class JMSServiceLocator extends JNDIServiceLocator
{
	public static final String QUEUE_FACTORY = "QueueFactory";
	public static final String QUEUE		 = "Queue";
	public static final String TOPIC_FACTORY = "TopicFactory";
	public static final String TOPIC		 = "Topic";
			 
	public JMSServiceLocator() throws Exception
	{
		super();
	}
	
	/**
	*  Finds and retrieves the specific JNDI administered JMS object. The JMS object to be retrieved
	*  should be specified as a string parameter. 
	*  @param aJMSObjectType the string of the JMS object to be retrieved
	*  @return EJBHome object belonging to the specified class
	*/
	public Object lookup(Object aJMSObjectType) throws Exception
	{
		String jmsType = (String)aJMSObjectType;
		if(jmsType.equals(QUEUE_FACTORY) || jmsType.equals(TOPIC_FACTORY))
			return getConnectionFactory(jmsType);
		else if(jmsType.equals(QUEUE) || jmsType.equals(TOPIC))
			return getDestination(jmsType);
		else
			throw new IllegalArgumentException("Invalid JMS Type requested for lookup!");
	}
	
    /**
    *  Retrieves the JMS QueueConnectionFactory from the JNDI namespace
    *  @param aQueueConnectionFactoryName the QueueConnectionFactoryName
    *  @return the javax.jms.QueueConnectionFactory instance
    */
    private ConnectionFactory getConnectionFactory(String aConnectionFactoryName)
    throws NamingException
    {
		ConnectionFactory connectionFactory = null;
		if(aConnectionFactoryName.equals(QUEUE_FACTORY))
        	connectionFactory = (QueueConnectionFactory)javax.rmi.PortableRemoteObject.narrow(initContext.lookup(aConnectionFactoryName),
								QueueConnectionFactory.class);
		else if(aConnectionFactoryName.equals(TOPIC_FACTORY))
			connectionFactory = (TopicConnectionFactory)javax.rmi.PortableRemoteObject.narrow(initContext.lookup(aConnectionFactoryName),
								TopicConnectionFactory.class);
										
		return connectionFactory;
	}

    /**
    *  Retrieves the JMS Destination from the JNDI namespace
    *  @param aDestinationName the name of the destination
    *  @return the javax.jms.Destination instance
    */
    private Destination getDestination(String aDestinationName) throws NamingException
    {
    	Destination destination = null;
		if(aDestinationName.equals(QUEUE))
			destination = (Queue)javax.rmi.PortableRemoteObject.narrow(initContext.lookup(aDestinationName),Queue.class);
		else if(aDestinationName.equals(TOPIC))
			destination = (Topic)javax.rmi.PortableRemoteObject.narrow(initContext.lookup(aDestinationName),Topic.class);
		return destination;			
	}
}