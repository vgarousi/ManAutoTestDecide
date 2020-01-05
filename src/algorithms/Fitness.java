package algorithms;

import java.util.ArrayList;
public class Fitness {
	static double selectedForReporting;
	static double selectedForExecution;
	static double selectedForDesign;
	static double totalTimeSaving;
	static double totalROI;

	public static double getTotalROI(){
		return totalROI;
	}
	public static double[][] fitnessSort(double[][] fitnessValues,
			ArrayList<int[][]> newPopulation) {
		for (int i = 0; i < fitnessValues[0].length - 1; i++) {
			for (int j = 0; j < fitnessValues[0].length - 1; j++) {
				if (fitnessValues[1][j] < fitnessValues[1][j + 1]) {
					double temp1 = fitnessValues[0][j];
					double temp2 = fitnessValues[1][j];
					fitnessValues[0][j] = fitnessValues[0][j + 1];
					fitnessValues[1][j] = fitnessValues[1][j + 1];
					fitnessValues[0][j + 1] = temp1;
					fitnessValues[1][j + 1] = temp2;
				}

				if (fitnessValues[1][j] == fitnessValues[1][j + 1]) {
					int currentSelectedItem = Fitness
							.countSelectedItems(newPopulation
									.get((int) fitnessValues[0][j]));
					int nextSelectedItem = Fitness
							.countSelectedItems(newPopulation
									.get((int) fitnessValues[0][j + 1]));
					if (currentSelectedItem > nextSelectedItem) {
						double temp1 = fitnessValues[0][j];
						double temp2 = fitnessValues[1][j];
						fitnessValues[0][j] = fitnessValues[0][j + 1];
						fitnessValues[1][j] = fitnessValues[1][j + 1];
						fitnessValues[0][j + 1] = temp1;
						fitnessValues[1][j + 1] = temp2;
					}
				}
			}
		}
		return fitnessValues;
	}

	public double fitnessCalculator(int[][] individual, boolean printResult) {
	
		selectedForDesign = 0;
		selectedForExecution = 0;
		selectedForReporting = 0;
		countSelectedUCsForEachActivity(individual);
		double manualDesignTime = 0;
		double automatedDesignTime = 0;
		double manualScriptingTime = 0;
		double automatedScriptingTime = 0;
		double manualExecutionTime = 0;
		double automatedExecutionTime = 0;
		double manualReportingTime = 0;
		double automatedReportingTime = 0;

		for (int usecaseID = 0; usecaseID < UseCaseProfile.USECASESIZE; usecaseID++) {
			if (individual[usecaseID][0] == 1) {
				double[] designInfo = UseCaseProfile.getTestDesignInfo(usecaseID);
				manualDesignTime += designInfo[0];
				automatedDesignTime += designInfo[1];
			}
			if (individual[usecaseID][1] == 1) {
				double[] scriptInfo = UseCaseProfile.getTestScriptingInfo(usecaseID);
				manualScriptingTime += scriptInfo[0];
				automatedScriptingTime += scriptInfo[1];

				double[] executeInfo = UseCaseProfile.getTestExecuteInfo(usecaseID);
				manualExecutionTime += executeInfo[0];
				automatedExecutionTime += executeInfo[1];
			}
			if (individual[usecaseID][2] == 1) {
				if (individual[usecaseID][1] == 0) {
				} else if (individual[usecaseID][1] == 1) {
					double[] reportInfo = UseCaseProfile
							.getTestReportingInfo(usecaseID);
					manualReportingTime += reportInfo[0];
					automatedReportingTime += reportInfo[1];
				}
			}
		}
		
		double ROIDesign = 0, ROIExecute = 0, ROIReport = 0, FitnessTotal = 0;
		if (selectedForDesign != 0) {
			ROIDesign = (manualDesignTime - automatedDesignTime) / (UseCaseProfile.automatedDesignFixedCost);
			FitnessTotal += (ROIDesign - 1);
		}
		double executionBenefit = 0;
		double scriptCost = 0;
		if (selectedForExecution != 0) {
			executionBenefit = (manualExecutionTime - (UseCaseProfile.automatedExecutionFixedCost * UseCaseProfile.NUMBEROFEXECUTION));
			scriptCost = UseCaseProfile.automatedScriptingFixedCost + automatedScriptingTime - manualScriptingTime;
			ROIExecute = (executionBenefit * 1.0 / scriptCost);
			FitnessTotal += (ROIExecute - 1);
		}

		if (selectedForReporting != 0) {
			ROIReport = (manualReportingTime - automatedReportingTime)/ (UseCaseProfile.automatedReportingFixedTime);
			FitnessTotal += (ROIReport - 1);
		}
		
		totalROI = (ROIDesign + ROIExecute + ROIReport);
		totalTimeSaving = (manualDesignTime - automatedDesignTime) + executionBenefit + (manualReportingTime - automatedReportingTime);
		if (printResult) {
			System.err.println("Design TimeSaving: " + '\t'+ (manualDesignTime - automatedDesignTime) + '\t'	+ "Design Cost" + '\t'+ (UseCaseProfile.automatedDesignFixedCost));
			System.err.println("Execution TimeSaving: " + '\t'+ executionBenefit + '\t'+ "Scripting Cost" + '\t' + scriptCost);
			System.err.println("Reporting TimeSaving: " + '\t'+ (manualReportingTime - automatedReportingTime)	+ '\t' + "Reporting Cost" + '\t'+ (UseCaseProfile.automatedReportingFixedTime));
			System.err.println("Separate ROI: " + '\t' + (ROIDesign) + '\t' + (ROIExecute)	+ '\t' + (ROIReport));
			System.err.println("Total Time Saving = " + '\t' + totalTimeSaving);
			System.err.println("Total ROI = " + '\t' + totalROI);
			System.err.println("---------------------------");
			Chromosome.printChromosome(individual, printResult);
			System.err.println("---------------------------");
	
		}

		return FitnessTotal;
	}
	
	private void countSelectedUCsForEachActivity(int[][] individual) {
		for (int usecaseID = 0; usecaseID < UseCaseProfile.USECASESIZE; usecaseID++) {
			if (individual[usecaseID][0] == 1) {
				selectedForDesign++;
			}
			if (individual[usecaseID][1] == 1) {
				selectedForExecution++;
			}
			if (individual[usecaseID][2] == 1) {
				selectedForReporting++;
			}
		}
	}
	
	public static int countSelectedItems(int[][] individual) {
		int selectedItem = 0;
		for (int usecaseID = 0; usecaseID < UseCaseProfile.USECASESIZE; usecaseID++) {
			for (int activityID = 0; activityID < UseCaseProfile.ACTIVITYSIZE; activityID++) {
				if (individual[usecaseID][activityID] == 1) {
					selectedItem++;
				}
			}
		}
		return selectedItem;
	}
}