import java.io.*;
import java.net.*;

public class AggregationServer extends Thread {
    String[] activeServers = new String[20];
    private String contentServerName;
    private int timeLeft;

    public AggregationServer(String contentServerName) {
        try {
            this.contentServerName = contentServerName;
            this.timeLeft = 12;
            while (true) {
                Thread.sleep(1000);
                this.timeLeft -= 1;
                if (this.timeLeft == 0) {
                    System.out.println("Timer ended, time to remove content server content and kill thread");
                }
            }
        }
        catch (Exception e) {

        }
    }

    private void extendContentServerLife(String contentServerName) {
        if (this.contentServerName.equals(contentServerName)) {
            this.timeLeft = 12;
        }
    }

    //can have a constructor for threads that come from content servers 
    // and set the content server number to that value, so when we do 
    // stuff to that thread, we do it to the right content server

    @Override
    public void run() {
        // keeping track of content server counters
        
    }
    public static void main(String[] args) {
        try {
            LamportClock AStime = new LamportClock();
            Integer server = 4567;
            if (args.length >= 1) {
                server = Integer.parseInt(args[0]);
            }
            while (true) {
                ServerSocket ss = new ServerSocket(server);
                Socket s=ss.accept();  
                DataInputStream din=new DataInputStream(s.getInputStream());  
                
                String putContent="";  
                putContent=din.readUTF();  
                String[] parts = putContent.split("<!endline!>;");

                if (parts[0].contains("content server")) {
                    String contentHeaderType = parts[0].split("1.")[0];
                    String contentHeaderName = parts[0].split("1.")[1];
                    if (contentHeaderType.contains("ping")) {
                       
                    }
                    // start new thread for this particular CS
                    AggregationServer newContentServer = new AggregationServer(contentHeaderName);
                    newContentServer.start();
                    put(parts);
                    DataOutputStream dout=new DataOutputStream(s.getOutputStream());  
                    dout.writeUTF("200 ok LC:" + String.valueOf(AStime.get()));  
                    dout.flush(); 

                }
                else if (parts[0].contains("client server")) {
                    DataOutputStream dout=new DataOutputStream(s.getOutputStream());  
                    sendToClient();
                    dout.writeUTF(sendToClient());  
                    dout.flush(); 
                }
                
                
               
                ss.close();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    static String sendToClient() {
        // read files and return contents in string format
        try {
            String content = "";

            File folder = new File("./saved");
            File[] listOfFiles = folder.listFiles();
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    FileInputStream fstream = new FileInputStream(file);
                    BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
                    String strLine;
    
                    //Read File Line By Line
                    while ((strLine = br.readLine()) != null)   {
                    // if current line doesn't have an identifyer, we remove the last endline then add this line
                    if (!strLine.contains("<!endline!>;")) {
                        content += strLine;
                    }
                    else {
                        content += strLine + "\n";
                    }
                    }
                    fstream.close();
                }
            }    
            return content;
        }
        catch (Exception e) {
            System.out.println("Error");
            return "Error";
        }
    }
    static void put(String[] parts) {
        try {
            PrintWriter writer = new PrintWriter("./saved/content server 1.txt", "UTF-8");
            for (int i = 1; i < parts.length; i++) {
                writer.println(parts[i]);
            }
            writer.close();
            // call ping because we know the content server is active
            
        }
        catch (Exception e) {
            System.out.println("Error");
        }
    }
    void controlContent(String contentServerName) {
        // reset timer to 0
    }
}