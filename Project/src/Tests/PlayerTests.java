package Tests;


import java.util.ArrayList;
import Model.*;
import Model.utils.*;
import org.junit.Test;

import static Model.utils.GameUtils.*;
import static org.junit.Assert.assertEquals;
/**
 * Created by boyinzhang on 4/9/17.
 */
public class PlayerTests {
    @Test
    public void createPlayerTest() throws Exception{
        Board board = new Board(NUM_PLAYER);
        Player player = new Player(0,board);

        assertEquals(player.getId(), 0);
        assertEquals(player.getScore(), 0);
        assertEquals(player.getGolds(),0);
        assertEquals(player.getNumCards(),0);

        GemInfo testGem = new GemInfo(0);
        assertEquals(player.getGems(),testGem);
        assertEquals(player.getCards(),testGem);

        ArrayList<Card> testReserves = new ArrayList<Card>();
        assertEquals(player.getReserves(),testReserves);

    }

    public void reserveCardTest() throws Exception{
        Board board = new Board(NUM_PLAYER);
        Player player = new Player(0,board);

        GemInfo developmentCost = new GemInfo(1,1,1,1,0);
        Card card = new Card(1,developmentCost,Gem.SAPPHIRE);
        player.reserveCard(card);

        GemInfo testGem = new GemInfo(0);
        assertEquals(player.getGolds(),1);
        assertEquals(player.getReserves().size(),1);
        assertEquals(player.getScore(),0);
        assertEquals(player.getGems(),testGem);
        assertEquals(board.getAvailableGolds(),INIT_AMOUNT_GOLD-1);
    }

    public void collectGemsTest() throws Exception{
        Board board = new Board(NUM_PLAYER);
        Player player = new Player(0,board);

        GemInfo selectedGems = new GemInfo(1,1,1,0,0);
        GemInfo leftGems = new GemInfo(6,6,6,7,7);

        assertEquals(player.collectGems(selectedGems),true);
        assertEquals(player.getGems(),selectedGems);
        assertEquals(player.getGolds(),0);
        assertEquals(board.getAvailableGem(),leftGems);

        selectedGems.setGems(0,0,0,2,0);
        leftGems.setGems(6,6,6,5,7);
        assertEquals(player.collectGems(selectedGems),true);
        assertEquals(player.getGems(),selectedGems);
        assertEquals(player.getGolds(),0);
        assertEquals(board.getAvailableGem(),leftGems);

    }
}
