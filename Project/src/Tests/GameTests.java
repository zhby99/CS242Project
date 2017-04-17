package Tests;

import Model.Board;
import Model.Player;
import Game.Game;
import org.junit.Test;

import static Model.utils.GameUtils.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by boyinzhang on 4/9/17.
 */
public class GameTests {

    @Test
    public void endConditionTest() throws Exception{
        Game game = new Game();
        game.players[0].updateScore(1);
        assertEquals(game.checkEndofGame(),0);
        game.players[1].updateScore(15);
        assertEquals(game.checkEndofGame(),1);
    }

    @Test
    public void currentPlayerTest() throws Exception{
        Game game = new Game();
        assertEquals(game.currentPlayer, game.players[0]);
        game.turnToNextPlayer();
        assertEquals(game.currentPlayer, game.players[1]);
    }


}
