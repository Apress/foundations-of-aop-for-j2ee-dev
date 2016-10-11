#!/bin/sh

echo "-------------------------------------------------------------------"
echo "- Foundations of Aspect-Oriented Programming for J2EE Development -"
echo "- Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -"
echo "- APress                                                          -"
echo "-                                                                 -"
echo "- Chapter 9: Quality of Service and AOP                           -"
echo "-------------------------------------------------------------------"

echo "---------------------------"
echo "- Application: JMXExample -"
echo "- Aspect:      JMX Simple -"
echo "---------------------------"

if [ -f $JAVA_HOME/bin/java ]
then
    if [ -f $JBOSSAOP_HOME/jboss-aop.jar ]
    then
        echo ---- Compilation ----

	cd ../..

        "$JAVA_HOME/bin/javac" -classpath "$JBOSSAOP_HOME/jboss-aop.jar:$JBOSSAOP_HOME/javassist.jar:$JBOSSAOP_HOME/jboss-common.jar:$JBOSSAOP_HOME/trove.jar:$CLASSPATH" -d classes src/aop/management/jmx/simple/*.java

        echo ---- Execution ----

        "$JAVA_HOME/bin/java" -classpath "classes:src:$JBOSSAOP_HOME/jboss-aop.jar:$JBOSSAOP_HOME/javassist.jar:$JBOSSAOP_HOME/jboss-common.jar:$JBOSSAOP_HOME/trove.jar:$CLASSPATH"  -Djava.system.class.loader=org.jboss.aop.standalone.SystemClassLoader aop.management.jmx.simple.JMXExample
    else
        echo "JBoss AOP has not been found."
        echo "Edit the env.sh file to set the JBOSSAOP_HOME environment variable."
    fi
else
    echo "Java has not been found."
    echo "Edit the env.sh file to set the JAVA_HOME environment variable."
fi
