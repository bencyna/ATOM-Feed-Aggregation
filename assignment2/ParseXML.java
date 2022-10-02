import javax.swing.text.AbstractDocument.ElementEdit;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;



public class ParseXML {


    public static String XMLtoString(String line) {
        try {
        // String[] splitLine = line.split(":", 2);
        // String newLine = "<" + splitLine[0] + ">" + splitLine[1] + "</" + splitLine[0] + ">";

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = factory.newDocumentBuilder();

        Document doc = builder.parse("test.xml");

        NodeList personList = doc.getElementsByTagName("author");    

        for (int i=0; i< personList.getLength(); i++) {
            Node p = personList.item(i);
            if (p.getNodeType() ==Node.ELEMENT_NODE) {
                Element person = (Element) p;
                NodeList nameList = person.getChildNodes();
                for (int j=0; i< nameList.getLength(); j++) {
                    Node n = nameList.item(j);
                    if (n.getNodeType() == Node.ELEMENT_NODE) {
                        Element name = (Element) n;

                        System.out.println("name: " + name.getTagName() + "=" + name.getTextContent());
                    }
                }
                
            }
        }

        return "je";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    } 
    public void StringToXML(String[] parts, String filePath) throws ParserConfigurationException, FileNotFoundException {
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

        File xmlFile = new File("products.xml");

        FileOutputStream outStream = new FileOutputStream(xmlFile);




        return;
    } 

    // to test xml we have a main
    public static void main(String[] args) {
        try {
            XMLtoString("hello");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}