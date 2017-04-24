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

    private void gameInit() throws IOException{

        ArrayList<String> names = new ArrayList<String>(NUM_PLAYER);
        for(int i = 0; i < NUM_PLAYER; i++){
            String username = null;
            try {
                username = (String)input[i].readObject();
                names.add(username);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        Game game = new Game(names);
        System.out.println("new Game");
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

    /**
     * Send vote commend to all clients
     * @throws IOException
     */
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
                            tmp = currentPlayer;
                            askNewGame();
                            continue;
						}

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
