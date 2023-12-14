package server_stuff;

import gameobjects.Bullet;
import gameobjects.GameObject;
import gameobjects.Player;
import utils.GameController;
import utils.GameParser;
import utils.Vector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class GameProtocol implements Runnable {

    private Socket client;
    private BufferedReader inStream;
    private GameController controller;
    ObjectOutputStream objectOutputStream;

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
        try {
            objectOutputStream = new ObjectOutputStream(client.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void run() {
        try {
            System.out.println("new gamer");
            controller.addPlayer(client);

            inStream = new BufferedReader(new InputStreamReader(client.getInputStream()));

            Timer timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    try {
                        GameParser gameParser = new GameParser(new Player(controller.getPlayer(client)),
                                new ArrayList<>(controller.getEntities()));
                        objectOutputStream.writeObject(gameParser);
                        objectOutputStream.flush();

                    } catch (IOException e) {
                        System.out.println("Client disconnected abruptly");
                        this.cancel();
                        try {
                            client.close();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            };
            timer.scheduleAtFixedRate(timerTask, 0, 5);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
