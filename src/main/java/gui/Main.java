package gui;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.Body;
import com.almasb.fxgl.settings.GameSettings;
import facade.Facade;
import gui.auxclasses.CreatureFactory;
import gui.auxclasses.MuscleComponent;
import gui.auxclasses.SceneFactory;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Map;

public class Main extends GameApplication {
    private int secondsPerCreature;
    private Facade f;

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(800);
        gameSettings.setHeight(600);
        gameSettings.setTitle("Creatures");
        secondsPerCreature = 5;
    }

    @Override
    protected void initGame() {
        Entity bg = SceneFactory.newBackground();
        Entity floor = SceneFactory.newFloor();
        Entity marker = SceneFactory.newMarker(0.0);

        getGameWorld().addEntities(bg, floor, marker);

        for (int i = 0; i < 20; i++)
            getGameWorld().addEntity(SceneFactory.staticMarker(i*800.0));

        f = new Facade(4);

        getMasterTimer().runAtInterval(this::updateState, Duration.millis(20));
        ArrayList<Entity> creature = CreatureFactory.newCreature(f.getCreature(), getMasterTimer());
        for (Entity e : creature)
            getGameWorld().addEntity(e);
        getMasterTimer().runOnceAfter(this::testCreature, Duration.seconds(secondsPerCreature));
    }

    @Override
    protected void initPhysics() {
        getPhysicsWorld().setGravity(0, 300);
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("gen", 0);
        vars.put("subject", 1);
        vars.put("currentFitness", 0.0);
        vars.put("bestFitness", 0.0);
        vars.put("meanFitness", 0.0);
    }

    @Override
    protected void initUI() {
        Font font = new Font(30);
        ArrayList<Text> labels = new ArrayList<>();
        labels.add(new Text(15, 480, "Generación:"));
        labels.add(new Text(15, 550, "Sujeto:"));
        labels.add(new Text(270, 550, "Fitness:"));
        labels.add(new Text(520, 480, "Mejor:"));
        labels.add(new Text(520, 550, "Promedio:"));

        Text t = new Text(40, 510, "");
        t.textProperty().bind(getGameState().intProperty("gen").asString());
        labels.add(t);
        t = new Text(40, 580, "");
        t.textProperty().bind(getGameState().intProperty("subject").asString());
        labels.add(t);
        t = new Text(295, 580, "");
        t.textProperty().bind(getGameState().doubleProperty("currentFitness").asString());
        labels.add(t);
        t = new Text(545, 510, "");
        t.textProperty().bind(getGameState().doubleProperty("bestFitness").asString());
        labels.add(t);
        t = new Text(545, 580, "");
        t.textProperty().bind(getGameState().doubleProperty("meanFitness").asString());
        labels.add(t);

        labels.forEach(l -> {
            l.setFont(font);
            getGameScene().addUINode(l);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void testCreature() {
        f.setFitness(getGameState().doubleProperty("currentFitness").doubleValue());
        for (Entity e : getGameWorld().getEntitiesByType(EntityTypes.MUSCLE)) {
            e.getComponent(MuscleComponent.class).killAction();
            e.removeFromWorld();
        }
        for (Entity e : getGameWorld().getEntitiesByType(EntityTypes.NODE)) {
            getPhysicsWorld().getJBox2DWorld().destroyBody(e.getComponent(PhysicsComponent.class).getBody());
            e.removeComponent(PhysicsComponent.class);
            e.removeFromWorld();
        }
        //getPhysicsWorld().getJBox2DWorld().setContinuousPhysics(true);
        for (Entity e : getGameWorld().getEntitiesByType(EntityTypes.MARKER)) {
            e.removeFromWorld();
        }
        if (f.finishedGen())
            newGen();
        else
            updateUI();
        getGameWorld().addEntity(SceneFactory.newMarker(
                getGameState().doubleProperty("bestFitness").doubleValue()));
        ArrayList<Entity> creature = CreatureFactory.newCreature(f.getCreature(), getMasterTimer());
        for (Entity e : creature)
            getGameWorld().addEntity(e);
        getMasterTimer().runOnceAfter(this::testCreature, Duration.seconds(secondsPerCreature));
    }

    private void newGen() {
        double[] x = f.newGen();
        getGameState().intProperty("subject").setValue(1);
        getGameState().intProperty("gen").setValue(
                getGameState().intProperty("gen").intValue() + 1);
        getGameState().doubleProperty("currentFitness").setValue(0);
        getGameState().doubleProperty("bestFitness").setValue(x[0]);
        getGameState().doubleProperty("meanFitness").setValue(x[1]);
    }

    private void updateUI() {
        getGameState().intProperty("subject").setValue(
                getGameState().intProperty("subject").intValue() + 1);
    }

    protected void updateState() {
        double x = 0.0;
        int nodes = 0;
        for (Entity e : getGameWorld().getEntitiesByType(EntityTypes.NODE)) {
            x += e.getCenter().getX();
            nodes++;
        }
        getGameState().doubleProperty("currentFitness").setValue(x);
        getGameScene().getViewport().setX((x / nodes) - 400);
        for (Entity e : getGameWorld().getEntitiesByType(EntityTypes.MUSCLE)) {
            e.getComponent(MuscleComponent.class).flex();
        }
    }
}
