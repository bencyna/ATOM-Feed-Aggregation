import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.net.*;

public class GETClient {
    /**
     * @param attempts
     */
    static void connect(Integer attempts, String server) {
        try {
            Integer port = Integer.parseInt(server.split(":")[1]);
            Socket s = new Socket("localhost", port);
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
                connect(attempts + 1, server);
            } else {
                System.out.println("Connection failed, unable to get content.");
            }
        }
    }

    public static void main(String[] args) {
        String server = "AggregationServer:4567";
        if (args.length >= 1) {
            server = args[0];
        }
        connect(0, server);
    }

    static String lamport() {
        return "0";
    }

    static void get(Socket s) {
        System.out.println("client");
        return;
    }
}