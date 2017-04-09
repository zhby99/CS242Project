package Model;

import Model.utils.GemInfo;

import java.util.Collections;
import java.util.Map;

/**
 * Created by yu on 4/8/17.
 */
public class Board {
    private int numPlayer;
    private Noble[] nobles;
    private Deck[] decks;
    private Card[][] cards;
    private GemInfo AvailableGem;
    private int AvailableGolds;
    private Player[] players;

    public Board(int numPlayer) {
        this.numPlayer = numPlayer;
        nobles = new Noble[numPlayer+1];
        decks = new Deck[3]; // three decks for cards of different level
        for (int i=0; i<3;i++) {
            decks[i] = new Deck();
        }
        cards = new Card[3][4];
        AvailableGem = new GemInfo(7,7,7,7,7);
        AvailableGolds = 5;
        players = new Player[4];
        for (int i=0; i<4;i++)
        {
            players[i] = new Player(i);
        }
    }

    /**
     * Shuffle three decks
     */
    public void shuffle(){
        Collections.shuffle(decks[0].cards);
        Collections.shuffle(decks[1].cards);
        Collections.shuffle(decks[2].cards);
    }

    /**
     * get a new card from the deckIndex deck, and remove this card from the deck
     * @param deckIndex the index of the deck that needs to give a new card
     * @return the card on the top of the deck
     */
    public Card getNewCard(int deckIndex){
        Card newCard = decks[deckIndex].cards.get(0);
        decks[deckIndex].cards.remove(0);
        return newCard;
    }


    /**
     * Judge whether the deck is empty
     * @param deckIndex the index of the deck
     * @return True of false
     */
    public boolean isDeckEmpty(int deckIndex){
        return decks[deckIndex].cards.isEmpty();
    }

    /**
     * Update the number of the available gems
     * @param deltaDiamond the change value of Diamond
     * @param deltaEmerald the change value of Emerald
     * @param deltaOnyx the change value of Onyx
     * @param deltaRuby the change value of Ruby
     * @param deltaSapphire the change value of Sapphire
     */
    public void changeGem(int deltaDiamond, int deltaEmerald,int deltaOnyx, int deltaRuby, int deltaSapphire){
        AvailableGem.updateInfo(deltaDiamond, deltaEmerald, deltaOnyx, deltaRuby, deltaSapphire);
    }


}
