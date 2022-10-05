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
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;



public class ParseXML {


    public String XMLtoString(String xml) {
        try {
        // String[] splitLine = line.split(":", 2);
        // String newLine = "<" + splitLine[0] + ">" + splitLine[1] + "</" + splitLine[0] + ">";
        String XMLheader = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?><feed xml:lang=\"en-US\" xmlns=\"http://www.w3.org/2005/Atom\">";    
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = factory.newDocumentBuilder();
        PrintWriter writer = new PrintWriter("tempXML.xml", "ISO-8859-1");
        String removeHeaderFromXML = xml.replace(XMLheader, ""); 
        String removeFeedFromXML = removeHeaderFromXML.replace("</feed>", ""); 
        String updatedXML = XMLheader + removeFeedFromXML + "</feed>";
        writer.println(updatedXML);
        writer.close();
        
            
        Document doc = builder.parse("tempXML.xml");

        NodeList entryList = doc.getElementsByTagName("entry");   
        
        String content = "";
        int EntryListLength = entryList.getLength();
        for (int i=0; i < EntryListLength; i++) {
            Node entry = entryList.item(i);
            if (entry.getNodeType() ==Node.ELEMENT_NODE) {
                Element feed = (Element) entry;
                NodeList itemEntry = feed.getChildNodes();
                for (int j=0; j < itemEntry.getLength(); j++) {
                    Node line = itemEntry.item(j);
                    if (line.getNodeType() == Node.ELEMENT_NODE) {
                        Element name = (Element) line;

                        content += (name.getTagName().trim() + ": " + name.getTextContent()).trim() + "\n";
                    }
                }
            if (i < EntryListLength - 1) {
                content += "entry\n";
            }
            }
        } 
        File tempfile = new File("tempXML.xml"); 
        tempfile.delete();
        return content;
        }
        catch (Exception e) {
            e.printStackTrace();
            return "XML to String failed";
        }
    } 
    public String StringToXML(String filepath) {
        try {
            BufferedReader in;
            StreamResult out;
            TransformerHandler th;
            AttributesImpl atts;

            in = new BufferedReader(new FileReader(filepath));

            String tempFileName = "temp-" + filepath.split("/")[1].split(".txt")[0] +".xml";
			out = new StreamResult(tempFileName);
            
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
                    th.endElement("", "", elements[0] +"\n");
                }
			}
			in.close();
			closeXML(th);

            FileInputStream fstream = new FileInputStream(tempFileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            String result = "";

            //Read File Line By Line
            while ((strLine = br.readLine()) != null)   {
              // if current line doesn't have an identifyer, we remove the last endline then add this line
              result += strLine;
            }
            fstream.close();
            File tempfile = new File(tempFileName); 
            tempfile.delete();

            return result;
        }
        catch (Exception e) {
            e.printStackTrace();
            return "String to XML failed";
        }
    } 

     public void closeXML(TransformerHandler th) {
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
            // System.out.println(StringToXML("input/file1.txt"));
            // XMLtoString("hello");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}