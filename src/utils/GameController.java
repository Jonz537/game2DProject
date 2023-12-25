package utils;

import gameobjects.*;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class GameController {

    private Game model;

    public GameController(Game model) {
        this.model = model;
    }

    public Player getPlayer() {
        return model.getPlayer();
    }

    public Player getPlayer(Socket client) {
        return model.getPlayer(client);
    }

    public void setPlayer(Player player) {
        model.setPlayer(player);
    }

    public void addPlayer(Socket socket) {
        model.addPlayer(socket);
    }

    public void removePlayer(Socket socket) {
        model.removePlayer(socket);
    }

    public HashMap<Socket, Player> getAllPlayers() {
        return model.players;
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
           ArrayList<GameObject> copy = new ArrayList<>(), syncEntities = new ArrayList<>(entities);

           for (GameObject go: syncEntities) {
               if (go instanceof Bullet) {
                   copy.add(new Bullet((Bullet) go));
               } else if (go instanceof Campfire) {
                   copy.add(new Campfire((Campfire) go));
               } else if (go instanceof Ghost) {
                   copy.add(new Ghost((Ghost) go));
               } else if (go instanceof Platform) {
                   copy.add(new Platform((Platform) go));
               } else if (go instanceof Player) {
                   copy.add(new Player((Player) go));
               } else if (go instanceof Tent) {
                   copy.add(new Tent((Tent) go));
               } else {
                   copy.add(new GameObject(go));
               }
       }
        return copy;
    }

    public void update() {
        model.update();
    }

    public void checkCollision() {
        model.checkCollisions();
    }

    public void updateImages() {
        model.updateImages();
    }

    public void updateSounds() {
        model.updateSounds();
    }
}
