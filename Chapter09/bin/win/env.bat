@echo off

echo -------------------------------------------------------------------
echo - Foundations of Aspect-Oriented Programming for J2EE Development -
echo - Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -
echo - APress                                                          -
echo -                                                                 -
echo - Chapter 9: Quality of Service and AOP                           -
echo -------------------------------------------------------------------

echo --------------------------------
echo - Configuring the environnment -
echo --------------------------------

if "%JAVA_HOME%" == "" set JAVA_HOME="C:\j2sdk1.4.1"
if "%JBOSSAOP_HOME%" == "" set JBOSSAOP_HOME="C:\JBOSSAOP"

echo JAVA_HOME=%JAVA_HOME%
echo JBOSSAOP_HOME=%JBOSSAOP_HOME%
echo The CLASSPATH environment variable must reference the MX4J librairies

if exist "%JAVA_HOME%\bin\java.exe" goto JBOSSAOP
if exist "%JAVA_HOME%\bin\java.bat" goto JBOSSAOP
if exist "%JAVA_HOME%\bin\java" goto JBOSSAOP

set JAVA_HOME=
echo Java has not been found.
echo Edit the env.bat file to set the JAVA_HOME environment variable.

:JBOSSAOP
if exist "%JBOSSAOP_HOME%\jboss-aop.jar" goto ok

set JBOSSAOP_HOME=
echo JBoss AOP has not been found.
echo Edit the env.bat file to set the JBOSSAOP_HOME environment variable.

:ok
