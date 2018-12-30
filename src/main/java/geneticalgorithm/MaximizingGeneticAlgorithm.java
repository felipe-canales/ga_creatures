package geneticalgorithm;

import java.util.ArrayList;

public abstract class MaximizingGeneticAlgorithm implements IGeneticAlgtorithm {
    ArrayList<Double> fitness;
    ArrayList<Object> population;

    private int populationSize;

    MaximizingGeneticAlgorithm(int size) {
        populationSize = size;
    }

    @Override
    public void select() {
        double prom = meanFitness();
        // Remover los peores
        int i = 0;
        while (i < fitness.size() && populationSize / 2 < population.size()) {
            if (fitness.get(i) < prom) {
                fitness.remove(i);
                population.remove(i);
            }
            else {
                i++;
            }
        }
    }

    @Override
    public double meanFitness() {
        double prom = 0;
        for ( Double x : fitness )
            prom += x;
        prom /= fitness.size();
        return prom;
    }

    @Override
    public double bestFitness() {
        double best = 0;
        for ( Double x : fitness )
            best = best > x? best : x;
        return best;
    }

    @Override
    public double worstFitness() {
        double worst = 0;
        for ( Double x : fitness )
            worst = worst < x? worst : x;
        return worst;
    }

    @Override
    public void setFitness(int i, double f) {
        fitness.set(i, f);
    }

    @Override
    public Object getSubject(int i) {
        return population.get(i);
    }

    @Override
    public int getPopulationSize() {
        return populationSize;
    }
}
