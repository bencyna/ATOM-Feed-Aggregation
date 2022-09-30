public class ParseXML {


    public String XMLtoString(String line) {
        String[] splitLine = line.split(":", 2);
        String newLine = "<" + splitLine[0] + ">" + splitLine[1] + "</" + splitLine[0] + ">";
        return "je";
    } 
    public void StringToXML(String[] parts, String filePath) {
        
        return;
    } 

    // to test xml we have a main
    public static void main(String[] args) {
        try {

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}