package utils;

import gameobjects.*;

import java.net.Socket;
import java.util.*;

public class Game {

    protected Player player;
    protected Vector spawnPoint = new Vector(0,0,0);

    protected HashMap<Socket, Player> players = new HashMap<>();
    protected ArrayList<GameObject> entities = new ArrayList<>();

    Random random = new Random();

    public Game() {
        player = new Player(new Vector(0,0,0), 0);
    }

    public void init() {

        entities.add(new Campfire(new Vector(0, -130, 0), 150));
        entities.add(new Campfire(new Vector(100 + Platform.IMG_LENGTH * 3, -30, 0), 350));
        entities.add(new Tent(new Vector(150 + Platform.IMG_LENGTH * 3, -30, 0), 80));
        entities.add(new Campfire(new Vector(100 + Platform.IMG_LENGTH * 7, -230, 0), 150));

        entities.add(new EndGoal(new Vector(9 * Platform.IMG_LENGTH, -300, 0), 100));

        entities.add(new Platform(new Vector(0, -200, 0), Platform.IMG_LENGTH * 2));
        entities.add(new Platform(new Vector(Platform.IMG_LENGTH * 3, -100, 0), Platform.IMG_LENGTH * 4));
        entities.add(new Platform(new Vector(Platform.IMG_LENGTH * 7, -300, 0), Platform.IMG_LENGTH * 30));

    }

    public Player getPlayer() {
        return players.getOrDefault(null, player);
    }


    public Player getPlayer(Socket client) {
        return players.getOrDefault(client, null);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void addPlayer(Socket client) {
        Player play = new Player(new Vector(0,0,0), 50);
        entities.add(play);
        players.put(client, play);
    }

    public void removePlayer(Socket socket) {
        removeEntity(players.remove(socket));
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

    public void removeEntity(GameObject gameObject) {
        entities.remove(gameObject);
    }

    public void setSpawnPoint(Vector spawnPoint) {
        this.spawnPoint = new Vector(spawnPoint.getX(), spawnPoint.getY() + 50, 0);
    }

    public void update() {
        synchronized (entities) {

            spawnGhost();

            entities.sort(Comparator.comparingDouble(GameObject::getZ));

            entities.forEach(gameObject -> {
            if (gameObject instanceof Ghost) {
                ((Ghost) gameObject).followPlayer();
            }
                synchronized (this) {
                    gameObject.update();
                }
            });

            checkPlayerDeath();
            checkToDestroyed();
        }
    }

    public void updateImages() {
        synchronized (entities) {
            entities.forEach(GameObject::animate);
        }
    }

    private void spawnGhost() {
        int chance = random.nextInt(0, 20000);
        if (!players.isEmpty() && chance == 1) {
            Player randomPlayer = getPlayer(Utils.getRandomKey(players));
            Vector ghostSpawn = new Vector(randomPlayer.getPos());
            ghostSpawn.addX(random.nextDouble(1000, 1100));
            ghostSpawn.addY(random.nextDouble(-200, 200));
            entities.add(new Ghost(ghostSpawn, 0.3, 50, randomPlayer));
        }
    }

    public void checkCollisions() {
        Set<GameObject> toDestroy = new HashSet<>();

        synchronized (entities) {
            for (Map.Entry<Socket, Player> playerEntry : players.entrySet()) {
                playerEntry.getValue().setTouchingFloor(false);
                for (GameObject go: getEntities()) {

                    if (go instanceof Platform) {
                        playerEntry.getValue().collidePlatform(go.getCollisionBox());
                    }
                    if (go instanceof  Tent && playerEntry.getValue().getCollisionBox().intersects(go.getCollisionBox())) {
                        setSpawnPoint(go.getPos());
                    }
                    if (go instanceof Ghost && playerEntry.getValue().getCollisionBox().intersects(go.getCollisionBox())) {
                        playerEntry.getValue().die(spawnPoint);
                        toDestroy.add(go);
                    }
                }
            }

            for (GameObject go: toDestroy) {
                entities.remove(go);
            }
        }
    }

    private void checkPlayerDeath() {
        synchronized (players) {
            for (Map.Entry<Socket, Player> entry : players.entrySet()) {
                entry.getValue().outOfBounds(spawnPoint);
            }
        }
    }

    private void checkToDestroyed() {
        Set<GameObject> toBeDestroyed = new HashSet<>();
        synchronized (entities) {
            for (GameObject go: entities) {
                if (go instanceof Bullet) {
                    if (((Bullet) go).timeOut()) {
                        toBeDestroyed.add(go);
                    }
                    for (GameObject ghost: entities) {
                        if (ghost instanceof Ghost && go.getCollisionBox().intersects(ghost.getCollisionBox())) {
                            toBeDestroyed.add(ghost);
                            toBeDestroyed.add(go);
                        }
                    }
                }
            }
            for (GameObject go: toBeDestroyed) {
                entities.remove(go);
            }
        }
    }
}
