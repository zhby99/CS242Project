package Game;

import Model.Board;
import Model.Player;

import static Model.utils.GameUtils.*;

/**
 * Created by boyinzhang on 4/9/17.
 */



public class Game {
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
    public final boolean checkEndofGame(){
        for(Player player: this.players){
            if(player.hasWon()){
                return true;
            }
        }
        return false;
    }

    /**
     * Turn the next player
     */
    public void turnToNextPlayer(){
        int currentId = currentPlayer.getId();
        if(currentId == NUM_PLAYER){
            currentId = 0;
        }
        currentPlayer = this.players[currentId];
    }
}
