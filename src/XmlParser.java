import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class XmlParser {
    UserHandler userhandler;


    public XmlParser(){
      File inputFile = new File("/Users/pooja.sharma/blt/app/main/core/sfdc-test/flatfile.xml");
       // File inputFile = new File("/Users/pooja.sharma/Downloads/schemaComparisonTest1.xml");

        try {
        SAXParserFactory factory = SAXParserFactory.newInstance();

        SAXParser saxParser = factory.newSAXParser();
        userhandler = new UserHandler();

            saxParser.parse(inputFile, userhandler);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

	public String findFileName(String testName) throws ParserConfigurationException, SAXException, IOException {
        return userhandler.readData(testName);
	}

}
