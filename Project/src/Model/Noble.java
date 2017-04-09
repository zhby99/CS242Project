package Model;
import Model.utils.*;

import static Model.utils.GameUtils.NOBLE_SCORE;

/**
 * Created by wu on 4/8/17.
 */
public class Noble {
	int score;
	GemInfo threshold;

	Noble(int diamondCost, int emeraldCost, int onyxCost, int rubyCost, int sapphireCost){
		score = NOBLE_SCORE;
		threshold = new GemInfo(diamondCost,emeraldCost,onyxCost,rubyCost,sapphireCost);
	}

}
