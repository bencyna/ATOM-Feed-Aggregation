import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.net.*;

public class GETClient {
    /**
     * @param attempts
     */
    static void connect(Integer attempts, String server) {
        try {
            FileInputStream LCNum = new FileInputStream("GETClientLamportClock.txt");
            BufferedReader LamportClockSavedNumber = new BufferedReader(new InputStreamReader(LCNum));
            String num = LamportClockSavedNumber.readLine();

            int lamportNumber = Integer.parseInt(num);

            LamportClock ClientTime = new LamportClock(lamportNumber);

            LamportClockSavedNumber.close();

            Integer port = Integer.parseInt(server.split(":")[1]);
            Socket s = new Socket("localhost", port);
            DataInputStream din = new DataInputStream(s.getInputStream());
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());

            String serverContentAll = "";
            dout.writeUTF("type: get name: client server lc:" + String.valueOf(ClientTime.get()));
            dout.flush();
            serverContentAll = din.readUTF();
            String serverContent = serverContentAll.split("<!endline!>;")[1];
            Integer ServerLamport = Integer.parseInt(serverContentAll.split("<!endline!>;")[0]);

            System.out.println("Server says: " + serverContent);
            PrintWriter writer = new PrintWriter("client_output.txt", "UTF-8");
            writer.print(serverContent);
            writer.close();

            dout.close();
            s.close();

            // read server LC and update
            ClientTime.Set(ServerLamport, ClientTime.get());
            PrintWriter writeLC = new PrintWriter("GETClientLamportClock.txt", "UTF-8");
            writeLC.print(ClientTime.get());
            writeLC.close();

        } catch (Exception e) {
            e.printStackTrace();
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