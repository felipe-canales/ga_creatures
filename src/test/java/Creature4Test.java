import creature.Creature;
import creature.Creature4;
import org.junit.*;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.*;

public class Creature4Test {
    private Creature c;
    private Random r;

    @Before
    public void setUp() {
        r = new Random(0);
        c = new Creature4(r);
    }

    @Test
    public void constructor() {
        Creature c2 = new Creature4(c);
        assertEquals(c.getFrictions(), c2.getFrictions());
        assertEquals(c.getNodes(), c2.getNodes());
        assertEquals(c.getStrengths(), c2.getStrengths());
        assertEquals(c.getTimes(), c.getTimes());
    }

    @Test
    public void consistencyFriction() {
        int n = c.getNodes();
        ArrayList<Double> f = c.getFrictions();
        assertEquals(n, f.size());
    }

    @Test
    public void consistencyStrength() {
        ArrayList<ArrayList<Double>> s = c.getStrengths();
        assertEquals(4, s.size());
        int x = 0;
        for (ArrayList<Double> a : s)
            assertEquals(x++, a.size());
        assertEquals(4, x);
    }

    @Test
    public void consistencyTime() {
        ArrayList<ArrayList<Double>> t = c.getTimes();
        assertEquals(4, t.size());
        int x = 0;
        for (ArrayList<Double> a : t)
            assertEquals(x++, a.size());
        assertEquals(4, x);
    }

    @Test
    public void mutateDoesNothing() {
        int n = c.getNodes();
        c.mutate(r);
        assertEquals(n, c.getNodes());
    }
}
