from subprocess import run
import time

print("starting your program...")
run(["javac", "AggregationServer.java"]);
run(["javac", "GETClient.java"]);
run(["javac", "ContentServer.java"]);
run(["javac", "LamportClock.java"]);