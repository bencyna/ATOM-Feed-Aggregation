import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;


import java.net.*;
public class ContentServer {
    private String name;

    public static void main(String[] args) {
      try {
        ContentServer obj = new ContentServer();
        obj.run(args);
      }
      catch (Exception e){
        e.printStackTrace();
      }
    }
    static String lamport() {
        return "0";
    }

    public void run (String[] args) throws Exception {
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


            dout.writeUTF(put(args[1], CStime, true));  
            dout.flush();
            String serverResponse = "";
            serverResponse = din.readUTF();
            System.out.println(serverResponse);
            boolean userExits = false;
            dout.close();  
            s.close();

            while (!userExits) {
                // Enter data using BufferReader
                BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
                // Reading data using readLine
                String line = reader.readLine();

                File f = new File(line);
                System.out.println(line);


                if (line.contains("exit")) {
                    return;
                }

               

                // if line leads to an input file
                if (f.exists() && !f.isDirectory()) {  
                    Socket s2 = new Socket("localhost", port);
                    DataOutputStream dout2=new DataOutputStream(s2.getOutputStream());  
                    dout2.writeUTF(put(line, CStime, false));
                    dout2.flush();
                    dout2.close();  
                    s2.close();  
                }
                else if (line.contains("ping")) {
                    Socket s2 = new Socket("localhost", port);
                    DataOutputStream dout2=new DataOutputStream(s2.getOutputStream());  
                    dout2.writeUTF("1.type:ping 1.name:"+ this.name +" 1.lc:" + String.valueOf(CStime.get()) + "1.<!endline!>;");  
                    dout2.flush();
                    dout2.close();  
                    s2.close();
                }
                else {
                    System.out.println("Invalid input, either write \"ping\" to keep content server alive or provide the path for a new input text file");
                }
            }
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }

     String put(String filepath, LamportClock CStime, boolean first) {
        try {
            String content;
;            if (first) {
                FileInputStream contentNum = new FileInputStream("contentServerNum.txt");
                BufferedReader contentServerNumber = new BufferedReader(new InputStreamReader(contentNum));
                String num = contentServerNumber.readLine();
                content = "1.type:put 1.name:content server "+ num +" 1.lc:" + String.valueOf(CStime.get()) + "1.<!endline!>;";

                this.name = "content server "+ num + " ";

                int contentNumber = Integer.parseInt(num);
                contentServerNumber.close();

                String incrementNumber = String.valueOf(contentNumber+1);

                PrintWriter writer = new PrintWriter("contentServerNum.txt", "UTF-8");
                writer.print(incrementNumber);
                writer.close();
            }
            else {
                content = "1.type:put and ping 1.name:"+ this.name +"1.lc:" + String.valueOf(CStime.get()) + "1.<!endline!>;";
            }

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