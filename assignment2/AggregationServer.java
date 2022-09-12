import java.io.*;
import java.net.*;

public class AggregationServer extends Thread {
    private static int nextAvailable = 0;
    private static ASTrackCS[] activeServers = new ASTrackCS[20];
    private static String[] args;

    public AggregationServer(String[] args) {
        this.args = args;
    }

    public static void main(String[] args) {
        try {
            AggregationServer newServer = new AggregationServer(args);
            newServer.start();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void run() {
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
                    String contentHeaderType = parts[0].split("1.")[1];

                    String contentHeaderName = parts[0].split("1.lc")[0].split("name:")[1];
                    if (contentHeaderType.contains("ping")) {
                        for (int i = 0; i< activeServers.length; i++) {
                            if (activeServers[i] != null && activeServers[i].getContentServerName().trim().equals(contentHeaderName.trim())) {
                                activeServers[i].resetTimeLeft();
                                System.out.println(activeServers[i].getContentServerName());
                                System.out.println(i);
                                break;
                            }
                        }
                    }
                    else if (contentHeaderType.contains("put")) {
                        // start new thread for this particular CS
                        ASTrackCS newContentServer = new ASTrackCS(contentHeaderName);
                        newContentServer.start();
                        activeServers[nextAvailable] = newContentServer;

                        int checked=0;
                        while (activeServers[nextAvailable] != null) {
                            checked += 1;
                            if (checked > 20) {
                                //we know there are 20 content servers active, need to remove one
                            }
                            nextAvailable = (nextAvailable + 1) % 20;
                        }
                        put(parts);
                        DataOutputStream dout=new DataOutputStream(s.getOutputStream());  
                        dout.writeUTF("200 ok LC:" + String.valueOf(AStime.get()));  
                        dout.flush(); 
                    }
                    // if it contains both...
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
            System.out.println("Restarting server...");

            // AggregationServer newServer = new AggregationServer(args);
            // newServer.start();
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
                        content += strLine + "\n";
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
            String contentHeaderName = parts[0].split("1.lc")[0].split("name:")[1];
            PrintWriter writer = new PrintWriter("./saved/" + contentHeaderName + ".txt", "UTF-8");
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