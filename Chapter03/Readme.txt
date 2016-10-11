-------------------------------------------------------------------
- Foundations of Aspect-Oriented Programming for J2EE Development -
- Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -
- APress                                                          -
-                                                                 -
- Chapter 3: AspectJ                                              -
-------------------------------------------------------------------

You will find in this directory the AspectJ code example given in Chapter 3.

This directory contains the following files:

-   bin\win     Windows files for launching the examples
-   bin/unix    Unix files for launching the examples
-   classes     .class files
-   src         source files

-   default1.lst, default2.lst, default3.lst, default4.lst
        4 AspectJ files to compile respectively
	TraceAspect1, TraceAspect2, TraceAspect3 and AddDate
-   Readme.txt
        this file


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
launch TraceAspect.bat to compile and run the TraceAspect example.

(same procedure for TraceAspect2, TraceAspect3 and AddDate).


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
launch TraceAspect.sh to compile and run the TraceAspect example.

(same procedure for TraceAspect2, TraceAspect3 and AddDate).
