package Model;
import Model.utils.*;
import java.util.ArrayList;

import static Model.utils.GemInfo.combineGems;
import static Model.utils.GemInfo.reduceGems;

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

	public void buyCard(Card newCard){

	}

	/**
	 * Reserved a specified card in this round by the player.
	 * @param newCard the card chose to be reserved
	 */
	public void reserveCard(Card newCard){
		if(this.reserves.size()>=3 || this.board.getAvailableGolds()<=0){
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

	public void buyReserved(){

	}
}
