# Introduction to JAVA RMI Assignment 1 Distributed Systems s2 2022

## Overveiw
Major design decision accross synchonising functions in the sorterImplementation. 

- When a content server sends new data to the aggregate server, it will override the old content for this server, meaning one server can only give 1 piece of information at a time

- I am yet to implement the queue functionality for requests, my initial idea was to receive requests as they come and add them to a queue. However, the one time socket that I am using means that I can't do it this way. I also thought about seperating requests in threads but again not sure how to go about setting this up. I think I have most of the other functionaity other than the bonus marks.

## Compile and run instructions for linux 
javac GETClient.java ContentServer.java AggregationServer.java ASTrackCS.java QueueContent.java

## Testing instructions
make sure that the content server files in ./saved are empty/deleted and make sure client_output.txt file is also empty/deleted

run this command 
```
python3 test.py 
```
## The output file will be created, the test cases are printed to the console and the client process will close. 

