@echo off

echo -------------------------------------------------------------------
echo - Foundations of Aspect-Oriented Programming for J2EE Development -
echo - Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -
echo - APress                                                          -
echo -                                                                 -
echo - Chapter 5: JBoss AOP                                            -
echo -------------------------------------------------------------------

echo ---------------------------------------------------------------
echo - Application: Order management                               -
echo - Aspect:      Tracing the addItem method                     -
echo ---------------------------------------------------------------

if exist "%JAVA_HOME%\bin\java.exe" goto jbossaop
if exist "%JAVA_HOME%\bin\java.bat" goto jbossaop
if exist "%JAVA_HOME%\bin\java" goto jbossaop

echo Java has not been found.
echo Edit the env.bat file to set the JAVA_HOME environment variable.

:jbossaop
if exist "%JBOSSAOP_HOME%\lib\jboss-aop.jar" goto ok

echo JBoss AOP has not been found.
echo Edit the env.bat file to set the JBOSSAOP_HOME environment variable.
goto theEnd

:ok

echo ---- Compilation ----

cd ..\..

"%JAVA_HOME%\bin\javac" -d classes src\aop\jboss\*.java
copy src\TraceAspect.xml src\META-INF\jboss-aop.xml

echo ---- Execution ----

"%JAVA_HOME%\bin\java" -classpath "src;classes;%CLASSPATH%" -Djava.system.class.loader=org.jboss.aop.standalone.SystemClassLoader aop.jboss.Customer

cd bin\win

:theEnd
