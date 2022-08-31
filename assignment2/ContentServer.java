import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.net.*;
public class ContentServer {
    public static void main(String[] args) {
        try {
            Socket s = new Socket("localhost", 6666);
            DataInputStream din=new DataInputStream(s.getInputStream());  
            DataOutputStream dout=new DataOutputStream(s.getOutputStream());  

            String str2="";  
            dout.writeUTF(put());  
            dout.flush();  
            str2=din.readUTF();  
            System.out.println("Content Server: "+str2);  

            dout.close();  
            s.close();  
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    static String lamport() {
        return "0";
    }

    static String put() {
        return "content";
    }
}