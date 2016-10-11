-------------------------------------------------------------------
- Foundations of Aspect-Oriented Programming for J2EE Development -
- Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -
- APress                                                          -
-                                                                 -
- Chapter 8: Design Patterns and AOP                              -
-------------------------------------------------------------------

This directory contains the AspectJ code example given in Chapter 8.

This directory contains the following files:

-   bin\win     Windows files for launching the examples
-   bin/unix    Unix files for launching the examples
-   classes     .class files
-   src         source files

-   default1.lst	AspectJ file to compile the examples
-   Readme.txt		this file


Prerequisites
-------------
The examples require:
- J2SE == 1.4.x
- AspectJ >= 1.1


Running the examples under Windows
==================================

1. Configuration
----------------
In the directory bin\win, edit env.bat,
set JAVA_HOME and ASPECTJ_HOME to directories where J2SE and AspectJ have been
installed.

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
set JAVA_HOME and ASPECTJ_HOME to directories where J2SE and AspectJ have been
installed.

In a shell:
- set the current directory to bin/unix
- run sh env.sh

2. Execution
------------
In the same shell as the one where env.sh has been launched,
launch the .sh files to compile and run the examples.
