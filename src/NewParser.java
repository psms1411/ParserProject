import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by pooja.sharma on 18/01/18.
 */
public class NewParser {
    Document doc;
    //String scrumTeam;
    String scrumTeamAttr = "scrumteam";
    String nameAttr = "name";
    String classAttr = "class";
    String fileAttr = "file";
    HashMap<String, String> indevTestsMap = new HashMap<>();
    FileOutputStream fos = null;
    boolean writeDuplicateIntoFile = false;  //If you want to check duplicate name files then set it to true

    public NewParser() {
        File inputFile = new File("/Users/pooja.sharma/blt/app/main/core/sfdc-test/flatfile.xml");

        try {
            fos = new FileOutputStream(new File("/Users/pooja.sharma/project/untitled/DuplicateClass.doc"), true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //File inputFile = new File("/Users/pooja.sharma/Downloads/schemaComparisonTest1.xml"); Test file
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        doc = null;
        try {
            doc = db.parse(inputFile);
            readAllNode();
            fos.close();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    void readAllNode() {
        Element nodes = doc.getDocumentElement();
//        for (int i = 0; i < nodes.getLength(); i++) {
//            Node node = nodes.item(i);
//            if (node.getNodeName() =="category") {
//                scrumTeam = node.getNodeName();
//                Element childElement = (Element) node;
        if(nodes.getTagName()=="ftests") {
            NodeList nodeList = nodes.getChildNodes();
            for(int i=0; i<nodeList.getLength(); i++) {
                Node childElement = (Node) (nodeList.item(i));
                if(childElement.getNodeName()=="category") {
                    processMainCategory(childElement);
                }
                else if(childElement.getNodeName()=="#text") {
                }
                else{
                    System.out.println("Only parsing category: "+childElement.getNodeName());
                }

            }

        }

//            }
//        }
    }

    private void processMainCategory(Node mainCatElement) {

        if(!mainCatElement.hasChildNodes()) {
            System.out.println("No test found");
            return;
        }

        NodeList nodeList = mainCatElement.getChildNodes();
        for(int i=0; i<nodeList.getLength(); i++) {
            Node childElement = (Node) (nodeList.item(i));
            if(childElement.getNodeName()=="category") {
                processAllChildNodes(childElement, null);
            }
             else if(childElement.getNodeName()=="#text") {
            }
            else{
                System.out.println("Only parsing category: "+childElement.getNodeName());
            }

        }

    }


    private void processAllChildNodes(Node catElem, String parentScrumTeamName) {

        String tempScrumName = returnAttribute(catElem, scrumTeamAttr);
        if(tempScrumName!=null) {
            parentScrumTeamName = tempScrumName;
        }

        if(!catElem.hasChildNodes()) {
            System.out.println("No test found in category "+returnAttribute(catElem, nameAttr));
        }

        NodeList nodeList = catElem.getChildNodes();
        for(int i=0; i<nodeList.getLength(); i++) {
            Node childElement = (Node) (nodeList.item(i));
            if(childElement.getNodeName()=="category") {
                processAllChildNodes(childElement, parentScrumTeamName);
            } else if(childElement.getNodeName()=="test-class"){
                takeCareOfTeamName(childElement, parentScrumTeamName);
            }
            //**Do we really needthis?
//            else if(childElement.getNodeName()=="test-case"){
//                takeCareOfTeamName(childElement, tempScrumName);
//            }

        }

    }


    public void proceesTestClass(Node catElem) {
        //String tempScrumName = returnAttribute(catElem, scrumTeamAttr);

        if(!catElem.hasChildNodes()) {
            System.out.println("No test found in test class"+returnAttribute(catElem, nameAttr));
        }

//        takeCareOfTeamName(catElem);
//     ** DO we really need to go through each test case when we have test class
//        NodeList nodeList = catElem.getChildNodes();
//        for(int i=0; i<nodeList.getLength(); i++) {
//            Node childElement = (Node) (nodeList.item(i));
//
//            if(childElement.getNodeName()=="test-case") {
//                takeCareOfTeamName(childElement, tempScrumName);
//            } else if(childElement.getNodeName()=="#text") {
//            }else{
//                System.out.println("other cat found in the test class: "+returnAttribute(childElement, nameAttr));
//            }
//
//        }

    }

    public void takeCareOfTeamName(Node childElement ,String parentScrumTeamName) {
        {
            String testClass =  returnAttribute(childElement, classAttr);
            if(testClass == null){
                //Check file attribute - those are dynamic testcases
                String dynamicTest = returnAttribute(childElement, fileAttr);
                if(dynamicTest!=null) {
                    //No need to pay attention -
                } else {
                    System.out.println("Test class doesnt have test attribute "+returnAttribute(childElement, nameAttr));
                }

                return;
            }
            String testScrumName = returnAttribute(childElement, scrumTeamAttr);

            String finalTeam;
            if(testScrumName!=null){
                finalTeam = testScrumName;
            } else {
                finalTeam = parentScrumTeamName;
            }

            if(finalTeam == null) {
                System.out.println("no Team found for "+testClass);
            }
            int index = testClass.lastIndexOf(".");
            testClass = testClass.substring(index + 1);
            String fileName = testClass;
            index = fileName.indexOf('$');
            if(index>0) {
                fileName = fileName.substring(0, index);
            }

            if(indevTestsMap.containsKey(fileName)) {
                //System.out.println("Duplicate found: Writing into file - check the path from FileOutputStream varaible here "+fileName);

                if(writeDuplicateIntoFile) {
                    try {
                        fos.write(fileName.getBytes());
                        fos.write("\n".getBytes());
                        fos.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            } else {
                indevTestsMap.put(fileName, finalTeam);
            }


        }
    }

    public String returnAttribute(Node element, String name) {
        Node n = element.getAttributes().getNamedItem(name);
        if(n!=null){
            return n.getNodeValue();
        } else {
            return null;
        }
    }

    public String returnTeamOwnerPerClass(String className) {
        return indevTestsMap.get(className);
    }

//    public static void main(String[] args) {
//        NewParser np = new NewParser();
//        np.readAllNode();
//        HashMap<String, String> indevTestsMap = np.indevTestsMap;
//
//    }
}
