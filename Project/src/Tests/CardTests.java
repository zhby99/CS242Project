package Tests;

import Game.Game;
import Model.Card;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by boyinzhang on 4/18/17.
 */
public class CardTests {
    @Test
    public void cardReservedTest() throws Exception {
        Game game = new Game();
        Card currentCard = game.getGameBoard().getCards()[0][0];
        assertFalse(currentCard.isReserved());
        currentCard.setReserved();
        assertTrue(currentCard.isReserved());
    }

    @Test
    public void cardPositionTest() throws Exception {
        Game game = new Game();
        Card currentCard = game.getGameBoard().getCards()[2][1];
        currentCard.setPosition(2,1);
        assertEquals(currentCard.getPosition()[0], 2);
        assertEquals(currentCard.getPosition()[1], 1);
    }
}
