@echo off

echo ------------------------------------------------------------
echo - La programmation orientee aspect pour Java/J2EE          -
echo - Renaud Pawlak, Jean-Philippe Retaille, Lionel Seinturier -
echo - Editions Eyrolles                                        -
echo -                                                          -
echo - Partie III : Applications de la POA                      -
echo - Chapitre 8 : Design patterns et POA                      -
echo ------------------------------------------------------------

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
