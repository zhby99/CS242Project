package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static View.utils.ViewUtils.*;

/**
 * Created by dajun on 4/17/17.
 */
public class DeckPanel extends JPanel {

    private File f;
    private BufferedImage image;
    private Image backgroundImage;

    public DeckPanel(String selectedColor){

        //initialize helper function

        f = new File("src/View/img/DeckBack.jpg");
        try {
            image = ImageIO.read(f);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //apply filter
        for(int x = 0; x < image.getWidth(); x++){
            for(int y = 0; y < image.getHeight(); y++){

                Color color = new Color(image.getRGB(x,y));

                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();

                Color newColor;
                switch (selectedColor){
                    case "red": newColor = new Color(red,green/2,blue/2);break;
                    case "green": newColor = new Color(red/2,green,blue/2);break;
                    case "blue": newColor = new Color(red/2,green/2,blue);break;
                    default:newColor = new Color(0,0,0);break;
                }

                image.setRGB(x,y,newColor.getRGB());
            }
        }
        backgroundImage = image.getScaledInstance(CARD_WIDTH,CARD_HEIGHT,Image.SCALE_SMOOTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, this); // see javadoc for more info on the parameters
    }

}

