package utils;

import gameobjects.GameObject;
import gameobjects.Player;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class GameParser implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    //TODO fix serializable images

    public Player player;
    public ArrayList<GameObject> entities;

    public GameParser(Player player, ArrayList<GameObject> entities) {
        this.player = player;
        this.entities = new ArrayList<>(entities);
    }

    @Override
    public String toString() {
        return "GameParser{" +
                "player=" + player +
                ", entities=" + entities +
                '}';
    }
}
