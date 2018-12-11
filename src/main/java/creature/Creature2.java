package creature;

import java.util.Random;

public class Creature2 extends AbstractCreature{
    public Creature2(Random r) {
        super(r, 2);
    }

    public Creature2(Creature old) {
        super(old);
    }

    @Override
    public void mutate(Random random) {

    }
}
