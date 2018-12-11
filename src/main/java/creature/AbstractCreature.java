package creature;

import java.util.ArrayList;
import java.util.Random;

public abstract class AbstractCreature implements Creature {
    protected int nodes;
    protected ArrayList<Double> frictions;
    protected ArrayList<ArrayList<Double>> strengths;
    protected ArrayList<ArrayList<Double>> times;

    public AbstractCreature(Random r, int t_nodes) {
        nodes = 0;
        frictions = new ArrayList<>();
        strengths = new ArrayList<>();
        times = new ArrayList<>();
        for (int i = 0; i < t_nodes; i++) {
            this.addNode(r);
        }
    }

    public AbstractCreature(Creature c) {
        nodes = c.getNodes();
        frictions = new ArrayList<>(c.getFrictions());
        strengths = new ArrayList<>();
        times = new ArrayList<>();
        for (ArrayList<Double> s : c.getStrengths()) {
            strengths.add(new ArrayList<>(s));
        }
        for (ArrayList<Double> t : c.getTimes()) {
            times.add(new ArrayList<>(t));
        }
    }

    protected void addNode(Random r) {
        ArrayList<Double> s = new ArrayList<>();
        ArrayList<Double> t = new ArrayList<>();
        for (int j = 0; j < nodes; j++) {
            s.add(r.nextDouble());
            t.add(r.nextDouble());
        }
        frictions.add(r.nextDouble());
        strengths.add(s);
        times.add(t);
        nodes++;
    }

    public int getNodes() {
        return nodes;
    }

    public ArrayList<Double> getFrictions() {
        return frictions;
    }

    public ArrayList<ArrayList<Double>> getStrengths() {
        return strengths;
    }

    public ArrayList<ArrayList<Double>> getTimes() {
        return times;
    }
}
