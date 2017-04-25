package Network;

import Controller.Controller;
import Game.*;
import Model.Card;
import Model.utils.GemInfo;

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
    private Game game;

    public Server(int port)throws Exception {
        listener = new ServerSocket(port);
        output = new ObjectOutputStream[NUM_PLAYER];
        input = new ObjectInputStream[NUM_PLAYER];

        System.out.println("Server is set up");

        for(int i = 0; i <  NUM_PLAYER; i++){
            Socket server = listener.accept();
            System.out.printf("%d players connected\n",i+1);

            output[i] = new ObjectOutputStream(server.getOutputStream());
            input[i] = new ObjectInputStream(server.getInputStream());
        }

    }

    private void gameInit() throws IOException, ClassNotFoundException {

        ArrayList<String> names = new ArrayList<String>(NUM_PLAYER);
        for(int i = 0; i < NUM_PLAYER; i++){

            output[i].writeObject("Please specify your username");
            String username = (String)input[i].readObject();
            names.add(username);

        }

        game = new Game(names);
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
            output[i].reset();
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
                            game = (Game) input[currentPlayer].readObject();
                            broadcastPlayers(game);

                            ArrayList<Integer> winners = new ArrayList<>();
                            winners.add(currentPlayer);

                            for (int nextPlayer = currentPlayer + 1; nextPlayer < NUM_PLAYER; nextPlayer++) {
                                //output[nextPlayer].writeObject("MOVE");
                                request = (String) input[nextPlayer].readObject();
                                game = (Game) input[nextPlayer].readObject();
                                if (request.startsWith("VICTORY")) {
                                    winners.add(nextPlayer);
                                }
                                broadcastPlayers(game);
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
                                game = new Game();
                                currentPlayer = 0;
                                broadcastPlayers(game);
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
                        // here is receiving the information for collection
                        if (request.startsWith("COLLECT")){
                            String command = (String) input[currentPlayer].readObject();
                            String[] splited = command.split(" ");
                            //splited is the number of each gems collected
                            GemInfo collectedGem = new GemInfo(Integer.parseInt(splited[0]),Integer.parseInt(splited[1]), Integer.parseInt(splited[2]), Integer.parseInt(splited[3]), Integer.parseInt(splited[4]));
                            game.getCurrentPlayer().collectGems(collectedGem);
                            game.turnToNextPlayer();
                            //send the updated game to all the clients
                            broadcastPlayers(game);
                            currentPlayer = (currentPlayer + 1) % NUM_PLAYER;
                        }

                        // here is receiving the information for buying cards
                        if (request.startsWith("PURCHASE")) {
                            String command = (String) input[currentPlayer].readObject();
                            String[] splited = command.split(" ");
                            //splited is the postion of the card, and whether the card is reserved
                            int cardX = Integer.parseInt(splited[0]);
                            int cardY = Integer.parseInt(splited[1]);
                            boolean isReserved;
                            if ( Integer.parseInt(splited[2]) == 1)
                                isReserved = true;
                            else
                                isReserved = false;
                            Card selectedCard;
                            //if it's reserved, then get the card from player section
                            if(isReserved)
                                selectedCard = game.getPlayers()[cardX].getReserves().get(cardY);
                            //if not, get the card from the game board
                            else
                                selectedCard = game.gameBoard.getCards()[cardX][cardY];

                            game.getCurrentPlayer().buyCard(selectedCard,isReserved);
                            //if it is not reserved, we need to put a new card back.
                            if(!isReserved) {
                                Card newCard = game.getGameBoard().getNewCard(cardX);
                                int[] positon = {cardX, cardY};
                                game.getGameBoard().setCardOnBoard(newCard, positon);
                            }
                            game.getCurrentPlayer().recruitAvailableNobles();
                            game.turnToNextPlayer();
                            broadcastPlayers(game);
                            currentPlayer = (currentPlayer + 1) % NUM_PLAYER;
                        }

                        //here is receiving the information for reserving card
                        if (request.startsWith("RESERVE")) {
                            String command = (String) input[currentPlayer].readObject();
                            String[] splited = command.split(" ");
                            // splited is the position of the card
                            int cardX = Integer.parseInt(splited[0]);
                            int cardY = Integer.parseInt(splited[1]);
                            Card selectedCard = game.gameBoard.getCards()[cardX][cardY];
                            game.getCurrentPlayer().reserveCard(selectedCard);
                            Card newCard = game.getGameBoard().getNewCard(cardX);
                            int[] positon = {cardX, cardY};
                            game.getGameBoard().setCardOnBoard(newCard, positon);
                            game.turnToNextPlayer();
                            broadcastPlayers(game);
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
