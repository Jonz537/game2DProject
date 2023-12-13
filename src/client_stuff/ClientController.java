package client_stuff;

import utils.Game;
import utils.GameController;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
            outStream = new PrintWriter(socket.getOutputStream(), true);

            Timer entitiesTimer = new Timer(2, e -> {
                // TODO receive stuff
            });

        } catch (IOException e) {
            // TODO handle exceptions
            throw new RuntimeException(e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void sendCommand(String command) {
        outStream.print(command);
        System.out.println(command);
    }


}
