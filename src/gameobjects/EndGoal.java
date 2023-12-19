package gameobjects;

import utils.Vector;

import java.awt.geom.Rectangle2D;

public class EndGoal extends GameObject{
    public EndGoal(Vector pos, int size) {
        super(pos, size);

        collisionBox = new Rectangle2D.Double(pos.getX(), pos.getY(), size, size);
        imageRef = "./assets/goal.png";
    }


}
