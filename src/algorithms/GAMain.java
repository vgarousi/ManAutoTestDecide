package algorithms;

import java.io.IOException;
import java.util.ArrayList;

public class GAMain {
	ArrayList<int[][]> population;
	static ArrayList<int[][]> newPopulation;
	
	final int NUMBEROFMUTATEDCELLS = (int) ((UseCaseProfile.USECASESIZE * UseCaseProfile.ACTIVITYSIZE) / GAProfile.MUTATIONFRACTION) + 1;
	double[][] fitnessValues;
	static double bestFit;
	static boolean debug = false;

	public int[][] GAAlgorithmBody() {
		try {
			Utility.readFromExcel();
			Utility.fillReusableHashParts();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		population = new ArrayList();
		population = createInitialPupulation();

		for (int GAStep = 0; GAStep < GAProfile.GENETIC_MAX_LOOP; GAStep++) {
			int[][][][] coupleArrays = Utility.copy4DArray(selectCouples());
			newPopulation = new ArrayList();
			for (int i = 0; i < population.size(); i++) {
				newPopulation.add(Utility.copy2DArray(population.get(i)));
			}

			for (int i = 0; i < GAProfile.POPULATION_SIZE / 2; i++) {
				int[][][] corssOvveredGens = crossOver(
						GAProfile.CROSSOVER_PROBABILITY,
						Utility.copy3DArray(coupleArrays[i]));
				int[][][] mutatedGens = mutation(
						GAProfile.MUTATION_PROBABILITY,
						Utility.copy3DArray(corssOvveredGens));
				newPopulation.add(Utility.copy2DArray(mutatedGens[0]));
				newPopulation.add(Utility.copy2DArray(mutatedGens[1]));
			}
			fitnessValues = new double[2][newPopulation.size()];
			for (int i = 0; i < newPopulation.size(); i++) {
				fitnessValues[0][i] = 0;
				fitnessValues[1][i] = -1;
			}

			for (int i = 0; i < newPopulation.size(); i++) {
				fitnessValues[0][i] = i;
				Utility.reusablityGraphEdgeCounter(newPopulation.get(i));
				fitnessValues[1][i] = calcFitness(newPopulation.get(i));
			}
			fitnessValues = Fitness.fitnessSort(fitnessValues, newPopulation);
			population = new ArrayList();
			for (int i = 0; i < GAProfile.POPULATION_SIZE; i++) {
				population.add(newPopulation.get((int) fitnessValues[0][i]));
			}
		}
	
		int[][] bestTADM = population.get(0);
		bestFit = fitnessValues[1][0];
		bestTADM = Utility.checkSpecialCases(bestTADM, bestFit);
		return bestTADM;
	}

	// ---------------------------------------------------------------------
	private ArrayList<int[][]> createInitialPupulation() {
		ArrayList<int[][]> population = new ArrayList();
		int[][] fullyManual = new int[UseCaseProfile.USECASESIZE][UseCaseProfile.ACTIVITYSIZE];
		for (int i = 0; i < UseCaseProfile.USECASESIZE; i++) {
			for (int j = 0; j < UseCaseProfile.ACTIVITYSIZE; j++) {
				fullyManual[i][j] = 0;
			}
		}
		population.add(fullyManual);
		for (int i = 1; i < GAProfile.POPULATION_SIZE; i++) {
			int[][] individual = createRandomIndividual();
			population.add(individual);
		}
		return population;
	}

	private int[][] createRandomIndividual() {
		int[][] individual;
		Chromosome g = new Chromosome();
		individual = g.getChromosome();
		return individual;
	}
	private int[][][][] selectCouples() {
		int halfPop = GAProfile.POPULATION_SIZE / 2;
		int[][][][] couples = new int[halfPop][2][][];
		for (int i = 0; i < halfPop; i = i + 1) {
			couples[i][0] = Utility.copy2DArray(population.get(i * 2));
			couples[i][1] = Utility.copy2DArray(population.get(i * 2 + 1));
		}
		return couples;
	}

	private double calcFitness(int[][] individual) {
		Fitness fitness = new Fitness();
		double fitnessValue = fitness.fitnessCalculator(individual, false);
		return fitnessValue;
	}

	private int[][][] crossOver(double CROSSOVER_PROBABILITY,
			int[][][] parrentArrays) {
		int[][][] parents = Utility.copy3DArray(parrentArrays);
		int[][][] corssOveredGens;
		double randomNumber = Math.random();

		if (randomNumber <= CROSSOVER_PROBABILITY) {
			corssOveredGens = Utility.copy3DArray(applyCrossOver(parents));
		} else {
			corssOveredGens = Utility.copy3DArray(parents);
		}
		return corssOveredGens;
	}

	private int[][][] applyCrossOver(int[][][] parrents) {
		int[][] parent1 = Utility.copy2DArray(parrents[0]);
		int[][] parent2 = Utility.copy2DArray(parrents[1]);
		int[][] tempParent = Utility.copy2DArray(parent1);
		int CROSSOVER_POINT = (int) Math.random()
				* (UseCaseProfile.USECASESIZE);
		for (int i = CROSSOVER_POINT; i < UseCaseProfile.USECASESIZE; i++) {
			for (int j = 0; j < UseCaseProfile.ACTIVITYSIZE; j++) {
				parent1[i][j] = parent2[i][j];
				parent2[i][j] = tempParent[i][j];
			}
		}
		int[][][] crossOveredParents = new int[UseCaseProfile.USECASESIZE][UseCaseProfile.ACTIVITYSIZE][2];
		crossOveredParents[0] = Utility.copy2DArray(parent1);
		crossOveredParents[1] = Utility.copy2DArray(parent2);
		return crossOveredParents;
	}

	private int[][][] mutation(double MUTATION_PROBABILITY,
			int[][][] parrentArrays) {
		int[][][] mutatedGens;
		double randomNumber = Math.random();
		if (randomNumber <= GAProfile.MUTATION_PROBABILITY) {
			mutatedGens = Utility.copy3DArray(applyMutation(parrentArrays));
		} else {
			mutatedGens = Utility.copy3DArray(parrentArrays);
		}
		return mutatedGens;
	}

	private int[][][] applyMutation(int[][][] parrentArrays) {
		int randomColumn, randomRow;
		int[][] parent1 = Utility.copy2DArray(parrentArrays[0]);
		int[][] parent2 = Utility.copy2DArray(parrentArrays[1]);
		for (int i = 0; i < NUMBEROFMUTATEDCELLS; i++) {
			randomColumn = (int) (Math.random() * UseCaseProfile.ACTIVITYSIZE);
			randomRow = (int) (Math.random() * UseCaseProfile.USECASESIZE);
			if (parent1[randomRow][randomColumn] == 0) {
				parent1[randomRow][randomColumn] = 1;
			} else if (parent1[randomRow][randomColumn] == 1) {
				parent1[randomRow][randomColumn] = 0;
			}
			if (parent2[randomRow][randomColumn] == 0) {
				parent2[randomRow][randomColumn] = 1;
			} else if (parent2[randomRow][randomColumn] == 1) {
				parent2[randomRow][randomColumn] = 0;
			}
		}
		parrentArrays[0] = Utility.copy2DArray(parent1);
		parrentArrays[1] = Utility.copy2DArray(parent2);
		return parrentArrays;
	}

}
