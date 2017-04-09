package Model;
import Model.utils.*;
import java.util.ArrayList;

/**
 * Created by wu on 4/8/17.
 */
public class Player {
	private int id;
	private int score;
	GemInfo gems;
	int golds;
	GemInfo cards;
	int numCards;
	ArrayList<Card> reserves;
	Board board;

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

	public void reserveCard(Card newCard){

	}

	public void collectGems(){

	}

	public void buyReserved(){

	}
}
