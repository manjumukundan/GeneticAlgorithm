
public class GeneticAlgorithm {

	public static int xCoord[] = {34,47,5,93,91,57,55,2,46,32,99,8,87,92,39,72,66,5,44,18,59,88,75,64,49,23,77,92,11,70,39,32,79,54,
			90,70,39,72,77,3,80,74,63,66,94,78,14,17,65,52,36,73,4,76,12,54,33,53,77,36,14,75,49,51,90,30,29,
			85,11,73,29,49,77,55,32,59,86,18,48,0,9,69,57,80,30,59,47,75,23,7,28,78,37,7,3,76,86,45,
			43,58,75,58,12,28,55,37,23,6,61,14,29,95,22,63,29,78,29,50,86,84};
	
	public static int yCoord[] = {63,1,31,9,64,45,44,40,3,29,83,27,93,38,83,2,86,70,73,43,76,80,13,
			11,20,9,94,5,91,0,42,61,90,53,89,47,70,95,61,41,19,83,61,88,60,63,25,86,16,89,40,21,10,
			29,93,86,93,56,61,39,6,94,32,9,0,76,3,1,88,37,48,22,8,67,12,43,73,26,74,32,98,40,1,94,
			50,32,19,71,71,94,79,59,19,85,12,44,64,55,27,37,67,79,39,39,68,88,2,21,35,89,62,34,26,22,58,61,96,5,47,1};
	
	private static final int initPopulationSize = 30;
	
	private static final long generations = 1000000;
	
	private static final double mutationRate = 0.00001;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		for (int i = 0; i < xCoord.length; i ++)
		{
			City city = new City(xCoord[i], yCoord[i], i + 1);
			City.addCity(city);
		}
		
        long startTime = System.currentTimeMillis();
        
		// create population with random tours of size 30 tours.
		Population pop = new Population(initPopulationSize, true);
        System.out.println("Initial random tour distance: " + pop.getFittest().getTourLength());

        // get an a new population from the initial population by applying the genetic algorithms.
        pop = evolvePopulation(pop);
        
        // Execute the same algorithm for 1 million times to get generations of population.
        for (long i = 0; i < generations; i++) {
            pop = evolvePopulation(pop);
            // find intermediate fittest or best tours.
            if (i > 0)
            {
            	int j = (int)i % 100000;
            	if (j == 0)
            	{
            		System.out.println("Intermediate random tour distance " + i + " : " + pop.getFittest().getTourLength());
            	}
            }        
        }

        long endTime = System.currentTimeMillis();
        
        // Result is the fittest tour among the tours in the final population.
        System.out.println("Best tour distance: " + pop.getFittest().getTourLength());
        System.out.println("Solution:");
        System.out.println(pop.getFittest());
        
        long timeTaken = (endTime - startTime)/(1000 * 60);
        System.out.println("Time Taken: " + timeTaken + "min");
	}
	
	// Create a new population.
    public static Population evolvePopulation(Population pop) {
        Population newPopulation = new Population(pop.populationSize(), false);

        newPopulation.saveTour(0, pop.getFittest());

        // Crossover population, by finding parent tours from randomly created population.
        // add the child tour to the new population, continue this for all the populations in the ancestor population.    
        for (int i = 1; i < newPopulation.populationSize(); i++) {
            // Select parents
            Tour parent1 = selectParent(pop);
            Tour parent2 = selectParent(pop);
            // Crossover parents
            Tour child = crossover(parent1, parent2);
            // Add child to new population
            newPopulation.saveTour(i, child);
        }

        // Mutate the new population
        for (int i = 1; i < newPopulation.populationSize(); i++) {
            mutate(newPopulation.getTour(i));
        }

        return newPopulation;
    }

    // Crossover the parent to create an offspring.
    public static Tour crossover(Tour parent1, Tour parent2) 
    {
        Tour child = new Tour();

        // Get start and end sub tour positions for parent1's tour
        int startPos = (int) (Math.random() * parent1.tourSize());
        int endPos = (int) (Math.random() * parent1.tourSize());

        
        for (int i = 0; i < child.tourSize(); i++) 
        {
            if (startPos < endPos && i > startPos && i < endPos) 
            {
                child.setCity(i, parent1.getCity(i));
            }
            else if (startPos > endPos) {
                if (!(i < startPos && i > endPos)) {
                    child.setCity(i, parent1.getCity(i));
                }
            }
        }

        // Loop through parent2's city tour
        for (int i = 0; i < parent2.tourSize(); i++) 
        {
            if (!child.containsCity(parent2.getCity(i)))
            {
                for (int j = 0; j < child.tourSize(); j++) 
                {
                    if (child.getCity(j) == null) 
                    {
                        child.setCity(j, parent2.getCity(i));
                        break;
                    }
                }
            }
        }
        return child;
    }

    
    // Mutate a tour using swap mutation
    private static void mutate(Tour tour) {
     
        for(int tourPos1=0; tourPos1 < tour.tourSize(); tourPos1++){
            
            if(Math.random() < mutationRate)
            {
                // Get a second random position in the tour
                int tourPos2 = (int) (tour.tourSize() * Math.random());

                City city1 = tour.getCity(tourPos1);
                City city2 = tour.getCity(tourPos2);
                tour.setCity(tourPos2, city1);
                tour.setCity(tourPos1, city2);
            }
        }
    }

    // Selects parent tour for crossover
    private static Tour selectParent(Population pop) 
    {     
        Population parentPop = new Population(5, false);
       
        for (int i = 0; i < 5; i++) {
            int pos = (int) (Math.random() * pop.populationSize());
            parentPop.saveTour(i, pop.getTour(pos));
        }
        // Get the fittest tour
        Tour fittest = parentPop.getFittest();
        return fittest;
    }

}
