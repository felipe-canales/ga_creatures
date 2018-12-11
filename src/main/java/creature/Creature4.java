package creature;

import java.util.Random;

public class Creature4 extends AbstractCreature {
    // Constructor
    public Creature4(Random r) {
        super(r, 4);
    }

    // Constructor
    public Creature4(Creature c) {
        super(c);
    }

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


    @Override
    public void mutate(Random random) {
        int chance = random.nextInt(5);
        switch (chance) {
            case 0:
            case 1:
                editNode(random);
                break;
            case 2:
            case 3:
                editMuscle(random);
                break;
            default:
                break;
        }
    }
}
