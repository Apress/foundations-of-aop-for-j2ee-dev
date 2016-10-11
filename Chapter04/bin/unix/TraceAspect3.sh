#!/bin/sh

echo "-------------------------------------------------------------------"
echo "- Foundations of Aspect-Oriented Programming for J2EE Development -"
echo "- Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -"
echo "- APress                                                          -"
echo "-                                                                 -"
echo "- Chapter 4: JAC                                                  -"
echo "-------------------------------------------------------------------"

echo "-------------------------------------------------------------"
echo "- Application: Order management                             -"
echo "- Aspect:      Tracing the Order class methods              -"
echo "-              and instropecting joinpoints                 -"
echo "-------------------------------------------------------------"

if [ -f $JAVA_HOME/bin/java ]
then
    if [ -f $JAC_HOME/jac.jar -a -f $JAC_HOME/lib/aopalliance.jar ]
    then
        echo ---- Compilation ----

	cd ../..

        "$JAVA_HOME/bin/javac" -classpath "$JAC_HOME/jac.jar:$JAC_HOME/lib/aopalliance.jar:$CLASSPATH" -d classes src/aop/jac/*.java

        echo ---- Execution ----

        "$JAVA_HOME/bin/java" -classpath "classes:$JAC_HOME/jac.jar:$CLASSPATH" org.objectweb.jac.core.Jac -R . -C classes:src customer3.jac
    else
        echo "JAC has not been found."
        echo "Edit the env.sh file to set the JAC_HOME environment variable."
    fi
else
    echo "Java has not been found."
    echo "Edit the env.sh file to set the JAVA_HOME environment variable."
fi

