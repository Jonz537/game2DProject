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
            int banana = 0;

            Timer timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    try {
//                        GameParser gameParser = new GameParser(controller.getPlayer(client), controller.getEntities());
//                        objectOutputStream.writeObject(gameParser);

                        // il client riesce ricevere perfettamente dati primitivi (tipo player.getY());
//                        objectOutputStream.writeObject(controller.getPlayer(client).getY());
//                        System.out.println(controller.getPlayer(client).getY());

                        // modifica i valori dei Vector3d (sembra mantenere il segno), quando il valore
                        // è 0 i valori vengono consegnati correttamente
//                        objectOutputStream.writeObject(controller.getPlayer(client).getPos());
//                        System.out.println(controller.getPlayer(client).getPos());

                        // Rectangle2D.Double funziona correttamente
//                        objectOutputStream.writeObject(controller.getPlayer(client).getCollisionBox());
//                        System.out.println(controller.getPlayer(client).getCollisionBox());

                        // sembra che l'unico problema risieda nell'invio di Vector3d (classe creata da me)
                        // non ho la minima idea del perché (suppongo c'entri Serializable)
                        objectOutputStream.flush();

                    } catch (IOException e) {
                        System.out.println("Client disconnected abruptly");
                    }
                }
            };
            timer.scheduleAtFixedRate(timerTask, 0, 5);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
