import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.HashMap;

public class UserHandler extends DefaultHandler {

    String categoryTeamName, classTeamName, testcaseTeamName;
    String label;
    String testname;
    String testClass;

    HashMap<String, String> indevTestsMap = new HashMap<>();
    private ArrayList<String> testslist = new ArrayList<String>();;
    ArrayList<String> list;
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException {
//    	try {

        if (qName.equalsIgnoreCase("category") ) {
            //create a new Employee and put it in Map
            String val = attributes.getValue("scrumteam");
            if(val!= null) {
                categoryTeamName = val;
            }


        } else if (qName.equalsIgnoreCase("test-class")) {

            classTeamName = attributes.getValue("scrumteam");
        }
        else if (qName.equalsIgnoreCase("test-case")) {
            label = attributes.getValue("labels");
            if(true) {
                testname =  attributes.getValue("name");
                testClass =  attributes.getValue("class");
                testcaseTeamName = attributes.getValue("scrumteam");

                String newTeam = null;
                if(testcaseTeamName != null) {
                    newTeam = testcaseTeamName;
                } else if (classTeamName != null) {
                    newTeam = classTeamName;
                } else if(categoryTeamName != null) {
                    newTeam = categoryTeamName;
                }


                if(true) {
                    //testslist = null;

                    int index = testClass.lastIndexOf(".");
                    testClass = testClass.substring(index + 1);
                    String fileName = testClass;
                    index = fileName.indexOf('$');
                    if(index>0) {
                        fileName = fileName.substring(0, index);
                    }


                    if(indevTestsMap.containsKey(fileName)) {
                        //System.out.print("Duplicate:"+fileName);
                        //testslist = indevTestsMap.get(newTeam);

                    } else {


                    }


//                    if(indevTestsMap.containsKey(newTeam)) {
//                        System.out.print("Duplicate:"+testClass);
//                        testslist = indevTestsMap.get(newTeam);
//
//                    } else {
//                        testslist = new ArrayList<String>();
//
//                    }
                    testslist.add(fileName);
                    if(fileName==null) {
                        String new1 = "gegegge";
                        System.out.println(new1);
                    }
                    if(newTeam==null) {
                        newTeam = fileName;
                        //String new1 = "gegegge";
                        //System.out.println(fileName);
                    }
                    if(newTeam!=null){
                        indevTestsMap.put(fileName, newTeam);
                    } else if(fileName!=null){
                        indevTestsMap.put(fileName, fileName);
                    }

//	         	 	if((newTeam != null && !testslist.isEmpty())) {
//	         	 		 list = indevTestsMap.get(newTeam);
//	         	 		if(list != null) {
//	         	 			testslist.addAll(list);
//	         	 			list.clear();
//	         	 		}


                }
                //}
            }

        }

    }

    public String readData(String testClass) {

        String teamTests = indevTestsMap.get(testClass);
        //if(teamTests.contains(testClass))
        //System.out.println(teamTests.size());

       return teamTests;

//  	for (String name: indevTestsMap.keySet()){
//
//            String key = name;
//            String value = indevTestsMap.get(name).toString();
//            System.out.println(key + " " + value);
//  	}


    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("category")) {
            //add Employee object to list
            //categoryTeamName = null;
        } else if(qName.equalsIgnoreCase("test-class")) {
            classTeamName = null;
        }else if(qName.equalsIgnoreCase("test-case")) {
            testcaseTeamName = null;
        }
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {

//        if (bAge) {
//            //age element, set Employee age
//            emp.setAge(Integer.parseInt(new String(ch, start, length)));
//            bAge = false;
//        } else if (bName) {
//            emp.setName(new String(ch, start, length));
//            bName = false;
//        } else if (bRole) {
//            emp.setRole(new String(ch, start, length));
//            bRole = false;
//        } else if (bGender) {
//            emp.setGender(new String(ch, start, length));
//            bGender = false;
//        }
    }
}



 /*  boolean bFirstName = false;
   boolean bLastName = false;
   boolean bNickName = false;
   boolean bMarks = false;
Map<String, ArrayList<String>> inDevTests = new HashMap<String, ArrayList<String>>();

   @SuppressWarnings({ "null", "unused" })
@Override
   public void startElement(String uri,
   String localName, String qName, Attributes attributes) throws SAXException {
	   String label, testname, testClass, devOwner, team = null;
	   if(qName.equalsIgnoreCase("category")) {
		   team = attributes.getValue("scrumteam");
		   testname =  attributes.getValue("name");

		  // devOwner = attributes.getValue("")

	   }else if (qName.equalsIgnoreCase("test-case")) {
		   label = attributes.getValue("labels");
		   	if(label.contains("indev")) {
        	 		testname =  attributes.getValue("name");
        	 		testClass =  attributes.getValue("class");
        	 		if(testname.isEmpty()) {
        	 			System.out.println(testname);
        	 		}else {
        	 			ArrayList<String> testslist;
        	 			if(inDevTests.containsKey(team)) {

        	 				testslist = inDevTests.get(team);
        	 				testslist.add(testname);
                	 		testslist.add(testClass);

        	 			} else {
        	 				testslist = new ArrayList<String>();
        	 				testslist.add(testname);
                	 		testslist.add(testClass);
        	 			}

        	 	if((team != null)) {
        	 		inDevTests.put(team, testslist);
        	 	}
        	 		}
         	}

      }
   }*/



  /* public void readData(String teamname) {

	  ArrayList<String> teamTests = inDevTests.get(teamname);
	  System.out.println(inDevTests.size());

	for (String name: inDevTests.keySet()){

          String key = name;
          String value = inDevTests.get(name).toString();
          System.out.println(key + " " + value);


}

	  Iterator<String> crunchifyIterator = teamTests.iterator();
		while (crunchifyIterator.hasNext()) {
			System.out.println(crunchifyIterator.next());
		}
   }

   @Override
   public void characters(char ch[], int start, int length) throws SAXException {

      if (bFirstName) {
         System.out.println("First Name: "
            + new String(ch, start, length));
         bFirstName = false;
      } else if (bLastName) {
         System.out.println("Last Name: " + new String(ch, start, length));
         bLastName = false;
      } else if (bNickName) {
         System.out.println("Nick Name: " + new String(ch, start, length));
         bNickName = false;
      } else if (bMarks) {
         System.out.println("Marks: " + new String(ch, start, length));
         bMarks = false;
      }
   }
}*/

