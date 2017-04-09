package Model;

import Model.utils.GemInfo;

import java.util.ArrayList;
import java.util.Collections;

import static Model.utils.GameUtils.*;

/**
 * Created by yu on 4/8/17.
 */
public class Board {

    private int numPlayer;
    private Noble[] nobles;
    private Deck[] decks;
    private Card[][] cards;
    GemInfo availableGem;
    private int availableGolds;

    public Board(int numPlayer) {
        this.numPlayer = numPlayer;
        nobles = new Noble[numPlayer+1];
        decks = new Deck[NUM_CARD_RANK]; // three decks for cards of different level
        for (int i=0; i<NUM_CARD_RANK;i++) {
            decks[i] = new Deck();
        }
        cards = new Card[NUM_CARD_RANK][NUM_CARD_PER_RANK];
        availableGem = new GemInfo(INIT_AMOUNT_PER_GEM,INIT_AMOUNT_PER_GEM,INIT_AMOUNT_PER_GEM,INIT_AMOUNT_PER_GEM,INIT_AMOUNT_PER_GEM);
        availableGolds = INIT_AMOUNT_GOLD;
    }

    public int getAvailableGolds(){
        return this.availableGolds;
    }

    /**
     * Call when any player choose to reserve a card, decrease the gold by 1.
     */
    public void reduceAvailableGolds(){
        this.availableGolds--;
    }

    /**
     * Shuffle three decks
     */
    public void shuffle(){
        for(Deck deck : this.decks){
            Collections.shuffle(deck.cards);
        }
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
    public boolean isDeckEmpty(int deckIndex) {
        return decks[deckIndex].cards.isEmpty();
    }

    /**
     * This function used to initialize all cards related.
     */
    public void initialBoard(){
        initialLowRankCards();
        initialMedianRankCards();
        initialHighRankCards();
        intitialNobles();
    }

    /**
     * Used to initial all noble cards
     */
    private void intitialNobles(){
        ArrayList<Noble> nobleCards = new ArrayList<Noble>();
        nobleCards.add(new Noble(0,4,0,4,0));
        nobleCards.add(new Noble(3,0,3,3,0));
        nobleCards.add(new Noble(0,4,0,0,4));
        nobleCards.add(new Noble(3,3,0,0,3));
        nobleCards.add(new Noble(4,0,4,0,0));
        nobleCards.add(new Noble(0,3,3,3,0));
        nobleCards.add(new Noble(0,0,4,4,0));
        nobleCards.add(new Noble(3,0,3,0,3));
        nobleCards.add(new Noble(4,0,0,0,4));
        nobleCards.add(new Noble(0,3,0,3,3));
        Collections.shuffle(nobleCards);
        for(int i = 0; i< numPlayer+1; i++){
            this.nobles[i] = nobleCards.get(i);
        }
    }

    /**
     * Used to initial high rank cards
     */
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

    /**
     * used to initial median rank cards
     */
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

    /**
     * used to initial low rank cards
     */
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

    /**
     * Update the number of the available gems
     * @param deltaDiamond the change value of Diamond
     * @param deltaEmerald the change value of Emerald
     * @param deltaOnyx the change value of Onyx
     * @param deltaRuby the change value of Ruby
     * @param deltaSapphire the change value of Sapphire
     */
    public void changeGem(int deltaDiamond, int deltaEmerald,int deltaOnyx, int deltaRuby, int deltaSapphire){
        availableGem.updateInfo(deltaDiamond, deltaEmerald, deltaOnyx, deltaRuby, deltaSapphire);
    }


}

