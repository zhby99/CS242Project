package Network;

import Controller.Controller;
import Game.*;

import javax.print.DocFlavor;
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
        //open new socket and stream
        listener = new ServerSocket(port);
        output = new ObjectOutputStream[NUM_PLAYER];
        input = new ObjectInputStream[NUM_PLAYER];

        System.out.println("Server is set up");

        for(int i = 0; i <  NUM_PLAYER; i++){
            //for wait enough players to connect
            Socket server = listener.accept();
            System.out.printf("%d players connected\n",i+1);
            //establish streaming for each player
            output[i] = new ObjectOutputStream(server.getOutputStream());
            input[i] = new ObjectInputStream(server.getInputStream());
        }

    }

    //initialize game after each player sends their game back
    private void gameInit() throws IOException, ClassNotFoundException {

        ArrayList<String> names = new ArrayList<String>(NUM_PLAYER);
        for(int i = 0; i < NUM_PLAYER; i++){

            output[i].writeObject("Please specify your username");
            String username = (String)input[i].readObject();
            names.add(username);

        }

        Game game = new Game(names);
        System.out.println("new Game");
        gameHelper(game);
    }

    //assign each player a unique id and send them the game
    private void gameHelper(Game game) throws IOException {

        for(int i = 0; i <  NUM_PLAYER; i++) {
            output[i].writeObject(i);
            output[i].writeObject(game);
            //ensure the game is initialized for each player
            try {
                String str = (String)input[i].readObject();
                System.out.println(str);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
        System.out.println("Game Starts");
    }

    //close the socket
    private void gameExit() throws IOException {

        listener.close();

        System.out.println("Game Ends");
    }


    //broadcast the list of winners to all players
    private void announceResult(ArrayList<Integer>  winners) throws IOException {

        for(int i = 0; i <  NUM_PLAYER; i++) {

            if (winners.contains(i)){
                if (winners.size() == 1)
                    output[i].writeObject("WIN");
                else {
                    output[i].writeObject("TIE");
                    output[i].writeObject(winners);
                }
            }
            else
                output[i].writeObject("LOSE");
        }

    }

    //when someone declares victory, the rest players still have one round to play
    private void lastRound(int currentPlayer) throws IOException, ClassNotFoundException {
        Game updatedGame = (Game) input[currentPlayer].readObject();
        broadcastPlayers(updatedGame);

        //collect the index of winners
        ArrayList<Integer> winners = new ArrayList<>();
        winners.add(currentPlayer);

        for (int nextPlayer = currentPlayer + 1; nextPlayer < NUM_PLAYER; nextPlayer++) {
            String request = (String) input[nextPlayer].readObject();
            updatedGame = (Game) input[nextPlayer].readObject();
            if (request.startsWith("VICTORY")) {
                winners.add(nextPlayer);
            }
            broadcastPlayers(updatedGame);
        }
        announceResult(winners);

    }

    /**
     * Send vote commend to all clients
     * @throws IOException
     */
    private void askNewGame() throws IOException{
        for(int i = 0; i < NUM_PLAYER; i++){
            output[i].writeObject("VOTE");
        }
    }

    //update game for each player
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
                int tmp = 0;
                int vote = 0;
                int agree = 0;
                while (true) {
                    try {
                        //action
                        String request = (String) input[currentPlayer].readObject();
                        if (request.startsWith("EXIT")) {
                            //let other player continue playing until only one left
                            gameExit();
                            break;
                        }

                        //a player meets the game winning requirement
                        if (request.startsWith("VICTORY")) {
                            lastRound(currentPlayer);
                            gameExit();
                            break;
                        }

                        //a player request a new game
						if (request.startsWith("RESTART")) {
                            tmp = currentPlayer;
                            //ask for consensus
                            askNewGame();
                            continue;
						}

						//count the votes and add up agree
                        if(request.startsWith("AGREE")){
                            vote += 1;
                            agree +=1;
                            currentPlayer = (currentPlayer + 1) % NUM_PLAYER;
                            if(agree == NUM_PLAYER){
                                vote = 0;
                                agree = 0;
                                Game newGame = new Game();
                                currentPlayer = 0;
                                broadcastPlayers(newGame);
                            }
                            else if(vote == NUM_PLAYER){
                                vote = 0;
                                agree = 0;
                                currentPlayer = tmp;
                            }

                            continue;
                        }

                        //add up the vote only
                        if(request.startsWith("DECLINE")){
                            vote += 1;
                            currentPlayer = (currentPlayer + 1) % NUM_PLAYER;
                            if(vote == NUM_PLAYER){
                                vote = 0;
                                agree = 0;
                                currentPlayer = tmp;
                            }

                            continue;
                        }

                        //valid action
                        if (request.startsWith("COLLECT") || request.startsWith("PURCHASE") || request.startsWith("RESERVE")) {
                            Game updatedGame = (Game) input[currentPlayer].readObject();
                            broadcastPlayers(updatedGame);
                            currentPlayer = (currentPlayer + 1) % NUM_PLAYER;
                        }

                        //next one;

                        //output[currentPlayer].writeObject("MOVE");

                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                        break;
                    }
                }


            } catch (IOException e) {
                e.printStackTrace();
                //break;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
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
