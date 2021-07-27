import java.util.*;
import java.io.*;

public class GeneticAlgorithm {
	private static stopwatchCPU timer1 = new stopwatchCPU();
	public static final int numberOfObjects = 20;
	public static final int[] weightsOfObjects = {349,833,684,309,471,680,96,246,872,219,729,253,658,911,941,569,964,480,301,991};
	public static final int[] valueOfObjects = {5,187,201,452,691,675,794,559,837,855,17,922,258,21,357,754,957,337,151,616};
	public static final int capacityOfKnapsack = 5000;
	
	public static final double crossProb = 0.6;
	public static final double mutProb = 0.002;
	
	public static final int popSize = 50;
	
	public static void main(String[] args) throws IOException{
		System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
		
		String file = "output.txt";
		
		PrintWriter outFile = new PrintWriter(file);
		
		int generation = 1000;
		
		
		//for loop for fine tuning analysis on p_g & popSize 
		//for(int i=generation;i<=1000;i=i+100) {
		
		//for loof for fine tuning analysis on p_mutate & p_cross
//		for(double i=crossProb;i<=1.0;i=i+0.1) {
//			for(double j=mutProb;j<=0.01;j=j+0.001) {
			
				timer1 = new stopwatchCPU();
				
				
				
				double time1 = timer1.elapsedTime();
				
				//array to keep fittest solution of each run
				int[][] testAlg = new int[30][1000];
				
				int cnt1 = 0;
				while(cnt1<30) {
					timer1 = new stopwatchCPU();
					
					
					
					time1 = timer1.elapsedTime();	
					Population initialPopulation = new Population();
					
	//				System.out.println(initialPopulation);
					
					Population currentPopulation = new Population(initialPopulation);
					Population nextPopulation = new Population(currentPopulation);
					
					
					
					GeneticAlgorithm.selection(currentPopulation, nextPopulation);
					GeneticAlgorithm.crossover(currentPopulation, nextPopulation);
					GeneticAlgorithm.mutation(nextPopulation);
					
					currentPopulation.calculateAttributes();
					nextPopulation.calculateAttributes();
					
					int cnt = 0;
					int fittest1;
					int fittest2 = 0;
					
					int fit1 = currentPopulation.getFitnessArr()[0][1];
					testAlg[cnt1][cnt] = fit1;
					cnt++;
					
	//				outFile.print((cnt1+1)+"\t"+currentPopulation.getFitnessArr()[0][1]+"\t");
					
					
					while(cnt<generation) {
						
						
						GeneticAlgorithm.selection(currentPopulation, nextPopulation);
						GeneticAlgorithm.crossover(currentPopulation, nextPopulation);
						GeneticAlgorithm.mutation(nextPopulation);
						
						//System.out.println(nextPopulation);
						
						fittest1 = currentPopulation.getFitnessArr()[0][1];
						fittest2 = nextPopulation.getFitnessArr()[0][1];
						//System.out.println("Fittest :" +nextPopulation.getFitnessArr()[0][1]);
						
						if(!(fittest1==fittest2))
							//cnt1=0;
						
						currentPopulation = new Population(nextPopulation);
						nextPopulation = new Population(nextPopulation);
						
						currentPopulation.calculateAttributes();
						nextPopulation.calculateAttributes();
						
	//					outFile.print((cnt+1)+"\t"+currentPopulation.getFitnessArr()[0][1]+"\t");
	//					outFile.println();
						
						int fit2 = fittest2;
						
						if(!(cnt==1000))
							testAlg[cnt1][cnt] = fit2;
						
						cnt++;
					}
					time1 = timer1.elapsedTime();
					System.out.println("Run number "+(cnt1+1)+" "+fittest2);
					System.out.printf("CPU Time in seconds: %f \n",time1);
					System.out.println();
					
	//				outFile.print(fittest2);
	//				outFile.println();
					
					cnt1++;
				}
				
	//		}
	//		}
	//			}
	//		}
	//		
					outFile.print("\t");
					
					for(int i = -1;i<30;i++) {
							if(!(i==-1))
								outFile.print((i+1)+"\t");
						for(int j = 0;j<1000;j++) {
							if(i==-1)
								outFile.print((j+1)+"\t");
							else
								outFile.print(testAlg[i][j]+"\t");
						}
						outFile.println();
				}
				outFile.close();
	}
	
	public static void selection(Population firstPop, Population secondPop) {
		int fittestChromosomeInd = firstPop.getFitnessArr()[0][0];
		int secondFittestChromosomeInd = firstPop.getFitnessArr()[1][0];
		
		secondPop.setChromosome(0, new Chromosome(firstPop.getGene(fittestChromosomeInd)));
		secondPop.setChromosome(1, new Chromosome(firstPop.getGene(secondFittestChromosomeInd)));
	}
	
	public static void crossover(Population firstPop, Population secondPop) {
		
		int cnt = 2;
		double crossoverProb = crossProb;
		while(cnt<firstPop.getPopulationSize()) {
			Chromosome parent1 = new Chromosome(firstPop.getParent());
			Chromosome parent2 = new Chromosome(firstPop.getParent());
			
			if(Math.random() <= crossoverProb) {
				for(int i=0;i<GeneticAlgorithm.numberOfObjects;i++) {
					if((parent1.getCapacity()-
						parent1.getGene(i)*GeneticAlgorithm.weightsOfObjects[i]+
						parent2.getGene(i)*GeneticAlgorithm.weightsOfObjects[i])<=GeneticAlgorithm.capacityOfKnapsack &&
					   (parent2.getCapacity()-
						parent2.getGene(i)*GeneticAlgorithm.weightsOfObjects[i]+
						parent1.getGene(i)*GeneticAlgorithm.weightsOfObjects[i])<=GeneticAlgorithm.capacityOfKnapsack) {
						
						int temp = parent1.getGene(i);
						
						parent1.setGene(i, parent2.getGene(i));
						parent2.setGene(i, temp);
						
						parent1.calculateFitness();
						parent2.calculateFitness();
					}
				}
				
				if(cnt<firstPop.getPopulationSize()) {
					secondPop.setChromosome(cnt, new Chromosome(parent1));
					cnt++;
				}
				if(cnt<firstPop.getPopulationSize()) {
					secondPop.setChromosome(cnt, new Chromosome(parent2));
					cnt++;
				}
				else {
					if(cnt<firstPop.getPopulationSize()) {
						secondPop.setChromosome(cnt, new Chromosome(parent1));
						cnt++;
					}
					if(cnt<firstPop.getPopulationSize()) {
						secondPop.setChromosome(cnt, new Chromosome(parent2));
						cnt++;
					}
				}
			}
		}
	}
	
	public static void mutation(Population pop) {
		
		double mutationProb = mutProb;
		
		for(int i=0;i<pop.getPopulationSize();i++) {
			for(int j=0;j<GeneticAlgorithm.numberOfObjects;j++) {
				if(Math.random() <= mutationProb && 
				   (pop.getGene(i).getCapacity()-
					pop.getGene(i).getGene(j)*GeneticAlgorithm.weightsOfObjects[j]+
					(1-pop.getGene(i).getGene(j))*GeneticAlgorithm.weightsOfObjects[j])<GeneticAlgorithm.capacityOfKnapsack) {
					pop.getGene(i).setGene(j, 1 - pop.getGene(i).getGene(j));
				}
			}
		}
		pop.calculateAttributes();
	}
}
