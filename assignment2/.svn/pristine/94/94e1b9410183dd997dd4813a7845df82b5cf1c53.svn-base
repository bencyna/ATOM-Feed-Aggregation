import java.io.*;
import java.net.*;

public class AggregationServer {
    public static void main(String[] args) {
        try {
            while (true) {
                ServerSocket ss = new ServerSocket(4567);
                Socket s=ss.accept();  
                DataInputStream din=new DataInputStream(s.getInputStream());  
                DataOutputStream dout=new DataOutputStream(s.getOutputStream());  
                
                String putContent="";  
                putContent=din.readUTF();  
                String[] parts = putContent.split("<!endline!>;");

                if (parts[0].contains("content server")) {
                    put(parts);
                }
                else if (parts[0].contains("client server")) {
                    sendToClient();
                }
                
                dout.writeUTF(sendToClient());  
                dout.flush();  
                ss.close();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    static String sendToClient() {
        // read file and return contents in string format
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
            PrintWriter writer = new PrintWriter("./saved/content server 1.txt", "UTF-8");
            for (int i = 1; i < parts.length; i++) {
                writer.println(parts[i]);
            }
            writer.close();
        }
        catch (Exception e) {
            System.out.println("Error");
        }
    }
}