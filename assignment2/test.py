from curses import echo
from subprocess import run, Popen
import time
from pathlib import Path
import os
import glob

def removeWaste():
    files = glob.glob('saved/*')
    for f in files:
        os.remove(f)

    open('server_state.txt', 'w').close()
    open('client_output.txt', 'w').close()


# input from 1 content server matches output from one client
def basics():
    server = Popen(["java", "AggregationServer"]) 
    time.sleep(1)
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
                    if output_contents[expectedOutputLineNum].strip() == "entry":
                                expectedOutputLineNum += 1
                    
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
    removeWaste()
    time.sleep(1)
    



# 3 content servers do a put, content server 1 is allowed to die, ensure the next get is correct
def killCS():
    server = Popen(["java", "AggregationServer"]) 
    time.sleep(1)
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
                            if output_contents[expectedOutputLineNum].strip() == "entry":
                                expectedOutputLineNum += 1
                            
                            if output_contents[expectedOutputLineNum].strip() == line.strip():
                                print(f"test {expectedOutputLineNum}: \" \n {line} \" passed")
                                passCount += 1
                            else:
                                print(f"test {expectedOutputLineNum}: \" \n {line} \" failed, expected {output_contents[expectedOutputLineNum]}")
                                failCount +=1
                        
                            expectedOutputLineNum += 1
                    
                    for line in input_contents3: 
                        if expectedOutputLineNum >= len(output_contents):
                            break

                        if len(str(output_contents).strip()) > 0:
                            if output_contents[expectedOutputLineNum].strip() == "entry":
                                expectedOutputLineNum += 1
                            
                            if output_contents[expectedOutputLineNum].strip() == line.strip():
                                print(f"test {expectedOutputLineNum}: \" \n {line} \" passed")
                                passCount += 1
                            else:
                                print(f"test {expectedOutputLineNum}: \" \n {line} \" failed, expected {output_contents[expectedOutputLineNum]}")
                                failCount +=1
                        
                            expectedOutputLineNum += 1
                    
                    
                    print(f"{passCount} lines passed, {failCount} lines failed, lines completed: {expectedOutputLineNum}/{len(input_contents2)+len(input_contents3)}")

    contentServer1.terminate()
    contentServer2.terminate()
    contentServer3.terminate()
    server.terminate()
    removeWaste()
    time.sleep(1)

# aggregation server killed
def failures():
    server = Popen(["java", "AggregationServer"]) 
    time.sleep(1)
    contentServer1 = Popen(["java", "ContentServer", "AggregationServer:4567", "./input/file1.txt"])
    time.sleep(1)
    contentServer2 = Popen(["java", "ContentServer", "AggregationServer:4567", "./input/file2.txt"])
    time.sleep(1)
    server.terminate()
    time.sleep(1)
    new_server = Popen(["java", "AggregationServer"]) 
    run(["java", "GETClient", "AggregationServer:4567"])
    time.sleep(.2)

    with open("./client_output.txt") as output:
        with open("./input/file1.txt") as input:
            with open("./input/file2.txt") as input2:
                input_contents = input.readlines()
                input_contents2 = input2.readlines()
                output_contents = output.readlines()
                passCount = 0
                failCount = 0
                expectedOutputLineNum = 0     

                for line in input_contents: 
                    if expectedOutputLineNum >= len(output_contents):
                        break

                    if len(str(output_contents).strip()) > 0:
                        if output_contents[expectedOutputLineNum].strip() == "entry":
                            expectedOutputLineNum += 1

                        if output_contents[expectedOutputLineNum].strip() == line.strip():
                            print(f"test {expectedOutputLineNum}: \" \n {line} \" passed")
                            passCount += 1
                        else:
                            print(f"test {expectedOutputLineNum}: \" \n {line} \" failed, expected {output_contents[expectedOutputLineNum]}")
                            failCount +=1
                    
                        expectedOutputLineNum += 1
                        
                for line in input_contents2: 
                    if expectedOutputLineNum >= len(output_contents):
                        break

                    if len(str(output_contents).strip()) > 0:
                        if output_contents[expectedOutputLineNum].strip() == "entry":
                            expectedOutputLineNum += 1

                        if output_contents[expectedOutputLineNum].strip() == line.strip():
                            print(f"test {expectedOutputLineNum}: \" \n {line} \" passed")
                            passCount += 1
                        else:
                            print(f"test {expectedOutputLineNum}: \" \n {line} \" failed, expected {output_contents[expectedOutputLineNum]}")
                            failCount +=1
                    
                        expectedOutputLineNum += 1
            
        print(f"{passCount} lines passed, {failCount} lines failed, lines completed: {expectedOutputLineNum}/{len(output_contents)}")



    contentServer1.terminate()
    contentServer2.terminate()
    new_server.terminate()
    removeWaste()
    time.sleep(1)

# for this one, I created 16 clones of file 2
# the test will first get file 1, and then open 20 different content servers, the aim of these tests is to 1
# ensure the order is correct, and 2, ensure that the least recently updated has been removed
def maxContentServers():
    server = Popen(["java", "AggregationServer"]) 

    time.sleep(.5)
    contentServer1 = Popen(["java", "ContentServer", "AggregationServer:4567", "./input/file1.txt"])
    contentServers = [contentServer1]
    time.sleep(.5)

    for x in range(2, 23):
        print(x)
        contentServers.append(Popen(["java", "ContentServer", "AggregationServer:4567", f"./input/file{x}.txt"]))
        time.sleep(.5)
    
    run(["java", "GETClient", "AggregationServer:4567"])
    time.sleep(.5)

    for x in range(2, 23):
        #compare files 2-22 with output, if same sucess
        pass
    

    for CServer in contentServers:
        CServer.terminate()

    server.terminate()
    removeWaste()
    time.sleep(1)




# basics()
# killCS()
failures()
# maxContentServers()
