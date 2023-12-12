package utils;

import gameobjects.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Game {

    protected Player player;
    protected ArrayList<GameObject> entities = new ArrayList<>();
    protected Vector3d spawnPoint = new Vector3d(0,0,0);

    public Game() {
        player = new Player(spawnPoint, 50);
        entities.add(new Fire(new Vector3d(0, -130, 0), 150));

        entities.add(new Fire(new Vector3d(100 + Platform.IMG_LENGHT * 3, -30, 0), 350));
        entities.add(new Tent(new Vector3d(150 + Platform.IMG_LENGHT * 3, -30, 0), 80));

        entities.add(new Fire(new Vector3d(100 + Platform.IMG_LENGHT * 7, -230, 0), 150));
        entities.add(player);
        entities.add(new Platform(new Vector3d(0, -200, 0), Platform.IMG_LENGHT * 2));
        entities.add(new Platform(new Vector3d(Platform.IMG_LENGHT * 3, -100, 0), Platform.IMG_LENGHT * 4));
        entities.add(new Platform(new Vector3d(Platform.IMG_LENGHT * 7, -300, 0), Platform.IMG_LENGHT * 7));
    }

    public ArrayList<GameObject> getEntities() {
        return entities;
    }

    public void addEntity(GameObject gameObject) {
        entities.add(gameObject);
    }

    public void update() {
        entities.forEach(GameObject::update);
        checkPlayerDeath();
        checkToDestroyed();
    }

    private void checkPlayerDeath() {
        for (GameObject go: entities) {
            if (go instanceof Player) {
                if (go.getY() < -600) {
                    go.setVelY(0);
                    go.setPos(spawnPoint);
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
        // TODO fix collision

        boolean playerFloor = false;
        for (GameObject go: getEntities()) {
            if (go instanceof Platform && player.getCollisionBox().intersects(go.getCollisionBox())
            && go.getCollisionBox().getY() + go.getCollisionBox().getHeight() < player.getY()) {
                player.setVelY(0);
                player.accY = 0;
                playerFloor = true;
                break;
            }
            if (go instanceof  Tent && player.getCollisionBox().intersects(go.getCollisionBox())) {
                setSpawnPoint(go.getPos());
            }
        }

        if (!playerFloor) {
            player.accY = 0.2;
        }
    }

    public void updateImages() {
        entities.forEach(GameObject::animate);
    }

    public Player getPlayer() {
        return player;
    }

    public Vector3d getSpawnPoint() {
        return spawnPoint;
    }

    public void setSpawnPoint(Vector3d spawnPoint) {
        this.spawnPoint = new Vector3d(spawnPoint.getX(), spawnPoint.getY() + 50, 0);
    }
}
