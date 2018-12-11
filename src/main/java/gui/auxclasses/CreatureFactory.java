package gui.auxclasses;

import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.PhysicsComponent;

import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import com.almasb.fxgl.time.Timer;
import creature.Creature;
import gui.EntityTypes;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class CreatureFactory {
    private static double[] xPos = {0.0, 50.0, 50.0, 0.0, -50.0, -50.0, -50.0, 0.0, 50.0};
    private static double[] yPos = {300.0, 300.0, 250.0, 250.0, 250.0, 300.0, 350.0, 350.0, 350.0};
    private static Color[] colors = {Color.WHITE, Color.PINK, Color.RED, Color.DARKRED, Color.BLACK};

    public static ArrayList<Entity> newCreature(Creature c, Timer masterTimer) {
        ArrayList<Entity> nodes = new ArrayList<>();
        ArrayList<Entity> muscles = new ArrayList<>();
        int x = 0;
        for (double d : c.getFrictions()) {
            nodes.add(newNode(d, x++));
        }
        for (int i = 0; i < c.getNodes(); i++) {
            for (int j = 0; j < i; j++) {
                muscles.add(newMuscle(nodes, i, j, masterTimer,
                        c.getTimes().get(i).get(j), c.getStrengths().get(i).get(j)));
            }
        }
        nodes.addAll(muscles);
        return nodes;
    }

    private static Entity newMuscle(ArrayList<Entity> nodes, int i, int j, Timer timer, double time, double strength) {
        MuscleComponent mc = new MuscleComponent(timer, time, strength, nodes.get(i), nodes.get(j));
        return Entities.builder()
                .type(EntityTypes.MUSCLE)
                .with(mc)
                .build();
    }

    private static Entity newNode(double friction, int i) {
        PhysicsComponent pc = new PhysicsComponent();
        pc.setBodyType(BodyType.DYNAMIC);
        pc.setFixtureDef(new FixtureDef().restitution(0).friction((float) friction).density(1.2f));
        return Entities.builder()
                .at(xPos[i], yPos[i])
                .type(EntityTypes.NODE)
                .with(pc)
                .viewFromNodeWithBBox(new Circle(20.0, colors[(int)(friction * 5)]))
                .build();
    }
}
