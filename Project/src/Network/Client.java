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

    private int clientID;
    private String username;

    public Client(String serverAddress) throws Exception {

        // Setup networking
        socket = new Socket(serverAddress, PORT);
        System.out.println("Connected to " + socket.getRemoteSocketAddress());

        flowInput = new ObjectInputStream(socket.getInputStream());
        flowOutput = new ObjectOutputStream(socket.getOutputStream());

        String msg = (String)flowInput.readObject();
        System.out.println(msg);

        //get username from keyboard
        keyboardInput =new Scanner(System.in);
        String name = keyboardInput.nextLine();

        //send username back
        flowOutput.writeObject(name);

        //fetch id and game
        clientID = (Integer) flowInput.readObject();
        Game game = (Game) flowInput.readObject();
        controller = new Controller(game, flowOutput, clientID, name);

        //notify server the local game is ready
        flowOutput.writeObject(name+" initialized");

        //out.writeObject(game);
    }

    //gaming logic
    private void play() throws ClassNotFoundException, IOException {
        while(true){
            String response = null;
            try {
                response = (String) flowInput.readObject();
                if(response.startsWith("WIN")){
                    System.out.println("You win :)");
                    break;
                }
                //this player ties with the other players
                else if(response.startsWith ("TIE")){
                    //get the index of players
                    ArrayList<Integer> playerList = (ArrayList<Integer>) flowInput.readObject();
                    //remove duplicate of the player itself
                    playerList.remove(new Integer(clientID));
                    System.out.printf("You tie with:");
                    for (Integer aPlayerList : playerList) {
                        System.out.printf(" %s", controller.game.players[aPlayerList].getName());
                    }
                    System.out.println(" !");
                    break;
                }
                //this player loses
                else if(response.startsWith("LOSE")){
                    System.out.println("You lose :(");
                    break;
                }
                //server wants to update the game
                else if(response.startsWith("UPDATE")){
                    Game updatedGame = (Game) flowInput.readObject();
                    controller.game = updatedGame;
                    controller.boardUI.updateByGame(updatedGame);
                }
                //someone wants to start a new game
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