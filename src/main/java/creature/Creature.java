package creature;

import java.util.ArrayList;
import java.util.Random;

public interface Creature {
    int getNodes();
    ArrayList<Double> getFrictions();
    ArrayList<ArrayList<Double>> getStrengths();
    ArrayList<ArrayList<Double>> getTimes();
    void mutate(Random random);
}
