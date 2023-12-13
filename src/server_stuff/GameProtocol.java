package server_stuff;

import gameobjects.Bullet;
import utils.GameController;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
            outStream = new PrintWriter(client.getOutputStream(), true);

            Timer entitiesTimer = new Timer(2, e -> {
                // TODO send stuff
                controller.getPlayer();
                controller.getEntities();
            });

            String request;
            while((request = inStream.readLine()) != null) {
                // TODO receive stuff
                System.out.println(request);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
