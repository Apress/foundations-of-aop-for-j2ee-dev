#!/bin/sh

echo "-------------------------------------------------------------------"
echo "- Foundations of Aspect-Oriented Programming for J2EE Development -"
echo "- Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -"
echo "- APress                                                          -"
echo "-                                                                 -"
echo "- Chapter 4: JAC                                                  -"
echo "-------------------------------------------------------------------"

echo "-------------------------------"
echo "- Configuring the environment -"
echo "-------------------------------"

if [ "$JAVA_HOME" = "" ] ; then JAVA_HOME=/share/softs/j2sdk1.4.1
fi
if [ "$JAC_HOME" = "" ] ; then JAC_HOME=/share/softs/jac-0.12-beta1
fi

echo "JAVA_HOME=$JAVA_HOME"
echo "JAC_HOME=$JAC_HOME"

if [ -f $JAVA_HOME/bin/java ]
then
    if [ -f $JAC_HOME/jac.jar -a -f $JAC_HOME/lib/aopalliance.jar ]
    then
        echo
    else
        echo "JAC has not been found."
        echo "Edit the env.sh file to set the JAC_HOME environment variable."
    fi
else
    echo "Java has not been found."
    echo "Edit the env.sh file to set the JAVA_HOME environment variable."
fi
