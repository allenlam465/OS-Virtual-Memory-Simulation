Name: Allen Lam & John Ung
Program:CS431_Project2
Date: March 3, 2018

Description:
A simulation of virtual memory using Java. Implements FIFO for the TLB and clock algorithm for page replacement.

Reads in a file with following format:

	1	-Read or write bit
	A242	-Virtual memory address
	21412	-Integer to write into memory
	0	-Read or write bit
	B321	-Virtual memory address to read integer

	*R/W Bit is determined by a 0/1 respectivly.

Copies page files provided to a new directory page_files_copy for usage without changing original page files.

Notes:
The CSV files might have formatting issues due to how excel reads a data cell. 
Leading 0's are not shown within the file and E are taken in as a exponent declaration. 

Usage: 
All commands were run in terminal within the directory the files exists.

Compile within a terminal with the following commands

	javac Driver.java

Run within a terminal with the following commands

	java Driver

Input file name to process simulation with.
Make sure that the file exists in the same directory.