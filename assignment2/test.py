from subprocess import run, Popen
import time

# input from 1 content server matches output from one client
def basics():
    server = Popen(["java", "AggregationServer"]) 
    run(["java", "ContentServer", "AggregationServer:4567", "./input/file1.txt"])
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
                    if output_contents[expectedOutputLineNum] == input_contents:
                        print(f"test {expectedOutputLineNum}: \"{line}\" passed")
                        passCount += 1
                    else:
                        print(f"test {expectedOutputLineNum}: \"{line}\" failed")
                        failCount +=1
                
                    expectedOutputLineNum += 1
            
            print(f"{passCount} tests passed, {failCount} tests failed, tests completed: {expectedOutputLineNum}/{len(input_contents)}")
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