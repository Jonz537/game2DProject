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
    private ObjectOutputStream objectOutputStream;

    private HashMap<String, Runnable> commandMap = new HashMap<>();

    {
        commandMap.put("rx", () -> controller.getPlayer(client).accelerate());
        commandMap.put("sx", () -> controller.getPlayer(client).decelerate());
        commandMap.put("jump", () -> controller.getPlayer(client).jump());
        commandMap.put("ball", () -> controller.addEntity(new Bullet(controller.getPlayer(client).getPos(), 25, 0.5, 0, 0)));
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
            System.out.println("new gamer:" + client.getInetAddress() + " " + client.getLocalPort());
            System.out.println(client);
            controller.addPlayer(client);
            System.out.println(controller.getAllPlayers());

            inStream = new BufferedReader(new InputStreamReader(client.getInputStream()));

            Timer timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    try {
                        synchronized (this) {
                            GameParser gameParser = new GameParser(new Player(controller.getPlayer(client)),
                                    controller.createEntitiesCopy(controller.getEntities()));
                            objectOutputStream.writeObject(gameParser);
                        }
                        objectOutputStream.flush();

                    } catch (IOException e) {
                        System.out.println("Client disconnected abruptly");
                        this.cancel();
                    }
                }
            };
            timer.scheduleAtFixedRate(timerTask, 0, 5);

            String message;
            while ((message = inStream.readLine()) != null) {
                new Thread(commandMap.get(message)).start();
            }

        } catch (IOException e) {
            System.out.println("Client disconnected abruptly");
            controller.removePlayer(client);
            try {
                client.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
