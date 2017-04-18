package Tests;

import Game.Game;
import Model.Card;
import Model.Noble;
import Model.utils.GemInfo;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by boyinzhang on 4/18/17.
 */
public class NobleTests {
    @Test
    public void nobleRecruitedTest() throws Exception {
        Game game = new Game();
        Noble currentNoble = game.getGameBoard().getNobles()[0];
        assertFalse(currentNoble.getIsRecruited());
        currentNoble.beRecruited();
        assertTrue(currentNoble.getIsRecruited());
    }

    @Test
    public void nobleSatisfiedTest() throws Exception{
        Game game = new Game();
        Noble currentNoble = game.getGameBoard().getNobles()[0];
        assertFalse(currentNoble.satisfied(new GemInfo(1)));
        assertTrue(currentNoble.satisfied(new GemInfo(4)));
    }
}
