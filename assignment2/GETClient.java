import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.net.*;
public class GETClient {
    public static void main(String[] args) {
        try {
            Socket s = new Socket("localhost", 4567 );
            DataInputStream din=new DataInputStream(s.getInputStream());  
            DataOutputStream dout=new DataOutputStream(s.getOutputStream());  

            String str2="";  
            dout.writeUTF("client server");  
            dout.flush();  
            str2=din.readUTF();  
            System.out.println("Server says: "+str2);  

            dout.close();  
            s.close();  
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    static String lamport() {
        return "0";
    }

    static void get(Socket s) {
        System.out.println("client");
        return;
    }
}