package Tests;

import Model.Board;
import org.junit.Test;

import static Model.utils.GameUtils.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by boyinzhang on 4/9/17.
 */
public class BoardTests {
    @Test
    public void createBoardTest() throws Exception{
        Board board = new Board(NUM_PLAYER);
    }
}
