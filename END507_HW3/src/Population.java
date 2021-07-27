
import java.util.*;

public class Population {
	private Chromosome[] population;
	private int[][] fitnessArr;
	private int populationSize;
	private int totalFitness;
	
	public Population() {
		populationSize = GeneticAlgorithm.popSize;
		
		population = new Chromosome[populationSize];
		fitnessArr = new int[populationSize][2];
		
		for(int i=0;i<populationSize;i++) {
			
			population[i] = new Chromosome(GeneticAlgorithm.numberOfObjects).chromosomeInitilization();
			fitnessArr[i][0]=i;
			fitnessArr[i][1]=population[i].calculateFitness();
			totalFitness += population[i].getFitness();
		}
		
		sortAsc(fitnessArr,2);
	}
	
	public Population(Population pop) {
		
		populationSize = pop.getPopulationSize();
		
		population = new Chromosome[populationSize];
		fitnessArr = new int[populationSize][2];
		totalFitness = pop.getTotalFitness();
		
		for(int i=0;i<populationSize;i++) {
			population[i] = new Chromosome(pop.getGene(i));
			fitnessArr[i][0]=i;
		}
	}
	
	public void calculateAttributes() {
		
		totalFitness = 0;
		
		for(int i=0;i<populationSize;i++) {
			population[i].calculateFitness();
			fitnessArr[i][0]=i;
			fitnessArr[i][1]=population[i].calculateFitness();
			totalFitness += population[i].getFitness();
		}
		
		sortAsc(fitnessArr,2);
	}
	
	public Chromosome getParent() {
		Random generator = new Random();
		
		int rand = generator.nextInt(totalFitness)+1;
		
		int cumFitness = 0;
		int index = 0;
		
		for(int i=0;i<populationSize&&cumFitness<=rand;i++) {
			cumFitness += population[i].getFitness();
			index = i;
		}
		return population[index];
	}
	
	public void setChromosome(int chromosomeIndex, Chromosome gene) {
		population[chromosomeIndex] = gene;
	}
	
	public Chromosome getGene(int i) {
		return population[i];
	}
	
	public int getPopulationSize() {
		return populationSize;
	}
	
	public int[][] getFitnessArr() {
		return fitnessArr;
	}
	
	public int getTotalFitness() {
		return totalFitness;
	}
    public void sortAsc(int[][] array, final int columnNumber){
        Arrays.sort(array, new Comparator<int[]>() {
            @Override
            public int compare(int[] first, int[] second) {
               if(first[columnNumber-1] < second[columnNumber-1]) return 1;
               else return -1;
            }
        });
    }
    
    public String toString() {
    	
    	String str = "";
    	for(int i=0;i<populationSize;i++) {
    		str += "Chromosome " + (i+1) +"\t| " + population[i].toString() + "| Fitness: "+population[i].getFitness()+"\n";
		}
    	return str;
    }
}
