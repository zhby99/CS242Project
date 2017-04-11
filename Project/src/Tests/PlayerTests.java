package Tests;

import Game.Game;
import Model.Board;
import Model.Card;
import org.junit.Test;

import static Model.utils.GameUtils.*;
import static org.junit.Assert.assertEquals;
/**
 * Created by boyinzhang on 4/9/17.
 */
public class PlayerTests {
    @Test
    public void buyCardTest() throws Exception{
        Game game = new Game();
        Card topCard = game.getGameBoard().getDecks()[0].cards.get(0);
        int cardScore = topCard.getCardScore();
        assertEquals(game.getCurrentPlayer().getScore(), 0);
        game.getCurrentPlayer().getGems().setGems(10,10,10,10,10);
        game.getCurrentPlayer().buyCard(topCard, false);
        assertEquals(game.getCurrentPlayer().getScore(), cardScore);
        topCard = game.getGameBoard().getDecks()[2].cards.get(0);
        cardScore = topCard.getCardScore();
        game.getCurrentPlayer().buyCard(topCard, false);
        assertEquals(game.getCurrentPlayer().getScore(), cardScore);

    }
}
