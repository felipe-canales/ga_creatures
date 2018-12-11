package gui;

import com.almasb.fxgl.entity.Entity;
import creature.Creature2;
import gui.auxclasses.CreatureFactory;
import gui.auxclasses.SceneFactory;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

public class MainTest extends Main {
    @Override
    protected void initGame() {
        Entity bg = SceneFactory.newBackground();
        Entity floor = SceneFactory.newFloor();
        Entity marker = SceneFactory.newMarker(0.0);

        getGameWorld().addEntities(bg, floor, marker);

        Creature2 c = new Creature2(new Random(0));

        getMasterTimer().runAtInterval(this::updateState, Duration.millis(20));
        ArrayList<Entity> creature = CreatureFactory.newCreature(c, getMasterTimer());
        for (Entity e : creature)
            getGameWorld().addEntity(e);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
