@echo off

echo -------------------------------------------------------------------
echo - Foundations of Aspect-Oriented Programming for J2EE Development -
echo - Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -
echo - APress                                                          -
echo -                                                                 -
echo - Chapter 3: AspectJ                                              -
echo -------------------------------------------------------------------

echo ---------------------------------------------------------------
echo - Application: Order management                               -
echo - Aspect:      Tracing the addItem method                     -
echo ---------------------------------------------------------------

if exist "%JAVA_HOME%\bin\java.exe" goto aspectj
if exist "%JAVA_HOME%\bin\java.bat" goto aspectj
if exist "%JAVA_HOME%\bin\java" goto aspectj

echo Java has not been found.
echo Edit the env.bat file to set the JAVA_HOME environment variable.

:aspectj
if exist "%ASPECTJ_HOME%\lib\aspectjtools.jar" goto ok
if exist "%ASPECTJ_HOME%\lib\aspectjrt.jar" goto ok

echo AspectJ has not been found.
echo Edit the env.bat file to set the ASPECTJ_HOME environment variable.
goto theEnd

:ok

echo ---- Compilation ----

"%JAVA_HOME%\bin\java" -classpath "%ASPECTJ_HOME%\lib\aspectjtools.jar;%ASPECTJ_HOME%\lib\aspectjrt.jar;%CLASSPATH%" org.aspectj.tools.ajc.Main -d ..\..\classes @..\..\default.lst

echo ---- Execution ----

"%JAVA_HOME%\bin\java" -classpath "..\..\classes;%ASPECTJ_HOME%\lib\aspectjrt.jar;%CLASSPATH%" aop.aspectj.Customer

:theEnd
