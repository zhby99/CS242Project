package Model;

/**
 * Created by wu on 4/8/17.
 */
public class Player {
	int id;
	int score;
	GemInfo gems;
	int golds;
	ArrayList<Card> cards;
	ArrayList<Card> reserves;

	Player(int pid){
		id = pid;
		score = 0;
		gems = new GemInfo(0,0,0,0,0);
		golds = 0;
		cards = new ArrayList<Card>();
		reserves = new ArrayList<Card>();
	}
}
