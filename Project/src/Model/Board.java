package Model;

import Model.utils.GemInfo;

import java.util.Map;

/**
 * Created by yu on 4/8/17.
 */
public class Board {
    int numPlayer;
    Noble[] nobles;
    Deck decks;
    Card[][] cards;
    GemInfo AvailableGem;
    int AvailableGolds;
    Player[] players;

    public Board(int numPlayer) {
        this.numPlayer = numPlayer;
        nobles = new Noble[numPlayer+1];
        decks = new Deck();
        cards = new Card[3][4];
        AvailableGem = new GemInfo(7,7,7,7,7);
        AvailableGolds = 5;
        players = new Player[4];
    }
}
