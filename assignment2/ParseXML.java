import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;



public class ParseXML {


    public String XMLtoString(String line) {
        String[] splitLine = line.split(":", 2);
        String newLine = "<" + splitLine[0] + ">" + splitLine[1] + "</" + splitLine[0] + ">";
        return "je";
    } 
    public void StringToXML(String[] parts, String filePath) throws ParserConfigurationException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();

        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        Document xmlDoc = docBuilder.newDocument();

        Element rootElement = xmlDoc.createElement("feed");

        Element mainElement = xmlDoc.createElement("inside1");

        Text productNameText = xmlDoc.createTextNode("inside?");
        
        Element productName = xmlDoc.createElement("inside1");

        Element name = xmlDoc.createElement("name");

        productName.appendChild(productNameText);
        mainElement.appendChild(productName);
        rootElement.appendChild(rootElement);
        xmlDoc.appendChild(rootElement);

        OutputFormat outFormat = new OutputFormat(xmlDoc);
        outFormat.setIndenting(true);


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