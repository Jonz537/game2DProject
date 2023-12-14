package server_stuff;

import gameobjects.Bullet;
import utils.GameController;
import utils.GameParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

public class GameProtocol implements Runnable {

    private Socket client;
    private BufferedReader inStream;
    private GameController controller;

    private static HashMap<String, Runnable> commandMap = new HashMap<>();

    {
        commandMap.put("rx", () -> controller.getPlayer().accelerate());
        commandMap.put("dx", () -> controller.getPlayer().decelerate());
        commandMap.put("jump", () -> controller.getPlayer().jump());
        commandMap.put("ball", () -> controller.addEntity(new Bullet(controller.getPlayer().getPos(), 25, 10, 10, 0.05)));
    }

    public GameProtocol(Socket client, GameController controller) {
        this.client = client;
        this.controller = controller;
    }

    @Override
    public void run() {
        try {
            System.out.println("new gamer");
            inStream = new BufferedReader(new InputStreamReader(client.getInputStream()));
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(client.getOutputStream());
            GameParser gameParser = new GameParser(controller.getPlayer(), controller.getEntities());

            objectOutputStream.writeObject(gameParser);
            objectOutputStream.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
