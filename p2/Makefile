###
# This Makefile can be used to make a scanner for the CFlat language
# (Yylex.class) and to make a program that tests the scanner (P2.class).
#
# The default makes both the scanner and the test program.
#
# make clean removes all generated files.
#
# Note: P2.java will not compile unless Yylex.class exists.
#
###

# define the java compiler to be used and the flags
JC = javac
FLAGS = -g -cp $(CP)
#CP = /home/alpha/cs536_fall2015:/home/alpha/cs536_fall2015/java_cup_src/classes/:.
IN = input/
OUT = output/
EXP = expect/
#CP = /afs/cs.wisc.edu/u/m/o/morganz/Desktop/tmp:/afs/cs.wisc.edu/u/m/o/morganz/Desktop/tmp/java_cup_src/classes/:.
CP = ~cs536-1/public/tools/deps:.

P2.class: P2.java Yylex.class sym.class
	$(JC) $(FLAGS) P2.java

Yylex.class: CFlat.jlex.java ErrMsg.class sym.class
	$(JC) $(FLAGS) CFlat.jlex.java

CFlat.jlex.java: CFlat.jlex sym.class
	java -cp $(CP) JLex.Main CFlat.jlex

sym.class: sym.java
	$(JC) $(FLAGS) sym.java

ErrMsg.class: ErrMsg.java
	$(JC) $(FLAGS) ErrMsg.java

	
###
# testing - add more here to run your tester and compare its results
# to expected results
###
test:
	java -cp $(CP) P2 2> $(OUT)allErrors.out
	diff $(IN)allTokens.in $(OUT)allTokens.out
	###for strings
	diff $(OUT)strings.out $(EXP)strings.exp
	###for integers
	diff $(OUT)integers.out $(EXP)integers.exp
	###for comments
	diff $(OUT)comments.out $(EXP)comments.exp
	###for all errors
	diff $(OUT)allErrors.out $(EXP)allErrors.exp


###
# clean up
###

clean:
	rm -f *~ *.class CFlat.jlex.java
	rm -f $(OUT)*

cleantest:
	rm -f $(OUT)*
