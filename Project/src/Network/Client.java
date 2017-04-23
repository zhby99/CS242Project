package Network;

import Controller.Controller;
import Game.Game;
import Model.*;
import View.*;

import java.lang.management.ManagementFactory;
import java.lang.reflect.Array;
import java.net.*;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by boyinzhang on 4/22/17.
 */
public class Client {

    private static int PORT = 8080;
    private Socket socket;

    private ObjectInputStream in = null;
    private ObjectOutputStream out = null;
    private Controller controller;

    public Client(String serverAddress) throws Exception {

        // Setup networking
        socket = new Socket(serverAddress, PORT);
        System.out.println("Connected to " + socket.getRemoteSocketAddress());

        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());
        Game game = (Game) in.readObject();
        controller = new Controller(game, out);

        //name interface
        out.writeObject(ManagementFactory.getRuntimeMXBean().getName()+" initialized");

        //out.writeObject(game);
    }

    public void play() throws ClassNotFoundException {
        while(true){
            String response = null;
            try {
                response = (String) in.readObject();
                if(response.startsWith("WIN")){
                    //out.writeObject("EXIT");
                    System.out.println("You win :)");
                    socket.close();
                    break;
                }
                if(response.startsWith("TIE")){
                    //out.writeObject("EXIT");
                    ArrayList<String> playerList = (ArrayList<String>) in.readObject();
                    System.out.printf("You tie with:");
                    for(int i = 0; i<playerList.size();i++){
                        System.out.printf(" %s",playerList.get(i));
                    }
                    System.out.println("!");
                    socket.close();
                    break;
                }
                if(response.startsWith("LOSE")){
                    //out.writeObject("EXIT");
                    System.out.println("You lose :(");
                    socket.close();
                    break;
                }
                if(response.startsWith("MOVE")){

                    //enable all buttons
                }
                if(response.startsWith("UPDATE")){
                    Game updatedGame = (Game) in.readObject();
                    controller.game = updatedGame;
                    controller.boardUI.updateByGame(updatedGame);
                }

                //socket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        System.out.println("Game Over");
    }


    //main
    public static void main(String[] args) throws Exception {

        String serverAddress = (args.length == 0) ? "localhost" : args[1];
        Client client = new Client(serverAddress);
        client.play();

    }

}