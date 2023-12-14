package client_stuff;

import utils.Game;
import utils.GameController;
import utils.GameParser;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientController extends GameController {

    private PrintWriter outStream;

    public ClientController(Game model) {
        super(model);
    }

    public void connect() {
        try (Socket socket = new Socket("localhost", 1234)) {
            outStream = new PrintWriter(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

            GameParser gameParser = (GameParser) objectInputStream.readObject();
//            System.out.println(gameParser);
            setPlayer(gameParser.getPlayer());
            setEntities(gameParser.getEntities());

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendCommand(String command) {
        outStream.print(command);
        System.out.println(command);
    }
}
