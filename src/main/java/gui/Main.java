package gui;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.State;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.Body;
import com.almasb.fxgl.settings.GameSettings;
import com.almasb.fxgl.app.StateChangeListener;
import facade.Facade;
import gui.auxclasses.CreatureFactory;
import gui.auxclasses.MuscleComponent;
import gui.auxclasses.SceneFactory;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class Main extends GameApplication {
    private int secondsPerCreature;
    private Facade f;
    private int numberOfCreatures;
    private ArrayList<Double> bestPerGen;

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(800);
        gameSettings.setHeight(600);
        gameSettings.setTitle("Creatures");
        bestPerGen = new ArrayList<>();
        
        // Parametros del programa
        secondsPerCreature = 5;
        numberOfCreatures = 10;
        // Fin parametros
        
        try {
        	FileWriter b = new FileWriter("best_log.txt");
        	FileWriter m = new FileWriter("mean_log.txt");
        	b.close();
        	m.close();
        }
        catch (IOException e) {
        	System.err.println("Error!");
        }
    }

    @Override
    protected void initGame() {
        Entity bg = SceneFactory.newBackground();
        Entity floor = SceneFactory.newFloor();
        Entity marker = SceneFactory.newMarker(0.0);

        getGameWorld().addEntities(bg, floor, marker);

        for (int i = 0; i < 20; i++)
            getGameWorld().addEntity(SceneFactory.staticMarker(i*800.0));

        f = new Facade(numberOfCreatures);

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
        labels.add(new Text(15, 480, "Generacion:"));
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
            e.removeFromWorld();
        }
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
        writeFitness(x);
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
        double center = nodes == 0? 0 : x / nodes;
        getGameState().doubleProperty("currentFitness").setValue(center);
        getGameScene().getViewport().setX(center - 400);
        for (Entity e : getGameWorld().getEntitiesByType(EntityTypes.MUSCLE)) {
            e.getComponent(MuscleComponent.class).flex();
        }
    }
    
    private void writeFitness(double[] f) {
    	try {
			FileWriter b = new FileWriter("best_log.txt", true);
			b.write(String.valueOf(f[0]));
			b.write('\n');
			b.close();
			FileWriter m = new FileWriter("mean_log.txt", true);
			m.write(String.valueOf(f[1]));
			m.write('\n');
			m.close();
		} catch (IOException e) {
			System.err.println("Error!");
		}
    }
}
