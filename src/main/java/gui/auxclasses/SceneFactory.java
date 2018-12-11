package gui.auxclasses;

import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.RenderLayer;
import com.almasb.fxgl.physics.PhysicsComponent;
import gui.EntityTypes;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SceneFactory {
    public static Entity newBackground() {
        return Entities.builder()
                .at(-1000, 0)
                .viewFromNode(new Rectangle(100000, 600, Color.SKYBLUE))
                .renderLayer(RenderLayer.BACKGROUND)
                .build();
    }

    public static Entity newFloor() {
        return Entities.builder()
                .at(-1000, 450)
                .type(EntityTypes.WALL)
                .viewFromNodeWithBBox(new Rectangle(100000, 150, Color.GREEN))
                .with(new PhysicsComponent())
                .build();
    }

    public static Entity newMarker(double pos) {
        return Entities.builder()
                .at(pos, 420)
                .type(EntityTypes.MARKER)
                .viewFromNode(new Rectangle(5, 30, Color.WHITE))
                .build();
    }

    public static Entity staticMarker(double pos) {
        return Entities.builder()
                .at(pos, 420)
                .viewFromNode(new Rectangle(5, 30, Color.ORANGE))
                .build();
    }
}
