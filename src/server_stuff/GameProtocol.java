package server_stuff;

import gameobjects.Bullet;
import utils.GameController;
import utils.GameParser;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.HashMap;

public class GameProtocol implements Runnable {

    private Socket client;
    private BufferedReader inStream;
    private PrintWriter outStream;
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
            System.out.println("New gamer");
            inStream = new BufferedReader(new InputStreamReader(client.getInputStream()));
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(client.getOutputStream());
            outStream = new PrintWriter(client.getOutputStream(), true);

            GameParser gameParser = new GameParser(null, null);

            try {
                objectOutputStream.writeObject(gameParser);
                System.out.println("Sent");
                objectOutputStream.flush();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
//                    System.out.println("Couldn't contact the client");
            }
            Timer entitiesTimer = new Timer(1000, e -> {


            });
            entitiesTimer.start();

            String request;
            while((request = inStream.readLine()) != null) {
                // TODO command to hashmap
                System.out.println(request);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
