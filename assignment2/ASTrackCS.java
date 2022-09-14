import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;

public class ASTrackCS extends Thread {
    private String contentServerName;
    private int timeLeft;

    public ASTrackCS(String contentServerName) {
        try {
            Writer output; 
            output = new BufferedWriter(new FileWriter("./server_state.txt", true));
            output.append(contentServerName);
            output.close();

            this.contentServerName = contentServerName;
            this.timeLeft = 12;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getContentServerName() {
        return this.contentServerName;
    }

    public void resetTimeLeft() {
        this.timeLeft = 12;
    }

    public void extendContentServerLife(String contentServerName) {
        if (this.contentServerName.equals(contentServerName)) {
            this.timeLeft = 12;
        }
    }

    private void removeCSFromServerState() {
        try {
            File inputFile = new File("server_state.txt");
            File tempFile = new File("myTempFile.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;

            while((currentLine = reader.readLine()) != null) {
                String trimmedLine = currentLine.trim();
                if(trimmedLine.equals(this.contentServerName)) continue;
                writer.write(currentLine + System.getProperty("line.separator"));
            }
            writer.flush();
            tempFile.renameTo(inputFile);

            writer.close(); 
            reader.close(); 
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        // keeping track of content server counters
        try {
            while (true) {
                Thread.sleep(1000);
                this.timeLeft -= 1;
                if (this.timeLeft <= 0) {
                    System.out.println(this.contentServerName.trim() + " dying");
                    File contentFile = new File("./saved/"+this.contentServerName +".txt");
                    contentFile.delete();
                    removeCSFromServerState();

                    return;
                }
            }
        }
        catch (Exception e) {
        }
    }
}
