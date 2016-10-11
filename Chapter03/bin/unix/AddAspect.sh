#!/bin/sh

echo "-------------------------------------------------------------------"
echo "- Foundations of Aspect-Oriented Programming for J2EE Development -"
echo "- Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -"
echo "- APress                                                          -"
echo "-                                                                 -"
echo "- Chapter 3: AspectJ                                              -"
echo "-------------------------------------------------------------------"

echo "-------------------------------------------------------------"
echo "- Application: Order management                             -"
echo "- Aspect:      Introducing a date for each order            -"
echo "-------------------------------------------------------------"

if [ -f $JAVA_HOME/bin/java ]
then
    if [ -f $ASPECTJ_HOME/lib/aspectjtools.jar -a -f $ASPECTJ_HOME/lib/aspectjrt.jar ]
    then
        echo ---- Compilation ----

        "$JAVA_HOME/bin/java" -classpath "$ASPECTJ_HOME/lib/aspectjtools.jar:$ASPECTJ_HOME/lib/aspectjrt.jar:$CLASSPATH" org.aspectj.tools.ajc.Main -d ../../classes @../../default4.lst

        echo ---- Execution ----

        "$JAVA_HOME/bin/java" -classpath "../../classes:$ASPECTJ_HOME/lib/aspectjrt.jar:$CLASSPATH" aop.aspectj.Customer
    else
        echo "AspectJ has not been found."
        echo "Edit the env.sh file to set the ASPECTJ_HOME environment variable."
    fi
else
    echo "Java has not been found."
    echo "Edit the env.sh file to set the JAVA_HOME environment variable."
fi

