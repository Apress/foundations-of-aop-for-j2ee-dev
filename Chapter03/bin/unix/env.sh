#!/bin/sh

echo "-------------------------------------------------------------------"
echo "- Foundations of Aspect-Oriented Programming for J2EE Development -"
echo "- Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -"
echo "- APress                                                          -"
echo "-                                                                 -"
echo "- Chapter 3: AspectJ                                              -"
echo "-------------------------------------------------------------------"

echo "-------------------------------"
echo "- Configuring the environment -"
echo "-------------------------------"

if [ "$JAVA_HOME" = "" ] ; then JAVA_HOME=/share/softs/j2sdk1.4.1
fi
if [ "$ASPECTJ_HOME" = "" ] ; then ASPECTJ_HOME=/share/softs/aspectj1.1.1
fi

echo "JAVA_HOME=$JAVA_HOME"
echo "ASPECTJ_HOME=$ASPECTJ_HOME"

if [ -f $JAVA_HOME/bin/java ]
then
    if [ -f $ASPECTJ_HOME/lib/aspectjtools.jar -a -f $ASPECTJ_HOME/lib/aspectjrt.jar ]
    then
        echo
    else
        echo "AspectJ has not been found."
        echo "Edit the env.sh file to set the ASPECTJ_HOME environment variable."
    fi
else
    echo "Java has not been found."
    echo "Edit the env.sh file to set the JAVA_HOME environment variable."
fi
