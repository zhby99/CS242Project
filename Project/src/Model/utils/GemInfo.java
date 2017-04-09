package Model.utils;

/**
 * Created by boyinzhang on 4/8/17.
 */
public class GemInfo {
    public int diamond;
    public int emerald;
    public int onyx;
    public int ruby;
    public int sapphire;


    public GemInfo(int numDiamond, int numEmerald,int numOnyx, int numRuby, int numSapphire){
        this.diamond = numDiamond;
        this.emerald = numEmerald;
        this.onyx = numOnyx;
        this.ruby = numRuby;
        this.sapphire = numSapphire;
    }

    public void updateInfo(int deltaDiamond, int deltaEmerald,int deltaOnyx, int deltaRuby, int deltaSapphire){
        this.diamond += deltaDiamond;
        this.emerald += deltaEmerald;
        this.onyx += deltaOnyx;
        this.ruby += deltaRuby;
        this.sapphire += deltaSapphire;
    }
}
