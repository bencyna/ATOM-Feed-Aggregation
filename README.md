# Introduction to JAVA RMI Assignment 1 Distributed Systems s2 2022

## Overveiw
Major design decision. 

- Heartbeat is supplied by content server, the Aggregation server will wait 12 seconds and if no response then it removes the content server

- Sorting files from last updated happens when the client requests it from the server, used selection sort for simplicity 

- Used threads for tracking content server times


## Compile and run instructions for linux 
compile:
```
javac GETClient.java ContentServer.java AggregationServer.java ASTrackCS.java QueueContent.java ParseXML.java
```
start server
```
java AggregationServer
```
start content server
```
java ContentServer AggregationServer:4567 ./input/file1.txt
```
Start client
```
java GETClient
```
output for client in client_output.txt


## Testing instructions
make sure that the content server files in ./saved are empty/deleted and make sure client_output.txt file is also empty/deleted. You will need to have compiled the program using the instructions above

run this command 
```
python3 test.py 
```


