package Model;

import Model.utils.GemInfo;

/**
 * Created by yu on 4/8/17.
 */
public class Board {
    int numPlayer;
    Noble[] nobles;
    Deck[] decks;
    Card[][] cards;
    GemInfo AvailableGem;
    int AvailableGolds;
    Player[] players;

    public Board(int numPlayer) {
        this.numPlayer = numPlayer;
        nobles = new Noble[numPlayer+1];
        decks = new Deck[3];
        cards = new Card[3][4];
        AvailableGem = new GemInfo(7,7,7,7,7);
        AvailableGolds = 5;
        players = new Player[4];
    }

    /**
     * This function used to initialize all cards related.
     */
    public void initialBoard(){
        initialLowRankCards();
        initialMedianRankCards();
        initialHighRankCards();

    }

    private void initialHighRankCards() {
        this.decks[2].cards.add(new Card(3, new GemInfo(3,3,3,0,5), Gem.RUBY));
        this.decks[2].cards.add(new Card(4, new GemInfo(0,6,0,3,3), Gem.RUBY));
        this.decks[2].cards.add(new Card(4, new GemInfo(0,7,0,0,0), Gem.RUBY));
        this.decks[2].cards.add(new Card(5, new GemInfo(0,7,0,3,0), Gem.RUBY));

        this.decks[2].cards.add(new Card(3, new GemInfo(3,3,5,3,0), Gem.RUBY));
        this.decks[2].cards.add(new Card(4, new GemInfo(6,0,3,0,3), Gem.RUBY));
        this.decks[2].cards.add(new Card(4, new GemInfo(7,0,0,0,0), Gem.RUBY));
        this.decks[2].cards.add(new Card(5, new GemInfo(7,0,0,0,3), Gem.RUBY));

        this.decks[2].cards.add(new Card(3, new GemInfo(3,5,0,3,3), Gem.ONYX));
        this.decks[2].cards.add(new Card(4, new GemInfo(0,3,3,6,0), Gem.ONYX));
        this.decks[2].cards.add(new Card(4, new GemInfo(0,0,0,7,0), Gem.ONYX));
        this.decks[2].cards.add(new Card(5, new GemInfo(0,0,3,7,0), Gem.ONYX));

        this.decks[2].cards.add(new Card(3, new GemInfo(5,0,3,3,3), Gem.EMERALD));
        this.decks[2].cards.add(new Card(4, new GemInfo(0,7,0,0,0), Gem.EMERALD));
        this.decks[2].cards.add(new Card(4, new GemInfo(3,3,0,0,6), Gem.EMERALD));
        this.decks[2].cards.add(new Card(5, new GemInfo(0,3,0,0,7), Gem.EMERALD));

        this.decks[2].cards.add(new Card(3, new GemInfo(0,3,3,5,3), Gem.DIAMOND));
        this.decks[2].cards.add(new Card(4, new GemInfo(3,0,6,3,0), Gem.DIAMOND));
        this.decks[2].cards.add(new Card(4, new GemInfo(0,0,7,0,0), Gem.DIAMOND));
        this.decks[2].cards.add(new Card(5, new GemInfo(3,0,7,0,0), Gem.DIAMOND));
    }

    private void initialMedianRankCards() {
        this.decks[1].cards.add(new Card(1, new GemInfo(0,3,3,0,2), Gem.SAPPHIRE));
        this.decks[1].cards.add(new Card(1, new GemInfo(0,2,0,3,2), Gem.SAPPHIRE));
        this.decks[1].cards.add(new Card(2, new GemInfo(2,0,4,1,0), Gem.SAPPHIRE));
        this.decks[1].cards.add(new Card(2, new GemInfo(5,0,0,0,3), Gem.SAPPHIRE));
        this.decks[1].cards.add(new Card(2, new GemInfo(0,0,0,0,5), Gem.SAPPHIRE));
        this.decks[1].cards.add(new Card(3, new GemInfo(0,0,0,0,6), Gem.SAPPHIRE));

        this.decks[1].cards.add(new Card(1, new GemInfo(3,2,0,0,2), Gem.ONYX));
        this.decks[1].cards.add(new Card(1, new GemInfo(3,3,2,0,0), Gem.ONYX));
        this.decks[1].cards.add(new Card(2, new GemInfo(0,3,0,5,0), Gem.ONYX));
        this.decks[1].cards.add(new Card(2, new GemInfo(0,4,0,2,1), Gem.ONYX));
        this.decks[1].cards.add(new Card(2, new GemInfo(5,0,0,0,0), Gem.ONYX));
        this.decks[1].cards.add(new Card(3, new GemInfo(0,0,6,0,0), Gem.ONYX));

        this.decks[1].cards.add(new Card(1, new GemInfo(2,0,2,0,3), Gem.EMERALD));
        this.decks[1].cards.add(new Card(1, new GemInfo(3,2,0,3,0), Gem.EMERALD));
        this.decks[1].cards.add(new Card(2, new GemInfo(0,3,0,0,5), Gem.EMERALD));
        this.decks[1].cards.add(new Card(2, new GemInfo(4,0,1,0,2), Gem.EMERALD));
        this.decks[1].cards.add(new Card(2, new GemInfo(0,5,0,0,0), Gem.EMERALD));
        this.decks[1].cards.add(new Card(3, new GemInfo(0,6,0,0,0), Gem.EMERALD));

        this.decks[1].cards.add(new Card(1, new GemInfo(2,2,3,0,0), Gem.RUBY));
        this.decks[1].cards.add(new Card(1, new GemInfo(0,0,3,2,3), Gem.RUBY));
        this.decks[1].cards.add(new Card(2, new GemInfo(3,0,5,0,0), Gem.RUBY));
        this.decks[1].cards.add(new Card(2, new GemInfo(0,0,5,0,0), Gem.RUBY));
        this.decks[1].cards.add(new Card(2, new GemInfo(1,2,0,0,4), Gem.RUBY));
        this.decks[1].cards.add(new Card(3, new GemInfo(0,0,0,6,0), Gem.RUBY));

        this.decks[1].cards.add(new Card(1, new GemInfo(0,3,2,2,0), Gem.DIAMOND));
        this.decks[1].cards.add(new Card(1, new GemInfo(2,0,0,3,3), Gem.DIAMOND));
        this.decks[1].cards.add(new Card(2, new GemInfo(0,1,2,4,0), Gem.DIAMOND));
        this.decks[1].cards.add(new Card(2, new GemInfo(0,0,0,5,0), Gem.DIAMOND));
        this.decks[1].cards.add(new Card(2, new GemInfo(0,0,3,5,0), Gem.DIAMOND));
        this.decks[1].cards.add(new Card(3, new GemInfo(6,0,0,0,0), Gem.DIAMOND));
    }

    private void initialLowRankCards() {
        //diamond
        this.decks[0].cards.add(new Card(0, new GemInfo(0,0,2,0,2), Gem.DIAMOND));
        this.decks[0].cards.add(new Card(1, new GemInfo(0,4,0,0,0), Gem.DIAMOND));
        this.decks[0].cards.add(new Card(0, new GemInfo(0,1,1,1,1), Gem.DIAMOND));
        this.decks[0].cards.add(new Card(0, new GemInfo(0,2,1,0,2), Gem.DIAMOND));
        this.decks[0].cards.add(new Card(0, new GemInfo(0,2,1,1,1), Gem.DIAMOND));
        this.decks[0].cards.add(new Card(0, new GemInfo(0,0,0,0,3), Gem.DIAMOND));
        this.decks[0].cards.add(new Card(0, new GemInfo(3,0,1,0,1), Gem.DIAMOND));
        this.decks[0].cards.add(new Card(0, new GemInfo(0,0,1,2,0), Gem.DIAMOND));
        //emerald
        this.decks[0].cards.add(new Card(0, new GemInfo(0,0,0,2,2), Gem.EMERALD));
        this.decks[0].cards.add(new Card(0, new GemInfo(1,1,0,0,3), Gem.EMERALD));
        this.decks[0].cards.add(new Card(0, new GemInfo(0,0,2,2,1), Gem.EMERALD));
        this.decks[0].cards.add(new Card(0, new GemInfo(1,0,1,1,1), Gem.EMERALD));
        this.decks[0].cards.add(new Card(0, new GemInfo(2,0,0,0,1), Gem.EMERALD));
        this.decks[0].cards.add(new Card(0, new GemInfo(0,0,0,3,0), Gem.EMERALD));
        this.decks[0].cards.add(new Card(0, new GemInfo(1,0,2,1,1), Gem.EMERALD));
        this.decks[0].cards.add(new Card(1, new GemInfo(0,0,4,0,0), Gem.EMERALD));
        //onyx
        this.decks[0].cards.add(new Card(0, new GemInfo(1,1,0,1,2), Gem.ONYX));
        this.decks[0].cards.add(new Card(0, new GemInfo(0,3,0,0,0), Gem.ONYX));
        this.decks[0].cards.add(new Card(0, new GemInfo(2,0,0,1,2), Gem.ONYX));
        this.decks[0].cards.add(new Card(0, new GemInfo(0,1,1,3,0), Gem.ONYX));
        this.decks[0].cards.add(new Card(0, new GemInfo(2,2,0,0,0), Gem.ONYX));
        this.decks[0].cards.add(new Card(0, new GemInfo(1,1,0,1,1), Gem.ONYX));
        this.decks[0].cards.add(new Card(0, new GemInfo(0,2,0,1,0), Gem.ONYX));
        this.decks[0].cards.add(new Card(1, new GemInfo(0,0,0,0,4), Gem.ONYX));
        //ruby
        this.decks[0].cards.add(new Card(0, new GemInfo(0,1,0,0,2), Gem.RUBY));
        this.decks[0].cards.add(new Card(0, new GemInfo(2,0,0,2,0), Gem.RUBY));
        this.decks[0].cards.add(new Card(0, new GemInfo(2,1,2,0,0), Gem.RUBY));
        this.decks[0].cards.add(new Card(0, new GemInfo(2,1,1,0,1), Gem.RUBY));
        this.decks[0].cards.add(new Card(0, new GemInfo(3,0,0,0,0), Gem.RUBY));
        this.decks[0].cards.add(new Card(0, new GemInfo(1,0,3,1,0), Gem.RUBY));
        this.decks[0].cards.add(new Card(0, new GemInfo(1,1,1,0,1), Gem.RUBY));
        this.decks[0].cards.add(new Card(1, new GemInfo(4,0,0,0,0), Gem.RUBY));
        //sapphire
        this.decks[0].cards.add(new Card(0, new GemInfo(0,3,0,1,1), Gem.SAPPHIRE));
        this.decks[0].cards.add(new Card(0, new GemInfo(0,2,2,0,0), Gem.SAPPHIRE));
        this.decks[0].cards.add(new Card(0, new GemInfo(1,2,0,2,0), Gem.SAPPHIRE));
        this.decks[0].cards.add(new Card(0, new GemInfo(1,1,1,1,0), Gem.SAPPHIRE));
        this.decks[0].cards.add(new Card(0, new GemInfo(1,0,2,0,0), Gem.SAPPHIRE));
        this.decks[0].cards.add(new Card(0, new GemInfo(0,0,3,0,0), Gem.SAPPHIRE));
        this.decks[0].cards.add(new Card(0, new GemInfo(1,1,1,2,0), Gem.SAPPHIRE));
        this.decks[0].cards.add(new Card(1, new GemInfo(0,0,0,4,0), Gem.SAPPHIRE));
    }
}
