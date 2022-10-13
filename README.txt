Stephen Wells 

I wrote the program in Java using opencsv (https://opencsv.sourceforge.net/) and jUnit for testing
It has dependencies on opencsv, apache commons, and JUnit. In my case I used versions 5.7.0, 3-3.12.0, JUnit5 respectively

To compile on windows:
javac -cp "<path to jar>;<path to jar2>" CSVCombiner.java

To run on windows:
java -cp <path to jar>;<path to jar2> CSVCombiner <csvfile1> <csvfile2> .... <output csv file>


For example, compiling and running in my case:
javac -cp ".;opencsv-5.7.0.jar;commons-lang3-3.12.0.jar" CSVCombiner.java
java -cp .;opencsv-5.7.0.jar;commons-lang3-3.12.0.jar CSVCombiner test.csv test1.csv result.csv
