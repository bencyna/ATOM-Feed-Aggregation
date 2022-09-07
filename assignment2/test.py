from subprocess import run, Popen
import time

# input from 1 content server matches output from one client
def basics():
    server = Popen(["java", "AggregationServer"]) 
    run(["java", "ContentServer", "AggregationServer:4567", "./input/file1.txt"])
    time.sleep(1)
    run(["java", "GETClient", "AggregationServer:4567"])
    server.kill()
    



# 3 content server do put, content server 2 is killed, ensure the next get is correct
def killCS():
    pass


# client tries reconnecting, aggregation server killed
def failures():
    pass


# content server put to AS, client GET, kill AS, restart AS client does GET
def advanced():
    pass


basics();