package algorithms;

public class GAProfile {
	

	static double CROSSOVER_PROBABILITY = 0.85; 
	static double MUTATION_PROBABILITY = 0.2;   
	static int POPULATION_SIZE = 60; 		
	static int GENETIC_MAX_LOOP = 14000;        
	static int MUTATIONFRACTION = 25;

	// Setup methods
	public static void setLOOP(int loop) {
		GENETIC_MAX_LOOP = loop;
	}

	public static void setPOPULATION_SIZE(int POPULATION) {
		POPULATION_SIZE = POPULATION;
	}

	public static void setMutationFraction(int MUTATION_FRACTION) {
		MUTATIONFRACTION = MUTATION_FRACTION;
	}

	public static void setMutationProbability(double MUTATION_PROB) {
		MUTATION_PROBABILITY = MUTATION_PROB;
	}

	public static void setCrossoverProbability(double CROSSOVER_PROB) {
		CROSSOVER_PROBABILITY = CROSSOVER_PROB;
	}
}