package Model;

import java.io.Serializable;

/**
 * Created by wu on 4/8/17.
 */
public enum Gem  implements Serializable {
    DIAMOND("Diamond",1){

    },
    EMERALD("Emerald",2) {

    },
    ONYX("Onyx", 3){

    },
    RUBY("Ruby", 4){

    },
    SAPPHIRE("Sapphire", 5){

    };

    private String gemName;
    private int index;

    Gem(final String gemName, int index) {
        this.gemName = gemName;
        this.index = index;
    }

    public String getGemName(){
        return gemName;
    }
    public int getIndex(){
        return index;
    }

}
