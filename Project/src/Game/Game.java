package Game;

import Model.Board;
import Model.Player;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;

import static Model.utils.GameUtils.*;

/**
 * Created by boyinzhang on 4/9/17.
 */



public class Game implements Serializable{
    public Player[] players;
    public Player currentPlayer;
    public Board gameBoard;

    public Game(){
        this.gameBoard = new Board(NUM_PLAYER);
        this.gameBoard.initialBoard();
        this.players = new Player[NUM_PLAYER];
        for(int i = 0; i < NUM_PLAYER; i++){
            players[i] = new Player(i+1 , this.gameBoard);
        }
        currentPlayer = players[0];
    }

    public Game(ServerSocket listener) throws IOException {
        this.gameBoard = new Board(NUM_PLAYER);
        this.gameBoard.initialBoard();
        this.players = new Player[NUM_PLAYER];
        for(int i = 0; i < NUM_PLAYER; i++){
            Socket socket = listener.accept();
            players[i] = new Player(socket, i+1 , this.gameBoard);
            OutputStream os = socket.getOutputStream();
            ObjectOutputStream outSteam = new ObjectOutputStream(os);
            outSteam.writeObject(this);
            outSteam.close();
            os.close();
        }
        currentPlayer = players[0];
        for(int i = 0; i < NUM_PLAYER; i++){
            players[i].start();
        }
    }

    public Board getGameBoard(){
        return this.gameBoard;
    }

    public Player getCurrentPlayer(){
        return this.currentPlayer;
    }

    public Player[] getPlayers(){
        return players;
    }

    /**
     * Used to check if the someone won.
     * @return
     */
    public final int checkEndofGame(){
        int numberOfWining = 0;
        for(Player player: this.players){
            if(player.hasWon()){
                numberOfWining += 1;
            }
        }
        return numberOfWining;
    }

    /**
     * Turn the next player
     */
    public void turnToNextPlayer(){
        int currentId = currentPlayer.getPlayerId();
        if(currentId == NUM_PLAYER){
            currentId = 0;
        }
        currentPlayer = this.players[currentId];
    }
}
