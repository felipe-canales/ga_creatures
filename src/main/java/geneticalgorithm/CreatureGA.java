package geneticalgorithm;

import creature.Creature;
import creature.Creature4;

import java.util.ArrayList;
import java.util.Random;

public class CreatureGA extends MaximizingGeneticAlgorithm {
    private Random random;

    public CreatureGA(int popSize, Random r) {
        super(popSize);
        random = r;
        this.population = new ArrayList<>();
        this.fitness = new ArrayList<>();

        for (int i = 0; i < popSize; i++) {
            population.add(new Creature4(random));
            fitness.add(0.0);
        }
    }

    public CreatureGA(int popSize) {
        this(popSize, new Random());
    }

    @Override
    public void reproduce() {
        int length = population.size();
        for (int i = 0; i < length; i++) {
            if (population.size() >= getPopulationSize())
                return;
            Creature n = new Creature4(getCreature(i));
            n.mutate(random);
            population.add(n);
            fitness.add(0.0);
        }
    }

    public Creature getCreature(int i) {
        return (Creature)getSubject(i);
    }
}
