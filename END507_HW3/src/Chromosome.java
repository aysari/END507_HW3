
public class Chromosome {
	private int[] gene;
	public int fitness;
	public int usedCapacity;
	
	public Chromosome (int length) {
		gene = new int[length];
		fitness = 0;
		usedCapacity = 0;
	}
	
	public Chromosome(Chromosome chromosome) {
		gene = new int[GeneticAlgorithm.numberOfObjects];
		
		for(int i=0;i<GeneticAlgorithm.numberOfObjects;i++) {
			gene[i] = chromosome.getGene(i);
			usedCapacity += gene[i]*GeneticAlgorithm.weightsOfObjects[i];
		}
		
		this.calculateFitness();
	}
	
	public Chromosome chromosomeInitilization() {
		
		for(int i=0;i<gene.length&&(usedCapacity+GeneticAlgorithm.weightsOfObjects[i]<=5000);i++) {
			if(Math.random()<= 0.5) {
				gene[i] = 1;
				usedCapacity += GeneticAlgorithm.weightsOfObjects[i];
			}
			else {
				gene[i] = 0;
			}
		}
		
		this.calculateFitness();
		return this;
	}
	
	public int calculateFitness() {
		fitness = 0;
		usedCapacity = 0;
		for(int i=0;i<gene.length;i++) {
			fitness += gene[i]*GeneticAlgorithm.valueOfObjects[i];
			usedCapacity += gene[i]*GeneticAlgorithm.weightsOfObjects[i];
		}
		
		return fitness;
	}
	
	public int getFitness() {
		return fitness;
	}
	
	public int getCapacity() {
		return usedCapacity;
	}
	
	public void setGene(int index, int value) {
		gene[index] = value;
	}
	
	public int getGene(int index) {
		return gene[index];
	}
	public String toString() {
		String str = "";
		for(int i=0;i<this.gene.length;i++) {
			str += this.gene[i] + " ";
		}
		return str;
	}
}
