package Model;
import Model.utils.*;
import java.util.ArrayList;

/**
 * Created by wu on 4/8/17.
 */
public class Player {
	int id;
	int score;
	GemInfo tmpGems;
	int tmpGolds;
	GemInfo gems;
	int golds;
	int numCards;
	ArrayList<Card> reserves;

	public Player(int pid){
		id = pid;
		score = 0;
		tmpGems = new GemInfo(0,0,0,0,0);
		tmpGolds = 0;
		gems = new GemInfo(0,0,0,0,0);
		golds = 0;
		numCards = 0;
		reserves = new ArrayList<Card>();
	}
}
