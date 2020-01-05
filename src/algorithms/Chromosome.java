package algorithms;

public class Chromosome {

	int chromosome[][]; 
	Chromosome() {
		
		chromosome = new int[UseCaseProfile.USECASESIZE][UseCaseProfile.ACTIVITYSIZE];
		for (int i = 0; i < UseCaseProfile.USECASESIZE; i++) {
			for (int j = 0; j < UseCaseProfile.ACTIVITYSIZE; j++) {
				chromosome[i][j] = 0;
			}
		}
	}

	
	private void createRandomChromosome() {
		for (int i = 0; i < UseCaseProfile.USECASESIZE; i++) {
			for (int j = 0; j < UseCaseProfile.ACTIVITYSIZE; j++) {
				double randomNumber = Math.random();
				if (randomNumber >= 0.5) {
					chromosome[i][j] = 1;
				}
			}
		}
	}

	public int[][] getChromosome() {
		createRandomChromosome();
		return chromosome;
	}

	public static void printChromosome(int[][] g, boolean debug) {
		if (debug) {
			for (int i = 0; i < UseCaseProfile.USECASESIZE; i++) {
				for (int j = 0; j < UseCaseProfile.ACTIVITYSIZE; j++) {
					System.err.print(g[i][j] + " \t");
				}
				System.err.println();
			}
			System.err.println("-----------------------------------");
		}
	}
}