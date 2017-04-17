package View;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Hashtable;

import static View.utils.ViewUtils.GEM_HEIGHT;
import static View.utils.ViewUtils.GEM_WIDTH;
import static View.utils.ViewUtils.plotStringWithOutline;

/**
 * Created by dajun on 4/17/17.
 */
public class GoldButton extends JButton {

    public GoldButton(int left, Hashtable<String, Image> gemImages){

        BufferedImage buffered = new BufferedImage(GEM_WIDTH, GEM_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = buffered.createGraphics();
        int x = 0;
        int y = 0;
        int r = GEM_WIDTH/2;

        Image gemImg = gemImages.get("Gold");
        g2d.drawImage(gemImg, 0, 0 , null);
        plotStringWithOutline(g2d,Integer.toString(left),x+r/2,y+r*3/2,GEM_WIDTH/4);

        this.setOpaque(false);
        this.setContentAreaFilled(false);
        //this.setBorderPainted(false);
        this.setIcon(new ImageIcon(buffered));

    }
}