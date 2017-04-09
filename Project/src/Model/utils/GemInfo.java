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

    public GemInfo(int numberPerGem){
        this(numberPerGem,numberPerGem,numberPerGem,numberPerGem,numberPerGem);
    }

    public void updateInfo(int deltaDiamond, int deltaEmerald,int deltaOnyx, int deltaRuby, int deltaSapphire){
        this.diamond += deltaDiamond;
        this.emerald += deltaEmerald;
        this.onyx += deltaOnyx;
        this.ruby += deltaRuby;
        this.sapphire += deltaSapphire;
    }

    public static void combineGems(GemInfo currentGems, GemInfo addGems){
        currentGems.diamond += addGems.diamond;
        currentGems.emerald += addGems.emerald;
        currentGems.onyx += addGems.onyx;
        currentGems.ruby += addGems.ruby;
        currentGems.sapphire += addGems.sapphire;
    }

    public static void reduceGems(GemInfo currentGems, GemInfo addGems){
        currentGems.diamond -= addGems.diamond;
        currentGems.emerald -= addGems.emerald;
        currentGems.onyx -= addGems.onyx;
        currentGems.ruby -= addGems.ruby;
        currentGems.sapphire -= addGems.sapphire;
    }
}
