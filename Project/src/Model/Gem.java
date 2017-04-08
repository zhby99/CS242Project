package Model;

/**
 * Created by wu on 4/8/17.
 */
public enum Gem {
    DIAMOND("D",1){

    },
    EMERALD("E",2) {

    },
    ONYX("O", 3){

    },
    RUBY("R", 4){

    },
    SAPPHIRE("S", 5){

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
