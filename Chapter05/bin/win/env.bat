@echo off

echo -------------------------------------------------------------------
echo - Foundations of Aspect-Oriented Programming for J2EE Development -
echo - Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -
echo - APress                                                          -
echo -                                                                 -
echo - Chapter 5: JBoss AOP                                            -
echo -------------------------------------------------------------------

echo --------------------------------
echo - Configuring the environnment -
echo --------------------------------

if "%JAVA_HOME%" == "" set JAVA_HOME="C:\Program Files\j2sdk1.4.1"
if "%JBOSSAOP_HOME%" == "" set JBOSSAOP_HOME="C:\Program Files\jboss-aop-dr2"

echo JAVA_HOME=%JAVA_HOME%
echo JBOSSAOP_HOME=%JBOSSAOP_HOME%

if exist "%JAVA_HOME%\bin\java.exe" goto jbossaop
if exist "%JAVA_HOME%\bin\java.bat" goto jbossaop
if exist "%JAVA_HOME%\bin\java" goto jbossaop

set JAVA_HOME=
echo Java has not been found.
echo Edit the env.bat file to set the JAVA_HOME environment variable.

:jbossaop
if exist "%JBOSSAOP_HOME%\lib\jboss-aop.jar" goto ok

set JBOSSAOP_HOME=
echo JBoss AOP has not been found.
echo Edit the env.bat file to set the JBOSSAOP_HOME environment variable.

:ok
set CLASSPATH=%CLASSPATH%;%JBOSSAOP_HOME%\lib\javassist.jar;%JBOSSAOP_HOME%\lib\jboss-aop.jar;%JBOSSAOP_HOME%\lib\jboss-common.jar;%JBOSSAOP_HOME%\lib\trove.jar