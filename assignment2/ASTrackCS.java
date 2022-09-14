import java.io.File;

public class ASTrackCS extends Thread {
    private String contentServerName;
    private int timeLeft;

    public String getContentServerName() {
        return this.contentServerName;
    }

    public void resetTimeLeft() {
        this.timeLeft = 12;
    }

    public ASTrackCS(String contentServerName) {
        this.contentServerName = contentServerName;
        this.timeLeft = 12;
    }

    public void extendContentServerLife(String contentServerName) {
        if (this.contentServerName.equals(contentServerName)) {
            this.timeLeft = 12;
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
                    return;
                }
            }
        }
        catch (Exception e) {
        }
    }
}
