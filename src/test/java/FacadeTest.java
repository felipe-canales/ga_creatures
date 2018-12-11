import facade.Facade;
import org.junit.*;
import static org.junit.Assert.*;

public class FacadeTest {
    private Facade f;

    @Before
    public void setUp() {
        f = new Facade(99);
    }

    @Test
    public void size() {
        int i = 0;
        while (!f.finishedGen()) {
            f.getCreature();
            f.setFitness(0);
            i++;
        }
        assertEquals(99, i);
    }

    @Test
    public void sizeAfter() {
        f.newGen();
        int i = 0;
        while (!f.finishedGen()) {
            f.getCreature();
            f.setFitness(0);
            i++;
        }
        assertEquals(99, i);
    }
}
