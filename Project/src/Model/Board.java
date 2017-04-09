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

    /**
     * This function used to initialize all cards related.
     * Written by Boyin
     */
    public void initialBoard(){
        this.decks.low.add(new Card(0, new GemInfo(0,0,2,0,2), Gem.DIAMOND));
        this.decks.low.add(new Card(1, new GemInfo(0,4,0,0,0), Gem.DIAMOND));
        this.decks.low.add(new Card(0, new GemInfo(0,1,1,1,1), Gem.DIAMOND));
        this.decks.low.add(new Card(0, new GemInfo(0,2,1,0,2), Gem.DIAMOND));
        this.decks.low.add(new Card(0, new GemInfo(0,2,1,1,1), Gem.DIAMOND));
        this.decks.low.add(new Card(0, new GemInfo(0,0,0,0,3), Gem.DIAMOND));
        this.decks.low.add(new Card(0, new GemInfo(3,0,1,0,1), Gem.DIAMOND));
        this.decks.low.add(new Card(0, new GemInfo(0,0,1,2,0), Gem.DIAMOND));

        this.decks.low.add(new Card(0, new GemInfo(0,0,0,2,2), Gem.EMERALD));
        this.decks.low.add(new Card(0, new GemInfo(1,1,0,0,3), Gem.EMERALD));
        this.decks.low.add(new Card(0, new GemInfo(0,0,2,2,1), Gem.EMERALD));
        this.decks.low.add(new Card(0, new GemInfo(1,0,1,1,1), Gem.EMERALD));
        this.decks.low.add(new Card(0, new GemInfo(2,0,0,0,1), Gem.EMERALD));
        this.decks.low.add(new Card(0, new GemInfo(0,0,0,3,0), Gem.EMERALD));
        this.decks.low.add(new Card(0, new GemInfo(1,0,2,1,1), Gem.EMERALD));
        this.decks.low.add(new Card(1, new GemInfo(0,0,4,0,0), Gem.EMERALD));

        this.decks.low.add(new Card(0, new GemInfo(1,1,0,1,2), Gem.ONYX));
        this.decks.low.add(new Card(0, new GemInfo(0,3,0,0,0), Gem.ONYX));


    }
}
