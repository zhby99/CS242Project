package Network;

import Game.Game;
import View.BoardUI;

import java.net.*;
import java.io.*;

/**
 * Created by boyinzhang on 4/22/17.
 */
public class Client {

    private static int PORT = 8901;
    private Socket socket;

    private BufferedReader in = null;
    private PrintWriter out = null;
    private boolean connected = false;
    private Game game;
    private BoardUI gui;

    public Client(String serverAddress) throws Exception {

        // Setup networking

        socket = new Socket(serverAddress, PORT);
        System.out.println("Connected");
        InputStream is = socket.getInputStream();
        ObjectInputStream gameInputStream = new ObjectInputStream(is);
        game = (Game) gameInputStream.readObject();
        gameInputStream.close();
        is.close();



        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        //this.gui.updateByGame(game);




    }


    //main
    public static void main(String[] args) throws Exception {

        //while (true) {
        String serverAddress = (args.length == 0) ? "localhost" : args[1];
        Client client = new Client(serverAddress);

        //}
    }

}