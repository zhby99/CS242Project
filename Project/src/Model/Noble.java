package Model;
import Model.utils.*;

import static Model.utils.GameUtils.NOBLE_SCORE;

/**
 * Created by wu on 4/8/17.
 */
public class Noble {
	private int score;
	private GemInfo threshold;
	private boolean isRecruited;

	public Noble(int diamondCost, int emeraldCost, int onyxCost, int rubyCost, int sapphireCost){
		score = NOBLE_SCORE;
		threshold = new GemInfo(diamondCost,emeraldCost,onyxCost,rubyCost,sapphireCost);
		isRecruited = false;
	}

	public boolean getIsRecruited(){
		return this.isRecruited;
	}

	public void beRecruited(){
		this.isRecruited = true;
	}

	public int getScore() {
		return score;
	}

	public GemInfo getThreshold(){
		return threshold;
	}

	public boolean satisfied(GemInfo cards){
		return this.threshold.isSatisfied(cards);
	}

}
