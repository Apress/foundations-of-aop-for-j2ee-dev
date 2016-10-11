@echo off

echo -------------------------------------------------------------------
echo - Foundations of Aspect-Oriented Programming for J2EE Development -
echo - Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -
echo - APress                                                          -
echo -                                                                 -
echo - Chapter 4: JAC                                                  -
echo -------------------------------------------------------------------

echo --------------------------------
echo - Configuring the environnment -
echo --------------------------------

if "%JAVA_HOME%" == "" set JAVA_HOME="C:\Program Files\j2sdk1.4.1"
if "%JAC_HOME%" == "" set JAC_HOME="C:\jac"

echo JAVA_HOME=%JAVA_HOME%
echo JAC_HOME=%JAC_HOME%

if exist "%JAVA_HOME%\bin\java.exe" goto aspectj
if exist "%JAVA_HOME%\bin\java.bat" goto aspectj
if exist "%JAVA_HOME%\bin\java" goto aspectj

set JAVA_HOME=
echo Java has not been found.
echo Edit the env.bat file to set the JAVA_HOME environment variable.

:aspectj
if exist "%JAC_HOME%\jac.jar" goto ok
if exist "%JAC_HOME%\lib\aopalliance.jar" goto ok

set JAC_HOME=
echo JAC has not been found.
echo Edit the env.bat file to set the JAC_HOME environment variable.

:ok
