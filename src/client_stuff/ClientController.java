package client_stuff;

import gameobjects.Player;
import utils.Game;
import utils.GameController;
import utils.GameParser;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class ClientController extends GameController {

    private PrintWriter outStream;
    private ObjectInputStream objectInputStream;
    private Socket socket;

    public ClientController(Game model) {
        super(model);
        try {
            socket = new Socket("localhost", 1234);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void connect() {

        try {
            Timer timer = new Timer();

            objectInputStream = new ObjectInputStream(socket.getInputStream());
            outStream = new PrintWriter(socket.getOutputStream());


            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    try {
//                        GameParser gameParser = (GameParser) objectInputStream.readObject();
//                        System.out.println(gameParser.getPlayer());
//                        setPlayer(gameParser.getPlayer());
//                        setEntities(gameParser.getEntities());
                        System.out.println(objectInputStream.readObject());
                    } catch (IOException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            };
            timer.scheduleAtFixedRate(timerTask, 0, 5);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void sendCommand(String command) {
        outStream.print(command);
        System.out.println(command);
    }
}
