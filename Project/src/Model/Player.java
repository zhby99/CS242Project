package Model;
import Model.utils.*;
import java.util.ArrayList;

import static Model.utils.GameUtils.MAX_RESERVED_CARDS;
import static Model.utils.GameUtils.WINNING_SCORE;
import static Model.utils.GemInfo.combineGems;
import static Model.utils.GemInfo.reduceGems;
import static java.lang.Math.*;

/**
 * Created by wu on 4/8/17.
 */
public class Player {
	final private int id;
	private int score;
	GemInfo gems;
	int golds;
	GemInfo cards;
	int numCards;
	ArrayList<Card> reserves;
	private Board board;

	public Player(int pid, Board newBoard){
		id = pid;
		score = 0;
		gems = new GemInfo(0,0,0,0,0);
		golds = 0;
		cards = new GemInfo(0,0,0,0,0);
		numCards = 0;
		reserves = new ArrayList<Card>();
		board = newBoard;
	}

	public int getId(){return id;}
	public int getScore() {return score;}
	public void updateScore(int newScore){ score = newScore;}


	public void buyCard(Card newCard, boolean isReserved){
        GemInfo requiredGem = newCard.getDevelopmentCost();
        GemInfo restGem = newCard.getDevelopmentCost();
        //requiredGem is the gem we need to pay for this card
        reduceGems(requiredGem,this.cards);
        //restGem is the number of gems we still need to buy this card when we spend all of our gems
        reduceGems(restGem, this.cards);
        reduceGems(restGem,this.gems);

        //if the restGem is negative, then we have enough this kind of gem, so set it to 0.
        if(restGem.diamond < 0) restGem.diamond = 0;
        if(restGem.emerald < 0) restGem.emerald = 0;
        if(restGem.onyx < 0) restGem.onyx = 0;
        if(restGem.ruby < 0) restGem.ruby = 0;
        if(restGem.sapphire < 0) restGem.sapphire = 0;

        //if the restGem is smaller than the number of golds, we can use golds to pay the rest gems.
        if( restGem.diamond + restGem.emerald + restGem.onyx + restGem.ruby + restGem.sapphire < golds ) {
            reduceGems(this.gems, requiredGem);
            this.golds -= (restGem.diamond + restGem.emerald + restGem.onyx + restGem.ruby + restGem.sapphire);
            this.gems.updateInfo(max(this.gems.diamond, 0), max(this.gems.emerald, 0), max(this.gems.onyx, 0), max(this.gems.ruby, 0), max(this.gems.sapphire, 0));
        }
	}

	/**
	 * Reserved a specified card in this round by the player.
	 * @param newCard the card chose to be reserved
	 */
	public void reserveCard(Card newCard){
		if(this.reserves.size()>= MAX_RESERVED_CARDS || this.board.getAvailableGolds()<=0){
			//TODO: Exception
		}
		else{
			this.reserves.add(newCard);
			this.golds++;
			this.board.reduceAvailableGolds();
		}
	}

	/**
	 * Update the gems of the current player according to the collection in this round.
	 * @param collectedGems The gems collected by player in this round
	 */
	public void collectGems(GemInfo collectedGems){
		combineGems(this.gems, collectedGems);
		reduceGems(this.board.availableGem, collectedGems);
	}

	/**
	 * Check if the player has won
	 * @return true if the player won
	 */
	public final boolean hasWon(){
		return this.score >= WINNING_SCORE;
	}

}


