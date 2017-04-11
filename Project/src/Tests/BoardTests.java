package Tests;

import Model.Board;
import Model.Card;
import org.junit.Test;

import static Model.utils.GameUtils.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by boyinzhang on 4/9/17.
 */
public class BoardTests {
    @Test
    public void createBoardTest() throws Exception{
        Board board = new Board(NUM_PLAYER);
        board.initialBoard();
        assertEquals(board.getNumPlayer(), NUM_PLAYER);
        assertEquals(board.getAvailableGolds(), INIT_AMOUNT_GOLD);
        assertEquals(board.getNobles().length, NUM_PLAYER+1);
    }

    @Test
    public void reduceGoldTest() throws Exception{
        Board board = new Board(NUM_PLAYER);
        board.initialBoard();
        board.reduceAvailableGolds();
        assertEquals(board.getAvailableGolds(), INIT_AMOUNT_GOLD-1);
    }

    @Test
    public void newCardTest() throws Exception{
        Board board = new Board(NUM_PLAYER);
        board.initialBoard();
        assertFalse(board.isDeckEmpty(0));
        Card topCard = board.getDecks()[0].cards.get(0);
        Card card = board.getNewCard(0);
        assertEquals(topCard, card);
    }
}
