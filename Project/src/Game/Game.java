package Game;

import Model.Board;
import Model.Player;

import static Model.utils.GameUtils.*;

/**
 * Created by boyinzhang on 4/9/17.
 */



public class Game {
    private Player[] players;
    private Player currentPlayer;
    //private Player nextTurnPlayer;
    private Board gameBoard;

    Game(){
        this.gameBoard = new Board(NUM_PLAYER);
        this.players = new Player[NUM_PLAYER];
        for(int i = 0; i < NUM_PLAYER; i++){
            players[i] = new Player(i+1 , this.gameBoard);
        }
        currentPlayer = players[0];
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
}
