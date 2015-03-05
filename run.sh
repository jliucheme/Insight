#! /bin/bash

rm -rf wc_output
mvn clean install
mvn exec:java -Dexec.mainClass=com.igalia.wordcount.Main
cat wc_output/part-r-00000 > wc_output/wc_result.txt

javac -classpath . MedMain.java
java MedMain
