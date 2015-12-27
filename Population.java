/*
* Population.java
* Manages a population of candidate tours
*/

public class Population {

    // population of tours
    Tour[] tours;

    // Construct a population
    public Population(int populationSize, boolean initialise) {
        tours = new Tour[populationSize];
        if (initialise) {
            // Loop and create individuals
            for (int i = 0; i < populationSize(); i++) {
                Tour newTour = new Tour();
                newTour.createRandomTour();
                saveTour(i, newTour);
            }
        }
    }
    
    public void saveTour(int index, Tour tour) {
        tours[index] = tour;
    }
    
    public Tour getTour(int index) {
        return tours[index];
    }

    // Gets the best tour in the population
    public Tour getFittest() {
        Tour fittest = tours[0];
        for (int i = 1; i < populationSize(); i++) {
            if (fittest.getFitness() <= getTour(i).getFitness()) {
                fittest = getTour(i);
            }
        }
        return fittest;
    }

    public int populationSize() {
        return tours.length;
    }
}
