@echo off

echo -------------------------------------------------------------------
echo - Foundations of Aspect-Oriented Programming for J2EE Development -
echo - Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaill�        -
echo - APress                                                          -
echo -                                                                 -
echo - Chapter 4: JAC                                                  -
echo -------------------------------------------------------------------

echo ---------------------------------------------------------------
echo - Application: Order management                               -
echo - Aspect:      Tracing the methods defined in the Order class -
echo -              and introspecting joinpoints                   -
echo ---------------------------------------------------------------

if exist "%JAVA_HOME%\bin\java.exe" goto jac
if exist "%JAVA_HOME%\bin\java.bat" goto jac
if exist "%JAVA_HOME%\bin\java" goto jac

echo Java has not been found.
echo Edit the env.bat file to set the JAVA_HOME environment variable.

:jac
if exist "%JAC_HOME%\jac.jar" goto ok
if exist "%JAC_HOME%\lib\aopalliance.jar" goto ok

echo JAC has not been found.
echo Edit the env.bat file to set the JAC_HOME environment variable.
goto theEnd

:ok

echo ---- Compilation ----

cd ..\..

"%JAVA_HOME%\bin\javac" -classpath "%JAC_HOME%\jac.jar;%JAC_HOME%\lib\aopalliance.jar;%CLASSPATH%" -d classes src\aop\jac\*.java

echo ---- Execution ----

"%JAVA_HOME%\bin\java" -classpath "classes;%JAC_HOME%\jac.jar;%CLASSPATH%" org.objectweb.jac.core.Jac -R . -C classes;src customer3.jac

cd bin\win

:theEnd
