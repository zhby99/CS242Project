package Network;

import Controller.Controller;
import Game.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

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

        for(int i = 0; i <  NUM_PLAYER; i++){
            Socket server = listener.accept();
            System.out.printf("%d players connected\n",i+1);

            output[i] = new ObjectOutputStream(server.getOutputStream());
            input[i] = new ObjectInputStream(server.getInputStream());

        }
    }

    private void gameInit() throws IOException {
        Game game = new Game();

        gameHelper(game);
    }

    private void gameHelper(Game game) throws IOException {
        for(int i = 0; i <  NUM_PLAYER; i++) {
            output[i].writeObject(i);
            output[i].writeObject(game);
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

    public void announceResult(ArrayList<Integer>  winners) throws IOException {

        for(int i = 0; i <  NUM_PLAYER; i++) {

            if (winners.contains(i)){
                if (winners.size() == 1)
                    output[i].writeObject("WIN");
                else {
                    //reference
                    //winners.remove(new Integer(i));
                    output[i].writeObject("TIE");
                    output[i].writeObject(winners);
                }
            }
            else
                output[i].writeObject("LOSE");
        }

    }

    private void askNewGame() throws IOException{
        for(int i = 0; i < NUM_PLAYER; i++){
            output[i].writeObject("VOTE");
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
        //while(true){
            try {
                gameInit();
                //game loop
                int currentPlayer = 0;
                int vote = 0;
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
                            Game updatedGame = (Game) input[currentPlayer].readObject();
                            broadcastPlayers(updatedGame);

                            ArrayList<Integer> winners = new ArrayList<>();
                            winners.add(currentPlayer);

                            for (int nextPlayer = currentPlayer + 1; nextPlayer < NUM_PLAYER; nextPlayer++) {
                                //output[nextPlayer].writeObject("MOVE");
                                request = (String) input[nextPlayer].readObject();
                                updatedGame = (Game) input[nextPlayer].readObject();
                                if (request.startsWith("VICTORY")) {
                                    winners.add(nextPlayer);
                                }
                                broadcastPlayers(updatedGame);
                            }
                            announceResult(winners);
                            gameExit();
                            break;
                        }

                        if (request.startsWith("RESTART")) {
                            //add consensus function
                            input[currentPlayer].readObject();
                            askNewGame();

                            if (request.startsWith("AGREE")) {
                                //flush input stream
                                input[currentPlayer].readObject();
                                vote += 1;
                                if (vote == NUM_PLAYER) {
                                    vote = 0;
                                    Game newGame = new Game();
                                    currentPlayer = 0;
                                    broadcastPlayers(newGame);
                                }
                                continue;
                            }

                        }

                        if (request.startsWith("COLLECT") || request.startsWith("PURCHASE") || request.startsWith("RESERVE")) {
                            Game updatedGame = (Game) input[currentPlayer].readObject();
                            broadcastPlayers(updatedGame);
                        }

                        //next one;
                        currentPlayer = (currentPlayer + 1) % NUM_PLAYER;
                        //output[currentPlayer].writeObject("MOVE");

                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                        break;
                    }
                }


            } catch (IOException e) {
                e.printStackTrace();
                //break;
            }
        //}
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
