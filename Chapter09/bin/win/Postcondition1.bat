@echo off

echo -------------------------------------------------------------------
echo - Foundations of Aspect-Oriented Programming for J2EE Development -
echo - Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -
echo - APress                                                          -
echo -                                                                 -
echo - Chapter 9: Quality of Service and AOP                           -
echo -------------------------------------------------------------------

echo --------------------------------------------
echo - Application: PostConditionExample1       -
echo - Aspect:      Postcondition               -
echo --------------------------------------------

if exist "%JAVA_HOME%\bin\java.exe" goto JBOSSAOP
if exist "%JAVA_HOME%\bin\java.bat" goto JBOSSAOP
if exist "%JAVA_HOME%\bin\java" goto JBOSSAOP

echo Java has not been found.
echo Edit the env.bat file to set the JAVA_HOME environment variable.

:JBOSSAOP
if exist "%JBOSSAOP_HOME%\jboss-aop.jar" goto ok

echo JBoss AOP has not been found.
echo Edit the env.bat file to set the JBOSSAOP_HOME environment variable.
goto theEnd

:ok

echo ---- Compilation ----

cd ..\..

"%JAVA_HOME%\bin\javac" -classpath "%JBOSSAOP_HOME%\jboss-aop.jar;%JBOSSAOP_HOME%\javassist.jar;%JBOSSAOP_HOME%\jboss-common.jar;%JBOSSAOP_HOME%\trove.jar;%CLASSPATH%" -d classes src\aop\contracts\postconditions\*.java

echo ---- Execution ----

"%JAVA_HOME%\bin\java" -classpath "classes;src;%JBOSSAOP_HOME%\jboss-aop.jar;%JBOSSAOP_HOME%\javassist.jar;%JBOSSAOP_HOME%\jboss-common.jar;%JBOSSAOP_HOME%\trove.jar;%CLASSPATH%" -Djava.system.class.loader=org.jboss.aop.standalone.SystemClassLoader aop.contracts.postconditions.PostConditionExample1

cd bin\win

:theEnd
