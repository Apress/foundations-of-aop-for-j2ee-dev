#!/bin/sh

echo "-------------------------------------------------------------------"
echo "- Foundations of Aspect-Oriented Programming for J2EE Development -"
echo "- Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -"
echo "- APress                                                          -"
echo "-                                                                 -"
echo "- Chapter 5: JBoss AOP                                            -"
echo "-------------------------------------------------------------------"

echo "-------------------------------"
echo "- Configuring the environment -"
echo "-------------------------------"

if [ "$JAVA_HOME" = "" ] ; then JAVA_HOME=/share/softs/j2sdk1.4.1
fi
if [ "$JBOSSAOP_HOME" = "" ] ; then JBOSSAOP_HOME=/share/softs/jbossaop
fi

echo "JAVA_HOME=$JAVA_HOME"
echo "JBOSSAOP_HOME=$JBOSSAOP_HOME"

if [ -f $JAVA_HOME/bin/java ]
then
    if [ -f $JBOSSAOP_HOME/lib/jboss-aop.jar]
    then
        echo
    else
        echo "JBoss AOP has not been found."
        echo "Edit the env.sh file to set the JBOSSAOP_HOME environment variable."
    fi
else
    echo "Java has not been found."
    echo "Edit the env.sh file to set the JAVA_HOME environment variable."
fi
