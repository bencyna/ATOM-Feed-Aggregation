import javax.swing.text.AbstractDocument.ElementEdit;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.helpers.AttributesImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
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
    public static void StringToXML(String filePath) {
        try {
            BufferedReader in;
            StreamResult out;
            TransformerHandler th;
            AttributesImpl atts;

            in = new BufferedReader(new FileReader("input/file5.txt"));
			out = new StreamResult("dataTest.xml");
            
            //
			SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory
				.newInstance();

            th = tf.newTransformerHandler();
            Transformer serializer = th.getTransformer();
            serializer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
            serializer.setOutputProperty(
                    "{http://xml.apache.org/xslt}indent-amount", "4");
            serializer.setOutputProperty(OutputKeys.INDENT, "yes");
            th.setResult(out);
            th.startDocument();
            atts = new AttributesImpl();
            th.startElement("", "", "Employee", atts);

        //
			String str;
			while ((str = in.readLine()) != null) {
				String[] elements = str.split(" ");
                atts.clear();
                th.startElement("", "", "Data", atts);
                th.startElement("", "", "Employee_Name", atts);
                th.characters(elements[0].toCharArray(), 0, elements[0].length());
                th.endElement("", "", "Employee_Name");
                th.endElement("", "", "Data");
			}
			in.close();
			closeXML(th);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    } 

    public static void closeXML(TransformerHandler th) {
        try {
            th.endElement("", "", "Employee");
            th.endDocument();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
	}

    // to test xml we have a main
    public static void main(String[] args) {
        try {
            StringToXML("hello");
            // XMLtoString("hello");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}