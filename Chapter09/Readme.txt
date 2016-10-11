-------------------------------------------------------------------
- Foundations of Aspect-Oriented Programming for J2EE Development -
- Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -
- APress                                                          -
-                                                                 -
- Chapter 9: Quality of service and AOP                           -
-------------------------------------------------------------------

You will find in this directory the code example given in Chapter 9.

This directory contains the following files:

-   bin\win     Windows files for launching the examples
-   bin/unix    Unix files for launching the examples
-   classes     .class files
-   src         source files
-   Readme.txt  this file

Prerequisites
-------------
The examples require:
- J2SE == 1.4.x
- JBoss AOP == dr2

For the supervison aspect only:
- MX4J


Execution des exemples sous Windows
===================================

1. Configuration
----------------
In the directory bin\win, edit env.bat,
set JAVA_HOME and JBOSSAOP_HOME to directories where J2SE and JBoss AOP have
been installed.

To test the JMX examples, the CLASSPATH environment variable must contain the
MX4J libraries (jar files)

In a DOS windows:
- set the current directory to bin\win
- run env.bat

2. Execution
------------
In the same DOS windows as the one where env.bat has been launched,
launch the .bat files to compile and run the examples.


Running the examples under Unix
===============================

1. Configuration
----------------
In the directory bin/unix, edit env.sh,
set JAVA_HOME and JBOSSAOP_HOME to directories where J2SE and AspectJ have been
installed.

To test the JMX examples, the CLASSPATH environment variable must contain the
MX4J libraries (jar files)

In a shell:
- set the current directory to bin/unix
- run sh env.sh

2. Execution
------------
In the same shell as the one where env.sh has been launched,
launch the .sh files to compile and run the examples.
