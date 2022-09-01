import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
        String content = "";
        try {
            FileInputStream fstream = new FileInputStream(filepath);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;

            //Read File Line By Line
            while ((strLine = br.readLine()) != null)   {
              // Print the content on the console
              content += strLine;
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