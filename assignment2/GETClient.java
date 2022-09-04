import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.net.*;

public class GETClient {
    /**
     * @param attempts
     */
    static void connect(Integer attempts) {
        try {
            Socket s = new Socket("localhost", 4567);
            DataInputStream din = new DataInputStream(s.getInputStream());
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());

            String str2 = "";
            dout.writeUTF("client server");
            dout.flush();
            str2 = din.readUTF();
            System.out.println("Server says: " + str2);

            dout.close();
            s.close();

        } catch (Exception e) {
            if (attempts < 3) {
                System.out.println("Connection failed, trying again...");
                try {
                    Thread.sleep(500);
                }
                catch(Exception err) {
                    System.out.println(e);
                }
                connect(attempts + 1);
            } else {
                System.out.println("Connection failed, unable to get content.");
            }
        }
    }

    public static void main(String[] args) {
        connect(0);
    }

    static String lamport() {
        return "0";
    }

    static void get(Socket s) {
        System.out.println("client");
        return;
    }
}