package View;

import Model.Noble;
import Model.utils.GemInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Hashtable;

import static View.utils.ViewUtils.*;

/**
 * Created by dajun on 4/17/17.
 */
public class NoblePanel extends JPanel {

    private Image nobleImage;

    public NoblePanel(Noble noble, Hashtable<String, Image> nobleImages) {

        //extract the noble information
        GemInfo gemInfo = noble.getThreshold();
        int score = noble.getScore();

        BufferedImage buffered = new BufferedImage(NOBLE_WIDTH, NOBLE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = buffered.createGraphics();

        //plot background card
        Image backImg = null;
        if(Math.random() < 0.5)
            backImg = nobleImages.get("Male");
        else
            backImg = nobleImages.get("Female");
        g2d.drawImage(backImg, 0, 0 , null);


        //Reduce the opacity to 50% of the card's left part
        BufferedImage subImage = buffered.getSubimage(0, 0, NOBLE_WIDTH*3/10, NOBLE_HEIGHT); // x, y, width, height
        addOpaqueLayer(subImage);

        //plot score
        plotStringWithOutline(g2d,Integer.toString(score),NOBLE_WIDTH/20,NOBLE_HEIGHT/4,NOBLE_WIDTH/4);

        //plot threshold cost
        int d = NOBLE_WIDTH/5;
        int x = NOBLE_WIDTH/20;
        int idx = 1;
        for(int i = 1; i <= 5; i++){
            int cost = gemInfo.getByIndex(i);
            if(cost == 0)
                continue;
            int y = NOBLE_HEIGHT-x-idx*d;
            idx++;
            plotRectWithOutline(g2d,COLOR_MAP.get(i-1),x,y,d,d);
            plotStringWithOutline(g2d,Integer.toString(cost),x+d/4,y+d*3/4,d*3/4);
        }

        nobleImage = buffered;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(nobleImage, 0, 0, this); // see javadoc for more info on the parameters
    }

}
