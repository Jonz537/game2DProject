package server_stuff;

import utils.Game;
import utils.GameController;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainServer {

    private static Game model;
    private static GameController controller;

    public static void main(String[] args) {
        model = new Game();
        controller = new GameController(model);

        ExecutorService executor = Executors.newCachedThreadPool();

        System.out.println("Server starting");
        try (ServerSocket server = new ServerSocket(1234)) {
            while (true) {
                executor.submit(new GameProtocol(server.accept(), controller));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }




}
