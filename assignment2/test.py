from subprocess import run, Popen
import time
from pathlib import Path
import os
import glob

# input from 1 content server matches output from one client
def basics():
    server = Popen(["java", "AggregationServer"]) 
    contentServer = Popen(["java", "ContentServer", "AggregationServer:4567", "./input/file1.txt"])
    time.sleep(1)
    run(["java", "GETClient", "AggregationServer:4567"])
    print("comparing your input and output files...")
    time.sleep(1)
    with open("./client_output.txt") as output:
        with open("./input/file1.txt") as input:
            input_contents = input.readlines()
            output_contents = output.readlines()
            passCount = 0
            failCount = 0
            expectedOutputLineNum = 0     

            for line in input_contents: 
                if expectedOutputLineNum >= len(output_contents):
                    break

                if len(str(output_contents).strip()) > 0:
                    if output_contents[expectedOutputLineNum].strip() == line.strip():
                        print(f"test {expectedOutputLineNum}: \" \n {line} \" passed")
                        passCount += 1
                    else:
                        print(f"test {expectedOutputLineNum}: \" \n {line} \" failed")
                        failCount +=1
                
                    expectedOutputLineNum += 1
            
            print(f"{passCount} lines passed, {failCount} lines failed, lines completed: {expectedOutputLineNum}/{len(input_contents)}")
    server.terminate()
    contentServer.terminate()
    



# 3 content servers do a put, content server 1 is allowed to die, ensure the next get is correct
def killCS():
    server = Popen(["java", "AggregationServer"]) 
    contentServer1 = Popen(["java", "ContentServer", "AggregationServer:4567", "./input/file1.txt"])
    for x in range(0, 10):
        print(f"Letting cs1 die... {x} seconds/10")
        time.sleep(1)
    contentServer2 = Popen(["java", "ContentServer", "AggregationServer:4567", "./input/file2.txt"])
    time.sleep(1)
    contentServer3 = Popen(["java", "ContentServer", "AggregationServer:4567", "./input/file3.txt"])
    time.sleep(2)
    run(["java", "GETClient", "AggregationServer:4567"])
    print("comparing your input and output files...")
    time.sleep(1)
    with open("./client_output.txt") as output:
        with open("./input/file1.txt") as input1:
            with open("./input/file2.txt") as input2:
                with open("./input/file3.txt") as input3:
                    input_contents1 = input1.readlines()
                    input_contents2 = input2.readlines()
                    input_contents3 = input3.readlines()
                    output_contents = output.readlines()
                    passCount = 0
                    failCount = 0
                    expectedOutputLineNum = 0   

                    for line in input_contents2: 
                        if expectedOutputLineNum >= len(output_contents):
                            break

                        if len(str(output_contents).strip()) > 0:
                            if output_contents[expectedOutputLineNum].strip() == line.strip():
                                print(f"test {expectedOutputLineNum}: \" \n {line} \" passed")
                                passCount += 1
                            else:
                                print(f"test {expectedOutputLineNum}: \" \n {line} \" failed")
                                failCount +=1
                        
                            expectedOutputLineNum += 1
                    
                    for line in input_contents3: 
                        if expectedOutputLineNum >= len(output_contents):
                            break

                        if len(str(output_contents).strip()) > 0:
                            if output_contents[expectedOutputLineNum].strip() == line.strip():
                                print(f"test {expectedOutputLineNum}: \" \n {line} \" passed")
                                passCount += 1
                            else:
                                print(f"test {expectedOutputLineNum}: \" \n {line} \" failed")
                                failCount +=1
                        
                            expectedOutputLineNum += 1
                    
                    
                    print(f"{passCount} lines passed, {failCount} lines failed, lines completed: {expectedOutputLineNum}/{len(input_contents2)+len(input_contents3)}")

    contentServer1.terminate()
    contentServer2.terminate()
    contentServer3.terminate()
    server.terminate()
    files = glob.glob('saved/*')
    for f in files:
        os.remove(f)

    open('server_state.txt', 'w').close()
    open('client_output.txt', 'w').close()

# client tries reconnecting, aggregation server killed
def failures():
    server = Popen(["java", "AggregationServer"]) 
    contentServer1 = Popen(["java", "ContentServer", "AggregationServer:4567", "./input/file1.txt"])
    contentServer1 = Popen(["java", "ContentServer", "AggregationServer:4567", "./input/file2.txt"])
    server.terminate()
    run(["java", "GETClient", "AggregationServer:4567"])
    new_server = Popen(["java", "AggregationServer"]) 

    #compare output    



    contentServer1.terminate()
    new_server.terminate()


# content server put to AS, client GET, kill AS, restart AS client does GET
def advanced():
    pass


# basics()
killCS()