package View.utils;

import Model.Card;
import Model.Gem;
import Model.utils.GemInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

/**
 * Created by dajun on 4/17/17.
 */
public class ViewUtils {

    public final static int ratio = 70;

    public final static int WINDOW_WIDTH=20*ratio;
    public final static int WINDOW_HEIGHT=10*ratio;
    public final static int GAME_WIDTH=13*ratio;
    public final static int GAME_HEIGHT=10*ratio;
    public final static int PLAYER_WIDTH=7*ratio;
    public final static int PLAYER_HEIGHT=10*ratio;

    public final static int CARD_WIDTH = ratio*3/2;
    public final static int CARD_HEIGHT = ratio*2;
    public final static int GEM_WIDTH = ratio;
    public final static int GEM_HEIGHT = ratio;
    public final static int NOBLE_WIDTH = ratio*5/4;
    public final static int NOBLE_HEIGHT = ratio*5/4;


    public final static Color customWhite = new Color(246,247,248);
    public final static Color customBlack = new Color(66,51,52);
    public final static Color customGreen = new Color(63,193,83);
    public final static Color customRed = new Color(252,45,46);
    public final static Color customBlue = new Color(40,150,221);

    public final static ArrayList<Color> colorMap= new ArrayList<Color>(Arrays.asList(customWhite,customBlack,customGreen,customRed,customBlue));

    //Set the top part of image to translucent
    public static void addOpaqueLayer(BufferedImage img){
        //50% opacity
        int alpha = 127;
        for(int cx = 0; cx<img.getWidth();cx++ ){
            for(int cy = 0; cy < img.getHeight(); cy++){
                //ARGB(8 bits per channel)
                int color = (alpha << 24) + img.getRGB(cx, cy);
                img.setRGB(cx,cy,color);
            }
        }
    }


    //add a border line to string
    public static void plotStringWithOutline(Graphics2D g2d, String s, int x, int y, int size){
        Font font = new Font("SansSerif",  Font.ITALIC + Font.BOLD, size);
        g2d.setFont(font);
        g2d.setColor(Color.black);
        g2d.drawString(s,x-1,y-1);
        g2d.drawString(s,x-1,y+1);
        g2d.drawString(s,x+1,y-1);
        g2d.drawString(s,x+1,y+1);
        g2d.setColor(Color.white);
        g2d.drawString(s,x,y);
    }


    //add a border line to Circle
    public static void plotCircleWithOutline(Graphics2D g2d, Color fillColor, int x, int y,int d){

        g2d.setColor(fillColor);
        g2d.fillOval(x,y,d,d);
        //if the color is white, use black as the border color
        if(fillColor == customWhite)
            g2d.setColor(customBlack);
        else
            g2d.setColor(customWhite);
        g2d.drawOval(x,y,d,d);

    }

    //add a border line to Rectangle
    public static void plotRectWithOutline(Graphics2D g2d, Color fillColor, int x, int y,int w, int h){

        g2d.setColor(fillColor);
        g2d.fillRect(x,y,w,h);

        //if the color is white, use black as the border color
        if(fillColor == customWhite)
            g2d.setColor(customBlack);
        else
            g2d.setColor(customWhite);
        g2d.drawRect(x,y,w,h);

    }

    public static ImageIcon plotGemButton(Gem gem, int left, Hashtable<String, Image> gemImages){

        BufferedImage buffered = new BufferedImage(GEM_WIDTH, GEM_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = buffered.createGraphics();
        int x = 0;
        int y = 0;
        int r = GEM_WIDTH/2;

        //plot other gem
        Image gemImg = gemImages.get(gem.getGemName());
        g2d.drawImage(gemImg, 0, 0 , null);
        plotStringWithOutline(g2d,Integer.toString(left),x+r/2,y+r*3/2,GEM_WIDTH/4);
/*
        this.setOpaque(false);
        this.setContentAreaFilled(false);
        this.setBorderPainted(false);
        this.setIcon(new ImageIcon(buffered));
*/
        return new ImageIcon(buffered);
    }


    public static ImageIcon plotCardButton(Card card, Hashtable<String, Image> gemImages, Hashtable<String, Image> cardImages) {

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

        return new ImageIcon(buffered);
    }

    public static ImageIcon plotGoldButton(int left, Hashtable<String, Image> gemImages){

        BufferedImage buffered = new BufferedImage(GEM_WIDTH, GEM_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = buffered.createGraphics();
        int x = 0;
        int y = 0;
        int r = GEM_WIDTH/2;

        Image gemImg = gemImages.get("Gold");
        g2d.drawImage(gemImg, 0, 0 , null);
        plotStringWithOutline(g2d,Integer.toString(left),x+r/2,y+r*3/2,GEM_WIDTH/4);

        return new ImageIcon(buffered);
        //this.setOpaque(false);
        //this.setContentAreaFilled(false);
        //this.setBorderPainted(false);
        //this.setIcon(new ImageIcon(buffered));

    }

}
