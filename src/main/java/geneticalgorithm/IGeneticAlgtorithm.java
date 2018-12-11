package geneticalgorithm;

public interface IGeneticAlgtorithm {

    void reproduce();
    void select();

    double meanFitness();
    double bestFitness();
    double worstFitness();

    void setFitness(int i, double f);
    Object getSubject(int i);
    int getPopulationSize();

}
