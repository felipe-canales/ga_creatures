package gui.auxclasses;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.time.Timer;
import com.almasb.fxgl.time.TimerAction;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import java.sql.SQLOutput;

public class MuscleComponent extends Component {
    private TimerAction stateChange;
    private double strength;
    private boolean state;
    private Entity node1;
    private Entity node2;

    public MuscleComponent(Timer timer, double time, double strength, Entity node1, Entity node2) {
        TimerAction t = timer.runAtInterval(this::change, Duration.seconds(time));
        this.stateChange = t;
        this.strength = strength;
        this.node1 = node1;
        this.node2 = node2;
    }

    public void flex() {
        double force = calculateStrength(node1, node2);
        Point2D direction = node1.getCenter().subtract(node2.getCenter()).normalize();
        double dirX = direction.getX();
        double dirY = direction.getY();
        PhysicsComponent p = node2.getComponent(PhysicsComponent.class);
        p.applyBodyForceToCenter(new Vec2(new Point2D(dirX, 0.0).multiply(force)));
        p.applyBodyForceToCenter(new Vec2(new Point2D(0.0, -dirY).multiply(force)));
        p = node1.getComponent(PhysicsComponent.class);
        p.applyBodyForceToCenter(new Vec2(new Point2D(-dirX, 0.0).multiply(force)));
        p.applyBodyForceToCenter(new Vec2(new Point2D(0.0, dirY).multiply(force)));

        /*Point2D direction2 = node2.getCenter().subtract(node1.getCenter()).normalize();
        node1.getComponent(PhysicsComponent.class).applyBodyForceToCenter(
                new Vec2(direction2.multiply(force)));*/
        //System.out.println("nuevos");
        //System.out.println(direction1);
        //System.out.println(direction2);
    }

    private void change() {
        state = !state;
    }

    private double calculateStrength(Entity node1, Entity node2) {
        double distance1 = node1.distance(node2);
        double defDistance = state? 150.0 : 100.0;
        return (distance1 - defDistance) * strength;
    }

    public void killAction() {
        stateChange.expire();
    }

    public boolean getState() {
        return state;
    }
}
