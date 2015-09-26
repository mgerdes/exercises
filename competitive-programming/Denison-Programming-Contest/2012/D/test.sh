#!/bin/bash
javac prob.java
time (java Main < ./$1/input.in 1> ./$1/actual.out) 2> ./$1/time.txt
diff -y ./$1/actual.out ./$1/expected.out
cat ./$1/time.txt
