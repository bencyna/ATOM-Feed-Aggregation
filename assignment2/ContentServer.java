import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;


import java.net.*;
public class ContentServer {
    public static void main(String[] args) {
        try {
            LamportClock CStime = new LamportClock();

            if (args.length < 2) {
                System.out.println("Error, format for connection and file to upload incorrect, please use this format: <ServerName>:<PortNumber> <filepath>");
                return;
            }
            Integer port = Integer.parseInt(args[0].split(":")[1]);

            Socket s = new Socket("localhost", port);
            DataOutputStream dout=new DataOutputStream(s.getOutputStream());  
            DataInputStream din = new DataInputStream(s.getInputStream());


            dout.writeUTF(put(args[1], CStime));  
            dout.flush();
            String serverResponse = "";
            serverResponse = din.readUTF();
            System.out.println(serverResponse);
            boolean userExits = false;

            while (!userExits) {
                // Enter data using BufferReader
                BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
                // Reading data using readLine
                String line = reader.readLine();

                System.out.println(line);

                if (line.contains("exit")) {
                    break;
                }
            }

            dout.close();  

            s.close();  
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    static String lamport() {
        return "0";
    }

    static String put(String filepath, LamportClock CStime) {
        String content = "1.type:put 1.name:content server 1 1.lc:" + String.valueOf(CStime.get()) + "1.<!endline!>;";
        try {
            FileInputStream fstream = new FileInputStream(filepath);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;

            //Read File Line By Line
            while ((strLine = br.readLine()) != null)   {
              // if current line doesn't have an identifyer, we remove the last endline then add this line
              content += strLine + "<!endline!>;";
            }
            fstream.close();
            return content;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return "Error failed to get content";
    }
}