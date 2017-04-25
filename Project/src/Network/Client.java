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
import java.util.Scanner;

/**
 * Created by boyinzhang on 4/22/17.
 */
public class Client {

    private static int PORT = 8080;
    private Socket socket;


    Scanner keyboardInput = null;

    private ObjectInputStream flowInput = null;
    private ObjectOutputStream flowOutput = null;
    private Controller controller;

    private int id;
    private String username;

    public Client(String serverAddress) throws Exception {

        // Setup networking
        socket = new Socket(serverAddress, PORT);
        System.out.println("Connected to " + socket.getRemoteSocketAddress());

        flowInput = new ObjectInputStream(socket.getInputStream());
        flowOutput = new ObjectOutputStream(socket.getOutputStream());


        String msg = (String)flowInput.readObject();
        System.out.println(msg);

        keyboardInput =new Scanner(System.in);
        String name = keyboardInput.nextLine();

        //name interface
        flowOutput.writeObject(name);

        id = (Integer) flowInput.readObject();
        Game game = (Game) flowInput.readObject();
        controller = new Controller(game, flowOutput, id, name);

        //name interface
        flowOutput.writeObject(name+" initialized");

        //out.writeObject(game);
    }

    private void play() throws ClassNotFoundException, IOException {
        while(true){
            String response = null;
            try {
                response = (String) flowInput.readObject();
                if(response.startsWith("WIN")){
                    System.out.println("You win :)");
                    break;
                }
                else if(response.startsWith ("TIE")){
                    ArrayList<Integer> playerList = (ArrayList<Integer>) flowInput.readObject();
                    playerList.remove(new Integer(id));
                    System.out.printf("You tie with:");
                    for (Integer aPlayerList : playerList) {
                        System.out.printf(" %d", aPlayerList);
                    }
                    System.out.println(" !");
                    break;
                }
                else if(response.startsWith("LOSE")){
                    System.out.println("You lose :(");
                    break;
                }
                else if(response.startsWith("UPDATE")){
                    Game updatedGame = (Game) flowInput.readObject();
                    controller.game = updatedGame;
                    controller.boardUI.updateByGame(updatedGame);
                }
                else if(response.startsWith("VOTE")){
                    controller.voteForNewGame();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        socket.close();
        System.out.println("Game Over");
    }


    //main
    public static void main(String[] args) throws Exception {

        String serverAddress = (args.length == 0) ? "localhost" : args[1];
        Client client = new Client(serverAddress);
        client.play();

    }

}