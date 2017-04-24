package Model;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by yu on 4/8/17.
 */
public class Deck implements Serializable {
    public ArrayList<Card> cards;

    public Deck() {
        cards = new ArrayList<Card>();
    }
}
