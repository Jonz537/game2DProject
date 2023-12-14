package utils;

import gameobjects.GameObject;
import gameobjects.Player;

import java.net.Socket;
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

    public void setEntities(ArrayList<GameObject> entities) {
        model.setEntities(entities);
    }

    public ArrayList<GameObject> createEntitiesCopy(ArrayList<GameObject> entities) {
//        ArrayList<GameObject> copy = new ArrayList<>();
//        for (GameObject go: entities) {
//            copy.add(new GameObject(go));
//        }
//        return copy;
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

    public Vector getSpawnPoint() {
        return model.getSpawnPoint();
    }

    public void addPlayer(Socket socket) {
        model.addPlayer(socket);
    }

    public void setPlayer(Player player) {
        model.setPlayer(player);
    }

    public Player getPlayer(Socket client) {
        return model.getPlayer(client);
    }
}
