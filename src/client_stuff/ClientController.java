package client_stuff;

import utils.Game;
import utils.GameController;
import utils.GameParser;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class ClientController extends GameController {

    private Socket socket;
    private BufferedReader inStream;
    private PrintWriter outStream;

    public ClientController(Game model) {
        super(model);
    }

    public void connect() {
        try {
            socket = new Socket("localhost", 1234);
            inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            outStream = new PrintWriter(socket.getOutputStream(), true);

            try {
                GameParser gameData = (GameParser) objectInputStream.readObject();
                System.out.println(gameData);
            } catch (IOException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
//                    System.out.println("Error receiving the file");
            }

            Timer entitiesTimer = new Timer(1000, e -> {

            });
            entitiesTimer.start();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendCommand(String command) {
        outStream.print(command);
        System.out.println(command);
    }


}
