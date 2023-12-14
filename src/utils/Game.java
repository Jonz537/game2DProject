package utils;

import gameobjects.*;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Game {

    protected Player player;
    protected HashMap<Socket, Player> players = new HashMap<>();
    protected ArrayList<GameObject> entities = new ArrayList<>();
    protected Vector spawnPoint = new Vector(0,0,0);

    public Game() {
        player = new Player(new Vector(0,0,0), 0);
    }

    public void init() {

        entities.add(new Campfire(new Vector(0, -130, 0), 150));
        entities.add(new Campfire(new Vector(100 + Platform.IMG_LENGTH * 3, -30, 0), 350));
        entities.add(new Tent(new Vector(150 + Platform.IMG_LENGTH * 3, -30, 0), 80));
        entities.add(new Campfire(new Vector(100 + Platform.IMG_LENGTH * 7, -230, 0), 150));

        entities.add(new Ghost(new Vector(100, 100, 0), 1, 50));

        entities.add(new Platform(new Vector(0, -200, 0), Platform.IMG_LENGTH * 2));
        entities.add(new Platform(new Vector(Platform.IMG_LENGTH * 3, -100, 0), Platform.IMG_LENGTH * 4));
        entities.add(new Platform(new Vector(Platform.IMG_LENGTH * 7, -300, 0), Platform.IMG_LENGTH * 7));

    }

    public Player getPlayer() {
        return players.getOrDefault(null, player);
    }

    public Player getPlayer(Socket client) {
        return players.get(client);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void addPlayer(Socket client) {
        Player play = new Player(new Vector(0,0,0), 50);
        entities.add(play);
        players.put(client, play);
    }

    public ArrayList<GameObject> getEntities() {
        return entities;
    }

    public void setEntities(ArrayList<GameObject> entities) {
        this.entities = entities;
    }

    public void addEntity(GameObject gameObject) {
        entities.add(gameObject);
    }

    public void update() {
//        entities.sort();
        entities.forEach(gameObject -> {
            if (gameObject instanceof Ghost) {
                ((Ghost) gameObject).followPlayer(getPlayer());
            }
            gameObject.update();
        });
        checkPlayerDeath();
        checkToDestroyed();
    }

    private void checkPlayerDeath() {
        for (GameObject go: entities) {
            if (go instanceof Player) {
                if (go.getY() < -600) {
                    go.setVelY(0);
                    ((Player) go).die(spawnPoint);
                }
            }
        }
    }

    private void checkToDestroyed() {
        Set<Bullet> toBeDestroyed = new HashSet<>();
        for (GameObject go: entities) {
            if (go instanceof Bullet && ((Bullet) go).timeOut()) {
                toBeDestroyed.add((Bullet) go);
            }
        }

        for (Bullet bullet: toBeDestroyed) {
            entities.remove(bullet);
        }

    }

    public void checkCollisions() {
        Set<GameObject> toDestroy = new HashSet<>();

        player.setTouchingFloor(false);
        for (GameObject go: getEntities()) {
            //TODO collision left right
            if (go instanceof Platform && player.getCollisionBox().intersects(go.getCollisionBox())) {

                if (player.getVelY() <= 0) {
                    player.setVelY(0);
                    player.setTouchingFloor(true);
                } else {
                    player.setY(player.getY() - player.getVelY());
                }
            }
            if (go instanceof  Tent && player.getCollisionBox().intersects(go.getCollisionBox())) {
                setSpawnPoint(go.getPos());
            }
            if (go instanceof Ghost && player.getCollisionBox().intersects(go.getCollisionBox())) {
                player.die(spawnPoint);
                toDestroy.add(go);
            }
        }

        for (GameObject go: toDestroy) {
            entities.remove(go);
        }
    }

    public void updateImages() {
        entities.forEach(GameObject::animate);
    }

    public Vector getSpawnPoint() {
        return spawnPoint;
    }

    public void setSpawnPoint(Vector spawnPoint) {
        this.spawnPoint = new Vector(spawnPoint.getX(), spawnPoint.getY() + 50, 0);
    }
}
