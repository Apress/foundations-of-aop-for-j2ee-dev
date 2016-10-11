@echo off

echo -------------------------------------------------------------------
echo - Foundations of Aspect-Oriented Programming for J2EE Development -
echo - Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -
echo - APress                                                          -
echo -                                                                 -
echo - Chapter 3: AspectJ                                              -
echo -------------------------------------------------------------------

echo --------------------------------
echo - Configuring the environnment -
echo --------------------------------

if "%JAVA_HOME%" == "" set JAVA_HOME="C:\Program Files\j2sdk1.4.1"
if "%ASPECTJ_HOME%" == "" set ASPECTJ_HOME="C:\Program Files\aspectj1.1.1"

echo JAVA_HOME=%JAVA_HOME%
echo ASPECTJ_HOME=%ASPECTJ_HOME%

if exist "%JAVA_HOME%\bin\java.exe" goto aspectj
if exist "%JAVA_HOME%\bin\java.bat" goto aspectj
if exist "%JAVA_HOME%\bin\java" goto aspectj

set JAVA_HOME=
echo Java has not been found.
echo Edit the env.bat file to set the JAVA_HOME environment variable.

:aspectj
if exist "%ASPECTJ_HOME%\lib\aspectjtools.jar" goto ok
if exist "%ASPECTJ_HOME%\lib\aspectjrt.jar" goto ok

set ASPECTJ_HOME=
echo AspectJ has not been found.
echo Edit the env.bat file to set the ASPECTJ_HOME environment variable.

:ok
