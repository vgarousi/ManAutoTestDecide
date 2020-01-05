package algorithms;
import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;

public class UseCaseProfile {

	UseCaseProfile(){
		try {
			Utility.fillReusableHashParts();
			Utility.readFromExcel();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//-------------Design-------------------------------------------------
	/*
	 *USECASE_PROFILE[usecaseID][1]: #TC
	 */
	public static double getManualDesignInfo(int usecaseID){
		double designFactor = USECASE_PROFILE[usecaseID][1];
		double manualDesignCost = designFactor * (MANUAL_DESIGN_Per_Step);
		manualDesignCost = manualDesignCost * NUMBEROFEXECUTION;
		return manualDesignCost;
	}
	
	public static double getAutomatedDesignInfo(int usecaseID){
		double designFactor = USECASE_PROFILE[usecaseID][1];
		double automatedDesignCost = designFactor * (AUTOMATED_DESIGN_Per_Step);
		//COMAINTENANCE_RATE = 0; in fact co-maintenance have not been considered. TODO: use different values for script and design maintenance rate.
		automatedDesignCost = automatedDesignCost + automatedDesignCost * COMAINTENANCE_RATE * (NUMBEROFEXECUTION-1);
		return automatedDesignCost;
	}

	public static double[] getTestDesignInfo(int usecaseID){
		double[] designInfo = new double[2];
		double manualDesignCost = getManualDesignInfo(usecaseID);
	    double automatedDesignCost = getAutomatedDesignInfo(usecaseID);
		designInfo[0]= manualDesignCost;
		designInfo[1]= automatedDesignCost;
		return designInfo;
	}
	
	//--------------- Scripting & Verification -----------------
	/*
	 *USECASE_PROFILE[usecaseID][2]: Complexity factor
	 */
		
	public static double getManualScriptingCost(int usecaseID){
		double manualScriptingTime = USECASE_PROFILE[usecaseID][0] * (MANUAL_SCRIPT_Per_Step);
		return manualScriptingTime;
	}
	
	/*
	 * USECASE_PROFILE[usecaseID][7]: # non-reusable steps of test scenario
	 * USECASE_PROFILE[usecaseID][8]: # non-reusable verifing step of test scenarios
	 */
	public static double getAutomatedScriptingCost(int usecaseID){
		double reusableScriptingCostFactor = getReusableTestScriptingCost(usecaseID);
		double scriptingCostFactor = USECASE_PROFILE[usecaseID][7] + USECASE_PROFILE[usecaseID][8];
		String tmp = ((int)USECASE_PROFILE[usecaseID][2])+"";
		double complexityFactor = complexityFactor(tmp);
		double scriptingCost = reusableScriptingCostFactor + scriptingCostFactor*complexityFactor;
		return scriptingCost;
	}
	/*USECASE_PROFILE[usecaseID][6]: manual co-maintenance, updates in manual test process*/
	public static double[] getTestScriptingInfo(int usecaseID){
		double manualScriptingCost = getManualScriptingCost(usecaseID);
		double automatedScriptingCost = getAutomatedScriptingCost(usecaseID);
		manualScriptingCost = manualScriptingCost + (USECASE_PROFILE[usecaseID][6]*(NUMBEROFEXECUTION-1));
		
		double coMaintenanceCost = automatedScriptingCost * COMAINTENANCE_RATE;
		double qualityImprovementCost = automatedScriptingCost * QUALITYIMPROVEMENT_RATE;
		automatedScriptingCost = automatedScriptingCost + (coMaintenanceCost)*(NUMBEROFEXECUTION-1) + qualityImprovementCost;
		
		double[] scriptInfo = new double[2];
		scriptInfo[0]= manualScriptingCost;
		scriptInfo[1]= automatedScriptingCost;
		return scriptInfo;
	}
	/*
	 * Shared part of the use cases are extracted and listed in the 2nd sheet of the input file,
	 *  this method calculates the cost of shared parts.
	 */
	public static double getReusableTestScriptingCost(int usecaseID){
		double scriptingCost = 0;
		StringTokenizer st = new StringTokenizer(reusableParts[usecaseID][1]);
		while (st.hasMoreTokens()){
			String x = st.nextToken();
			scriptingCost +=  (((Double.parseDouble(reusablePartsCostFactor.get(x).toString()))/((Integer)graphCounter.get(x)))*complexityFactor(reusablePartsCostComplexity.get(x).toString()));
		}
		st = new StringTokenizer(reusableParts[usecaseID][1]);
		while (st.hasMoreTokens()){
			String x = st.nextToken();
			scriptingCost += ((Double.parseDouble(reusablePartsCostFactor.get(x).toString())/((Integer)graphCounter.get(x)))*complexityFactor(reusablePartsCostComplexity.get(x).toString()));
		}
		return scriptingCost;
	}
	/*
	 *What is complexity level? For estimating average time for automated scripting, 
	 *automation experts of Pason divide use cases to 3 level of complexity and have 
	 *average time for each level. With the help of the automation expert of Pason, 
	 *we divided use cases of the SUT to 3 level and used 3 different average times 
	 * for each group.
	 */
	private static double complexityFactor(String complexity){
		int complexityLevel = Integer.parseInt(complexity);
		switch (complexityLevel) {
		case 1:
			return  (SIMPLE_AUTOMATED_SCRIPT_Per_Step);
		case 2:
			return  (AVERAGE_AUTOMATED_SCRIPT_Per_Step);
		case 3:
			return  (DIFFICULT_AUTOMATED_SCRIPT_Per_Step);
		default:
			return 0;
		}
	}
	//-----------------Execution----------------------------------
	/*
	 * USECASE_PROFILE[usecaseID][3]: Execution time
	 * USECASE_PROFILE[usecaseID][4]: Execution setup time
	 * USECASE_PROFILE[usecaseID][1]: # TestCases
	 */
	public static double getManualExecutionCost(int usecaseID){
		double manualExecutionTime = USECASE_PROFILE[usecaseID][3] * NUMBER_OF_CONFIGURATION * USECASE_PROFILE[usecaseID][1]/60; 
		manualExecutionTime += USECASE_PROFILE[usecaseID][4]/60;
		return manualExecutionTime;
	}
	
	public static double getAutomatedExecutionCost(int usecaseID){
		//only set up time is considered as fixed time for execution
		return 0;
	}
	
	public static double[] getTestExecuteInfo(int usecaseID){
		double manualExecute = (getManualExecutionCost(usecaseID))*NUMBEROFEXECUTION;
		double automatedExecute = getAutomatedExecutionCost(usecaseID);
		double[] executeInfo = new double[2];
		executeInfo[0]= manualExecute;
		executeInfo[1]= automatedExecute;
		return executeInfo;
	}
	
	//-----------------Reporting----------------------------------
	/*
	 * USECASE_PROFILE[usecaseID][5]: Number of potential defects of the UC
	 * */
	public static double getManualReportingCost(int usecaseID){
		double manualReortingTime = USECASE_PROFILE[usecaseID][5]*manualReporting*NUMBEROFEXECUTION;
		return manualReortingTime;
	}
	
	public static double getAutomatedReportingCost(int usecaseID){
		double automatedReortingTime =  USECASE_PROFILE[usecaseID][5]*automatedReporting*NUMBEROFEXECUTION;  
		return automatedReortingTime;
	}
	
	public static double[] getTestReportingInfo(int usecaseID){
		double[] reportInfo = new double[2];
		double manualReportingCost = getManualReportingCost(usecaseID);
		double automatedReportingCost = getAutomatedReportingCost(usecaseID);
		reportInfo[0] = manualReportingCost;
		reportInfo[1] = automatedReportingCost;
		return reportInfo;
	}
	//------------------------------------------------------------------------
	//All Parameters ---------------------------------------------------------
	static int SELECTED_ALG = 0;
	public static int GENETIC = 1;
	public static int BRUTE_FORCE = 2;
	//--------------------------------
	static String fileName ;
	static int NUMBEROFEXECUTION ; //1
	public static int USECASESIZE; //30
	public final static int ACTIVITYSIZE = 3;
	/*
	 * 1- Evalution is not considered. 
	 * 2- Decision for Script & Execution decisions are interdependant.
	 */
	//Design--------------------------
	static double MANUAL_DESIGN_Per_Step ; //1.48
	static double AUTOMATED_DESIGN_Per_Step; //0.16
	static double automatedDesignFixedCost;//3* 7 * 8 * 60
	//Scripting------------------------
	static double MANUAL_SCRIPT_Per_Step; 	//15
	static double SIMPLE_AUTOMATED_SCRIPT_Per_Step; //60/2
	static double AVERAGE_AUTOMATED_SCRIPT_Per_Step; //137.14/2
	static double DIFFICULT_AUTOMATED_SCRIPT_Per_Step; //327.27/2
	static double automatedScriptingFixedCost; //20 * 8 * 60
	//Execution---------------------
	//static double MANUAL_Execution_Factor = 2.5;
	static double NUMBER_OF_CONFIGURATION; // 3
	static double automatedExecutionFixedCost; //1.5*60
	//Reporting---------------------
	static double automatedReportingFixedTime; //3*5*8*60
	static double manualReporting; //60
	static double automatedReporting; //30
	//Maintenance--------------------
	static double COMAINTENANCE_RATE; //0
	static double QUALITYIMPROVEMENT_RATE; //0.09
   //-----------------------------------
	static HashMap  reusablePartsCostFactor = new HashMap();
	static HashMap  reusablePartsCostFactorBACKUP = new HashMap();
	static HashMap  reusablePartsCostComplexity = new HashMap();
	static HashMap graphCounter =new HashMap();
	static String [][] reusableParts = new String [USECASESIZE][2];
	static int separationPoint = 9;
	static double [][] USECASE_PROFILE = new double [USECASESIZE][separationPoint];
	
	/*Following methods are all setter methods*/

	//General-------------------------------------------
	public static void setExcelFilePath(String excelFilePath){
		UseCaseProfile.fileName = excelFilePath;
	}	
	public static void setUseCaseNumber(int UCNumber){
		UseCaseProfile.USECASESIZE = UCNumber;
		initiateUserProfile();
	}
	public static void initiateUserProfile(){
		reusableParts = new String [USECASESIZE][ACTIVITYSIZE];
		USECASE_PROFILE = new double [USECASESIZE][separationPoint];
	}
	public static void setNumberOfExecution(int numberOfExecution){
		UseCaseProfile.NUMBEROFEXECUTION = numberOfExecution;
	}
	//Design--------------------------------------------
	public static void setManual_Design_Per_Step(double manualDesignProductivity){
		UseCaseProfile.MANUAL_DESIGN_Per_Step = (1.0/manualDesignProductivity);
	}
	public static void setAutomated_DESIGN_Per_Step(double automatedDesignProductivity){
		UseCaseProfile.AUTOMATED_DESIGN_Per_Step = (1.0/automatedDesignProductivity);
	}
	public static void setAutomatedDesignFixedCost(double designFixedCost){
		UseCaseProfile.automatedDesignFixedCost = designFixedCost;
	}
	//Scripting-----------------------------------------
	public static void setManualScripting_Per_Step(double manualScriptingProductivity){
		UseCaseProfile.MANUAL_SCRIPT_Per_Step = (1.0/manualScriptingProductivity);
	}
	public static void setSimpleAutomatedScripting_Per_Step(double simpleAutomatedProductivity){
		UseCaseProfile.SIMPLE_AUTOMATED_SCRIPT_Per_Step = (1.0/simpleAutomatedProductivity)/2;
	}
	public static void setAverageAutomatedScripting_Per_Step(double averageAutomatedProductivity){
		UseCaseProfile.AVERAGE_AUTOMATED_SCRIPT_Per_Step = (1.0/averageAutomatedProductivity)/2;
	}
	public static void setDifficultAutomatedScripting_Per_Step(double difficultAutomatedProductivity){
		UseCaseProfile.DIFFICULT_AUTOMATED_SCRIPT_Per_Step = (1.0/difficultAutomatedProductivity)/2;
	}
	public static void setautomatedScriptingFixedCost(double scriptingFixedCost){
		UseCaseProfile.automatedScriptingFixedCost = scriptingFixedCost;
	}
	//Execution------------------------------------------
	public static void setNumberOfConfigurations(double numberOfConfiguration){
		UseCaseProfile.NUMBER_OF_CONFIGURATION = numberOfConfiguration;
	}
	public static void setautomatedExecutionFixedCost(double executionFixedCost){
		UseCaseProfile.automatedExecutionFixedCost = executionFixedCost;
	}
	//Reporting------------------------------------------
	public static void setManualReporting_Per_Defect(double manualReportingPerDefectProductivity){
		UseCaseProfile.manualReporting = (1.0/manualReportingPerDefectProductivity);
	}
	public static void setAutomatedReporting_Per_Defect(double automatedReportingPerDefectProductivity){
		UseCaseProfile.automatedReporting = (1.0/automatedReportingPerDefectProductivity);
	}
	public static void setautomatedReportingFixedCost(double reportingFixedCost){
		UseCaseProfile.automatedReportingFixedTime = reportingFixedCost;
	}
	//Maintenance------------------------------------------
	public static void setComaintenanceRate(double comanitenanceRate){
		UseCaseProfile.COMAINTENANCE_RATE = comanitenanceRate;
	}
	public static void setqualityImprovementRate(double qualityImprovementRate){
		UseCaseProfile.QUALITYIMPROVEMENT_RATE = qualityImprovementRate;
	}
	//-----------------------------------------------------
	public static void setSelectedAlg(int selected_alg) {
		SELECTED_ALG = selected_alg;
	}
	public static int getSelectedAlg() {
		return SELECTED_ALG;
	}
}