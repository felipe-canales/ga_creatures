package facade;

import creature.Creature;
import geneticalgorithm.CreatureGA;

public class Facade {
    private CreatureGA ga;
    private double bestFitness;
    private int currentCreature;

    public Facade(int popSize) {
        ga = new CreatureGA(popSize);
        bestFitness = 0.0;
        currentCreature = 0;
    }

    public Creature getCreature() {
        if (ga.getPopulationSize() == currentCreature) {
            currentCreature = 0;
        }
        return ga.getCreature(currentCreature);
    }

    public boolean finishedGen() {
        return ga.getPopulationSize() == currentCreature;
    }

    public double[] newGen() {
        double[] fitnesses = {ga.bestFitness(), ga.meanFitness(), ga.worstFitness()};
        ga.select();
        ga.reproduce();
        return fitnesses;
    }

    public void setFitness(double fitness) {
        ga.setFitness(currentCreature++, fitness);
        bestFitness = bestFitness < fitness ? bestFitness : fitness;
    }
}
