import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
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

        Document doc = builder.parse("dataTest.xml");

        NodeList entryList = doc.getElementsByTagName("entry");   
        

        for (int i=0; i< entryList.getLength(); i++) {
            Node p = entryList.item(i);
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
    public static void StringToXML(String contentServerName) {
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
            atts.addAttribute("1", "2", "xml:lang", "", "en-US");
            atts.addAttribute("", "", "xmlns", "", "http://www.w3.org/2005/Atom");
            th.startElement("", "", "feed", atts);
            atts.clear();
            th.startElement("", "", "entry", atts);

        //
			String str;
            Boolean lastIsAuthor = false;
			while ((str = in.readLine()) != null) {
				String[] elements = str.split(":");
                // for (int k = 0; k < elements.length; k++) {
                //     System.out.println(elements[k]);
                //     System.out.println(k);
                // }
                // System.out.println("----");
                if (str.contains("entry") && str.trim().length() < 7) {
                    th.endElement("", "", "entry");
                    th.startElement("", "", "entry", atts);
                }
                
                if (elements.length <= 1) {
                    continue;
                } 
                if (elements[0].trim().equals("author")) {
                    th.startElement("", "", elements[0], atts);
                    th.startElement("", "", "name", atts);
                    th.characters(elements[1].toCharArray(), 0, elements[1].length());
                    th.endElement("", "", "name");
                    lastIsAuthor = true;
                }
                
                else if (elements[0].trim().equals("email") && lastIsAuthor) {
                    th.startElement("", "", "email", atts);
                    th.characters(elements[1].toCharArray(), 0, elements[1].length());
                    th.endElement("", "", "email");
                    th.endElement("", "", "author");
                    lastIsAuthor = false;
                }
                else if (lastIsAuthor) {
                    th.endElement("", "", "author");
                    lastIsAuthor = false;
                }
                else {
                    atts.clear();
                    th.startElement("", "", elements[0], atts);
                    th.characters(elements[1].toCharArray(), 0, elements[1].length());
                    th.endElement("", "", elements[0]);
                }
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
            th.endElement("", "", "entry");
            th.endElement("", "", "feed");
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