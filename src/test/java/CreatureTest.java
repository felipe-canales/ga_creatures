import creature.Creature;
import creature.CreatureVariable;
import org.junit.*;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.*;

public class CreatureTest {
    private Creature c;
    private Random r;

    @Before
    public void setUp() {
        r = new Random(0);
        c = new CreatureVariable(r);
    }

    @Test
    public void constructor() {
        Creature c2 = new CreatureVariable(c);
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
        int n = c.getNodes();
        ArrayList<ArrayList<Double>> s = c.getStrengths();
        assertEquals(n, s.size());
        int x = 0;
        for (ArrayList<Double> a : s)
            assertEquals(x++, a.size());
        assertEquals(n, x);
    }

    @Test
    public void consistencyTime() {
        int n = c.getNodes();
        ArrayList<ArrayList<Double>> t = c.getTimes();
        assertEquals(n, t.size());
        int x = 0;
        for (ArrayList<Double> a : t)
            assertEquals(x++, a.size());
        assertEquals(n, x);
    }

    @Test
    public void mutateDoesNothing() {
        int n = c.getNodes();
        c.mutate(r);
        assertEquals(n, c.getNodes());
    }

    @Test
    public void mutateAddsNode() {
        int n = c.getNodes();
        r.nextInt();
        c.mutate(r);
        assertEquals(n + 1, c.getNodes());
        assertEquals(n + 1, c.getFrictions().size());
        assertEquals(n + 1, c.getStrengths().size());
        assertEquals(n + 1, c.getTimes().size());
    }

    @Test
    public void mutateRemovesNode() {
        int n = c.getNodes();
        r.nextInt();
        r.nextInt();
        r.nextInt();
        c.mutate(r);
        assertEquals(n == 2? 2 : n - 1, c.getNodes());
    }

}
