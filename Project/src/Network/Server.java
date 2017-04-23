package Network;

import Controller.Controller;
import Game.*;
import java.io.*;
import java.net.*;

import static Model.utils.GameUtils.*;

/**
 * Created by boyinzhang on 4/22/17.
 */
public class Server extends Thread  {
    /**
     * Runs the application. Pairs up clients that connect.
     */
    private ServerSocket listener;
    private ObjectInputStream[] input;
    private ObjectOutputStream[] output;

    public Server(int port)throws Exception {
        listener = new ServerSocket(port);
        output = new ObjectOutputStream[NUM_PLAYER];
        input = new ObjectInputStream[NUM_PLAYER];
    }

    private void gameInit() throws IOException {
        Game game = new Game();
        for(int i = 0; i <  NUM_PLAYER; i++){
            Socket server = listener.accept();
            System.out.printf("%d players connected\n",i+1);

            output[i] = new ObjectOutputStream(server.getOutputStream());
            output[i].writeObject(game);

            input[i] = new ObjectInputStream(server.getInputStream());
            try {
                String str = (String)input[i].readObject();
                System.out.println(str);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Game Starts");
    }



    public void gameExit() throws IOException {

        listener.close();

        System.out.println("Game Ends");
    }

    public void announceResult(int winner) throws IOException {

        for(int i = 0; i <  NUM_PLAYER; i++){

            if(i == winner)
                output[i].writeObject("WIN");
            else
                output[i].writeObject("LOSE");
        }

    }

    public void broadcastPlayers(Game updatedGame) throws IOException {

        for(int i = 0; i <  NUM_PLAYER; i++){
            output[i].writeObject("UPDATE");
            output[i].writeObject(updatedGame);
        }

    }

    @Override
    public void run() {

        System.out.println("Splendor is running");
        while(true){
            try {
                gameInit();
                //game loop
                int currentPlayer = 0;
                while (true) {
                    try {
                        //action
                        String request = (String) input[currentPlayer].readObject();
                        if (request.startsWith("EXIT")) {
                            //let other player continue playing until only one left
                            gameExit();
                            break;
                        }
                        if (request.startsWith("VICTORY")) {
                            //
                            announceResult(currentPlayer);
                            gameExit();
                            break;
                        }

                        if (request.startsWith("RESTART")) {
                            //add consensus function
                            Game newGame = new Game();
                            currentPlayer = 0;
                            broadcastPlayers(newGame);
                        }

                        if (request.startsWith("COLLECT") || request.startsWith("PURCHASE") || request.startsWith("RESERVE")) {
                            Game updatedGame = (Game) input[currentPlayer].readObject();
                            broadcastPlayers(updatedGame);
                        }

                        //next one;
                        currentPlayer = (currentPlayer + 1) % NUM_PLAYER;
                        output[currentPlayer].writeObject("MOVE");

                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                        break;
                    }
                }


            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
        System.out.println("Server closed");

    }


    public static void main(String [] args) {
        int port = (args.length == 0) ? 8080 : Integer.parseInt(args[1]);

        Thread t = null;
        try {
            t = new Server(port);
        } catch (Exception e) {
            e.printStackTrace();
        }
        t.start();

    }
}
