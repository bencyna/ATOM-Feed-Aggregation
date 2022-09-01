import java.io.*;
import java.net.*;

public class AggregationServer {
    public static void main(String[] args) {
        try {
            while (true) {
                ServerSocket ss = new ServerSocket(4567 );
                Socket s=ss.accept();  
                DataInputStream din=new DataInputStream(s.getInputStream());  
                DataOutputStream dout=new DataOutputStream(s.getOutputStream());  
                
                String putContent="";  
                putContent=din.readUTF();  
                String[] parts = putContent.split("<!endline!>;");
                PrintWriter writer = new PrintWriter("./saved/content server 1.txt", "UTF-8");
                for (int i = 0; i < parts.length; i++) {
                    writer.println(parts[i]);
                }
                writer.close();
                
                dout.writeUTF(sendToClient());  
                dout.flush();  
                ss.close();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    static String sendToClient() {
        return "success?";
    }
}