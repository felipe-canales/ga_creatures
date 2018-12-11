package creature;

import java.util.ArrayList;
import java.util.Random;

public class CreatureVariable extends AbstractCreature {
    // Constructor
    public CreatureVariable(Random r) {
        super(r, r.nextInt(6) + 2);
    }

    // Constructor
    public CreatureVariable(Creature c) {
        super(c);
    }

    // addNode allows mutation by adding another node

    // editMuscle allows mutation by changing the properties of a muscle
    private void editMuscle(Random r){ //int i, int j, double newStrength, double newTime) {
        int i = r.nextInt(nodes - 1) + 1;
        int j = r.nextInt(i);
        strengths.get(i).set(j, r.nextDouble());
        times.get(i).set(j, r.nextDouble());
    }

    private void editNode(Random r) {
        frictions.set(r.nextInt(nodes), r.nextDouble());
    }

    private void removeNode(Random random) {
        if (nodes == 2)
            return;
        int i = random.nextInt(nodes);
        frictions.remove(i);
        strengths.remove(i);
        for ( ArrayList<Double> a : strengths ) {
            if (a.size() > i) a.remove(i);
        }
        times.remove(i);
        for ( ArrayList<Double> a : times ) {
            if (a.size() > i) a.remove(i);
        }
        nodes--;
    }

    public void mutate(Random random) {
        int chance = random.nextInt(5);
        switch (chance) {
            case 0:
                editNode(random);
                break;
            case 1:
                editMuscle(random);
                break;
            case 2:
                addNode(random);
                break;
            case 3:
                removeNode(random);
                break;
            default:
                break;
        }
    }
}
