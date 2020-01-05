package algorithms;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;

import javax.swing.text.html.parser.ParserDelegator;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.format.Colour;
import jxl.read.biff.BiffException;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class Utility {
	
	public static int[][] copy2DArray(int[][] inputArray) {
		int[][] outputArray = new int[UseCaseProfile.USECASESIZE][UseCaseProfile.ACTIVITYSIZE];
		for (int i = 0; i < UseCaseProfile.USECASESIZE; i++) {
			for (int j = 0; j < UseCaseProfile.ACTIVITYSIZE; j++) {
				outputArray[i][j] = inputArray[i][j];
			}
		}
		return outputArray;
	}

	public static int[][][] copy3DArray(int[][][] inputArray) {
		int[][][] outputArray = new int[2][UseCaseProfile.USECASESIZE][UseCaseProfile.ACTIVITYSIZE];
		for (int k = 0; k < 2; k++) {
			for (int i = 0; i < UseCaseProfile.USECASESIZE; i++) {
				for (int j = 0; j < UseCaseProfile.ACTIVITYSIZE; j++) {
					outputArray[k][i][j] = inputArray[k][i][j];
				}
			}
		}
		return outputArray;
	}

	public static int[][][][] copy4DArray(int[][][][] inputArray) {
		int[][][][] outputArray = new int[GAProfile.POPULATION_SIZE / 2][2][UseCaseProfile.USECASESIZE][UseCaseProfile.ACTIVITYSIZE];
		for (int h = 0; h < GAProfile.POPULATION_SIZE / 2; h++) {
			for (int k = 0; k < 2; k++) {
				for (int i = 0; i < UseCaseProfile.USECASESIZE; i++) {
					for (int j = 0; j < UseCaseProfile.ACTIVITYSIZE; j++) {
						outputArray[h][k][i][j] = inputArray[h][k][i][j];
					}
				}
			}
		}
		return outputArray;
	}

	// ----------------------------------------------------
	
	public static void readFromExcel() throws IOException {
		File inputWorkbook = new File(UseCaseProfile.fileName);
		try {
			Workbook w = Workbook.getWorkbook(inputWorkbook);
			// Get the first sheet
			Sheet sheet = w.getSheet(0);
			// Loop over data
			
			for (int i = 1; i < UseCaseProfile.USECASESIZE + 1; i++) {
				for (int j = 1; j < UseCaseProfile.separationPoint + 1; j++) {
					Cell cell = sheet.getCell(j, i);
					if (cell == null) {
						System.err.println(i + "  " + j);
					}
					double x = Double.parseDouble(cell.getContents());
					UseCaseProfile.USECASE_PROFILE[i - 1][j - 1] = x;
				}
			}
			for (int i = 1; i < UseCaseProfile.USECASESIZE + 1; i++) {
				for (int j = UseCaseProfile.separationPoint + 1; j < UseCaseProfile.separationPoint + 3; j++) {
					Cell cell = sheet.getCell(j, i);
					String x = cell.getContents();
					UseCaseProfile.reusableParts[i - 1][j
							- UseCaseProfile.separationPoint - 1] = x;
				}
			}
		} catch (BiffException e) {
			e.printStackTrace();
		}
	}

	//public static void writeResultsToExecl(int[][] individual, double timeSaving, double ROI) throws RowsExceededException {
	public static void writeResultsToExecl(int[][] individual, double ROI) throws RowsExceededException {
		File exlFile = new File(UseCaseProfile.fileName);
		File tmpFile = new File("tmp.xls");
		try {
			Workbook existingWB = Workbook.getWorkbook(exlFile);
			WritableWorkbook writableWorkbook = Workbook.createWorkbook(
					tmpFile, existingWB);
			WritableSheet writableShee = writableWorkbook.getSheet("Results");
			if(writableShee != null){
				writableWorkbook.removeSheet(3);
			}
			WritableSheet writableSheet = writableWorkbook.createSheet("Results", 3);
			Label label;
			WritableCellFormat cf = new WritableCellFormat();
			label = new Label(1, 0, "Design");
			cf.setBackground(Colour.VERY_LIGHT_YELLOW);
			label.setCellFormat(cf);
			writableSheet.addCell(label);
			label = new Label(2, 0, "Script");
			label.setCellFormat(cf);
			writableSheet.addCell(label);
			label = new Label(3, 0, "Execute");
			label.setCellFormat(cf);
			writableSheet.addCell(label);
			label = new Label(4, 0, "Report");
			label.setCellFormat(cf);
			writableSheet.addCell(label);
			WritableSheet sheet = writableWorkbook.getSheet(0);
			for (int i = 1; i < UseCaseProfile.USECASESIZE + 1; i++) {
				Cell cell = sheet.getCell(0, i);
				if (cell == null) {
					System.err.println(i + " : empty cell!  " );
				}
				label = new Label(0, i, cell.getContents());
				label.setCellFormat(cf);
				writableSheet.addCell(label);
			}
						
			for (int i = 0; i < UseCaseProfile.USECASESIZE; i++) {
				label = generateLable(individual[i][0], 0,i);
				writableSheet.addCell(label);
				label = generateLable(individual[i][1], 1,i);
				writableSheet.addCell(label);
				label = generateLable(individual[i][1], 2,i);
				writableSheet.addCell(label);
				label = generateLable(individual[i][2], 3,i);
				writableSheet.addCell(label);
			}
label = new Label(1, UseCaseProfile.USECASESIZE + 2, "ROI:");
			label.setCellFormat(cf);
			writableSheet.addCell(label);
			label = new Label(2, UseCaseProfile.USECASESIZE + 2, ROI+"");
			label.setCellFormat(cf);
			writableSheet.addCell(label);
			// Write and close the workbook
			writableWorkbook.write();
			writableWorkbook.close();
			existingWB.close();
			exlFile.delete();
			tmpFile.renameTo(exlFile);
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static Label generateLable(int TADMCell,  int j, int i){
		Label label ;
		if (TADMCell == 1) {
			label = new Label(j + 1, i + 1, "Yes");
		} else {
			label = new Label(j + 1, i + 1, "No");
		}
		return label;
	}
	// --------------------------------------------------------------------------------


	public static void fillReusableHashParts() throws IOException {
		File inputWorkbook = new File(UseCaseProfile.fileName);
		try {
			Workbook w = Workbook.getWorkbook(inputWorkbook);
			// Get the second sheet
			Sheet sheet = w.getSheet(1);
			// Loop over data
			for (int i = 0; i < sheet.getRows(); i++) { // sheet.getRows()
				Cell cell = sheet.getCell(0, i);
				String item = cell.getContents();
				cell = sheet.getCell(1, i);
				double costFactor = Double.parseDouble(cell.getContents());
				UseCaseProfile.reusablePartsCostFactor.put(item, costFactor);
				cell = sheet.getCell(2, i);
				UseCaseProfile.reusablePartsCostComplexity.put(item,
						cell.getContents());
			}
		} catch (BiffException e) {
			e.printStackTrace();
		}
	}

	public static void reusablityGraphEdgeCounter(int[][] individual) {
		UseCaseProfile.graphCounter = new HashMap();
		for (int usecaseID = 0; usecaseID < UseCaseProfile.USECASESIZE; usecaseID++) {
			if (individual[usecaseID][0] == 1) {
				StringTokenizer st = new StringTokenizer(
						UseCaseProfile.reusableParts[usecaseID][0]);
				while (st.hasMoreTokens()) {
					Integer tmp = 0;
					String x = st.nextToken();
					if (UseCaseProfile.graphCounter.get(x) == null) {
						tmp = 1;
					} else {
						tmp = (Integer) (UseCaseProfile.graphCounter.get(x)) + 1;
					}
					UseCaseProfile.graphCounter.put(x, tmp);
				}
			}
			if (individual[usecaseID][1] == 1) {
				String scripAndAssertion = UseCaseProfile.reusableParts[usecaseID][1]
						+ " " + UseCaseProfile.reusableParts[usecaseID][2];
				StringTokenizer st = new StringTokenizer(scripAndAssertion);
				while (st.hasMoreTokens()) {
					Integer tmp = 0;
					String x = st.nextToken();
					if (UseCaseProfile.graphCounter.get(x) == null) {
						tmp = 1;
					} else {
						tmp = (Integer) (UseCaseProfile.graphCounter.get(x)) + 1;
					}
					UseCaseProfile.graphCounter.put(x, tmp);
				}
			}
		}
	}

	// ------------------------------------------------------------------------------
	public static int[][] checkSpecialCases(int[][] individual,
			double Bestfitness) {
		int[][] noDesign = new int[UseCaseProfile.USECASESIZE][UseCaseProfile.ACTIVITYSIZE];
		int[][] noScript = new int[UseCaseProfile.USECASESIZE][UseCaseProfile.ACTIVITYSIZE];
		int[][] noReport = new int[UseCaseProfile.USECASESIZE][UseCaseProfile.ACTIVITYSIZE];
		for (int usecaseID = 0; usecaseID < UseCaseProfile.USECASESIZE; usecaseID++) {
			noDesign[usecaseID][0] = 0;
			noDesign[usecaseID][1] = individual[usecaseID][1];
			noDesign[usecaseID][2] = individual[usecaseID][2];

			noScript[usecaseID][0] = individual[usecaseID][0];
			noScript[usecaseID][1] = 0;
			noScript[usecaseID][2] = individual[usecaseID][2];

			noReport[usecaseID][0] = individual[usecaseID][0];
			noReport[usecaseID][1] = individual[usecaseID][1];
			noReport[usecaseID][2] = 0;
		}

		Utility.reusablityGraphEdgeCounter(noDesign);
		Fitness fitness = new Fitness();
		double fitnessValueD = fitness.fitnessCalculator(noDesign, false);
		Utility.reusablityGraphEdgeCounter(noScript);
		double fitnessValueS = fitness.fitnessCalculator(noScript, false);
		Utility.reusablityGraphEdgeCounter(noReport);
		double fitnessValueR = fitness.fitnessCalculator(noReport, false);
		if (fitnessValueD > Bestfitness) {
			Bestfitness = fitnessValueD;
			individual = noDesign;
		}
		if (fitnessValueS > Bestfitness) {
			Bestfitness = fitnessValueS;
			individual = noScript;
		}
		if (fitnessValueR > Bestfitness) {
			Bestfitness = fitnessValueR;
			individual = noReport;
		}
		GAMain.bestFit = Bestfitness;
		return individual;
	}
}