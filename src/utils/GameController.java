package utils;

import gameobjects.GameObject;
import gameobjects.Player;

import java.awt.*;
import java.util.ArrayList;

public class GameController {

    private Game model;

    public GameController(Game model) {
        this.model = model;
    }

    public void addEntity(GameObject gameObject) {
        model.addEntity(gameObject);
    }

    public ArrayList<GameObject> getEntities() {
        return model.getEntities();
    }

    public void update() {
        model.update();
    }

    public Player getPlayer() {
        return model.getPlayer();
    }

    public void checkCollision() {
        model.checkCollisions();
    }

    public void updateImages() {
        model.updateImages();
    }

    public Vector3d getSpawnPoint() {
        return model.getSpawnPoint();
    }
}
