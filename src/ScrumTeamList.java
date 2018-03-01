import java.util.ArrayList;

public class ScrumTeamList {


    private String testName;
    private String testClass;
    private ArrayList<String> teamList;
    private String teamName;
   
   
    
    @Override
    public String toString() {
        return " Name=" + this.testName + " testClass=" + this.testClass + " teamList=" + this.teamList +
                " teamName=" + this.teamName;
    }
	public String getTestName() {
		return testName;
	}
	public void setTestName(String testName) {
		this.testName = testName;
	}
	public String getTestClass() {
		return testClass;
	}
	public void setTestClass(String testClass) {
		this.testClass = testClass;
	}
	public ArrayList<String> getTeamList() {
		return teamList;
	}
	public void setTeamList(ArrayList<String> teamList) {
		this.teamList = teamList;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
    
}
