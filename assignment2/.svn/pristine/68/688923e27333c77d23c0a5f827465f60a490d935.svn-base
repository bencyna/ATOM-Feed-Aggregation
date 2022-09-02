import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import java.net.*;
public class ContentServer {
    public static void main(String[] args) {
        try {
            Socket s = new Socket("localhost", 4567 );
            DataOutputStream dout=new DataOutputStream(s.getOutputStream());  

            dout.writeUTF(put("./input/file1.txt"));  
            dout.flush();  

            dout.close();  
            s.close();  
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    static String lamport() {
        return "0";
    }

    static String put(String filepath) {
        String content = "Location: content server 1 <!endline!>;";
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