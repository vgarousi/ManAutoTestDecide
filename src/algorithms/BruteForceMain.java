package algorithms;

import java.io.IOException;

/*This class implements brute force approach. It generates the binary 
 * values from 0 to 2^|use cases|*|activity size| and set the binary 
 * value in TADM cells to generate all possible TADM values. Then 
 * calculate the fitness value for each TADM and selects the best one*/

public class BruteForceMain {
	double bestFitness = -1000;
	int[][] bestItem;

	public BruteForceMain() {
		try {
			Utility.readFromExcel();
			Utility.fillReusableHashParts();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public int[][] generateAllBinaryValues() {
		int ArrayCellsSize = UseCaseProfile.USECASESIZE
				* UseCaseProfile.ACTIVITYSIZE;
		int[][] individual;
		double maxNumber = Math.pow(2, ArrayCellsSize);
		String format = "%" + ArrayCellsSize + "s";
		for (int i = 0; i < maxNumber; i++) {
			String x = Integer.toBinaryString(i);
			String binaryValue = String.format(format, x).replaceAll(" ", "0");
			individual = makeCombinationArray(binaryValue);
			findBestCombination(individual);
		}
		return bestItem;
	}
	
	
	private int[][] makeCombinationArray(String binaryValue) {
		int[][] tmp = new int[UseCaseProfile.USECASESIZE][UseCaseProfile.ACTIVITYSIZE];
		int k = 0;
		for (int i = 0; i < UseCaseProfile.USECASESIZE; i++) {
			for (int j = 0; j < UseCaseProfile.ACTIVITYSIZE; j++) {
				tmp[i][j] = binaryValue.charAt(k++) - 48;
			}
		}
		return tmp;
	}
	
	private void findBestCombination(int[][] individual) {
		Utility.reusablityGraphEdgeCounter(individual);
		double currentFit = calcFitness(individual);
				if (currentFit > bestFitness) {
			bestFitness = currentFit;
			bestItem = individual;
		} else if (currentFit == bestFitness) {
			int currentSelectedItem = Fitness.countSelectedItems(individual);
			int bestSelectedItem = Fitness.countSelectedItems(individual);
			if (currentSelectedItem > bestSelectedItem) {
				bestFitness = currentFit;
				bestItem = individual;
			}
		}
	}

	private double calcFitness(int[][] individual) {
		Fitness fitness = new Fitness();
		double fitnessValue = fitness.fitnessCalculator(individual, false);
		return fitnessValue;
	}

}