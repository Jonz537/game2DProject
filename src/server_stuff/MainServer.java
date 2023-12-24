package server_stuff;

import utils.Game;
import utils.GameController;
import utils.GameParser;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainServer {

    private static Game model;
    private static GameController controller;


    // Threads: main, update, animation, n * client connected


    public static void main(String[] args) {
        model = new Game();
        model.init();
        controller = new GameController(model);

        java.util.Timer physics = new java.util.Timer();
        TimerTask updateTask = new TimerTask() {
            @Override
            public void run() {
                controller.update();
                controller.checkCollision();
            }
        };
        physics.scheduleAtFixedRate(updateTask, 0, 2);

        java.util.Timer animations = new java.util.Timer();
        TimerTask animationTask = new TimerTask() {
            @Override
            public void run() {
                controller.updateImages();
            }
        };
        animations.scheduleAtFixedRate(animationTask, 0, 150);


        ExecutorService executor = Executors.newCachedThreadPool();

        System.out.println("Server starting");
        try (ServerSocket server = new ServerSocket(1235)) {
            while (true) {
                executor.submit(new GameProtocol(server.accept(), controller));
            }
        } catch (IOException e) {
            System.out.println("Couldn't connect to the client");
            e.printStackTrace();
        }

    }
}
