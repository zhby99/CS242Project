package View;

        import Model.Card;
        import Model.Gem;
        import Model.utils.GemInfo;

        import javax.swing.*;

        import java.awt.*;
        import java.awt.image.BufferedImage;
        import java.util.Hashtable;

        import static View.utils.ViewUtils.*;

/**
 * Created by dajun on 4/17/17.
 */

public class CardButton extends JButton{

    public CardButton(Card card, Hashtable<String, Image> gemImages, Hashtable<String, Image> cardImages) {

        //extract the card information
        Gem gem = card.getTargetGem();
        GemInfo gemInfo = card.getDevelopmentCost();
        int score = card.getCardScore();

        BufferedImage buffered = new BufferedImage(CARD_WIDTH, CARD_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = buffered.createGraphics();

        //plot background card
        Image backImg = cardImages.get(gem.getGemName());
        g2d.drawImage(backImg, 0, 0 , null);

        //Reduce the opacity to 50% of the card's top part
        BufferedImage subImage = buffered.getSubimage(0, 0, CARD_WIDTH, CARD_HEIGHT/5); // x, y, width, height
        addOpaqueLayer(subImage);

        //plot score
        if(score != 0){
            plotStringWithOutline(g2d,Integer.toString(score),CARD_WIDTH/10,CARD_HEIGHT*15/100,CARD_WIDTH/5);
        }

        //plot targetGem
        Image bonusImg = gemImages.get(gem.getGemName());
        bonusImg = bonusImg.getScaledInstance(GEM_WIDTH/2,GEM_HEIGHT/2,Image.SCALE_SMOOTH);
        g2d.drawImage(bonusImg,CARD_WIDTH*7/10,0,null);

        //plot development cost
        int x = CARD_WIDTH/20;
        int r = CARD_WIDTH/10;
        int idx = 1;
        for(int i = 1; i <= 5; i++){
            int cost = gemInfo.getByIndex(i);
            if(cost == 0)
                continue;
            int y = CARD_HEIGHT-idx*CARD_HEIGHT/6;
            idx++;
            plotCircleWithOutline(g2d,colorMap.get(i-1),x,y,2*r);
            plotStringWithOutline(g2d,Integer.toString(cost),x+r/2,y+r*3/2,r*3/2);
        }


        this.setOpaque(false);
        this.setContentAreaFilled(false);
        //this.setBorderPainted(false);
        this.setIcon(new ImageIcon(buffered));
    }

}
