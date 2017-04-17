package View;

import Model.Player;
import Model.utils.GemInfo;

import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static Model.utils.GameUtils.*;

/**
 * Created by Yu on 4/16/17.
 */
public class BoardUI {

    private JFrame window;
    private JPanel game;
    private JPanel playerArea;

    private JPanel decks[];
    private JPanel nobles[];
    private JButton gold;
    private JButton gems[];
    private JButton cards[][];
    private PlayerPanel players[];
    private JButton collect, reset, buy, reserve;

    private JMenuItem newGame;
    private JMenuItem exit;




    private int ratio;

    public BoardUI() {
        ratio = 70;
        window = new JFrame("Splendor");
        window.setPreferredSize(new Dimension(16*ratio,10*ratio));
        window.setMaximumSize(new Dimension(16*ratio,10*ratio));
        window.setMinimumSize(new Dimension(16*ratio,10*ratio));

        final JMenuBar menuBar = createMenuBar();
        this.window.setJMenuBar(menuBar);

        setLayout();

        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }

    /**
     * The init function to set up the menu bar
     * @return the menu bar to be created
     */
    private JMenuBar createMenuBar(){
        JMenuBar menuBar = new JMenuBar();
        final JMenu fileMenu = new JMenu("File");
        newGame = new JMenuItem("New");
        exit = new JMenuItem("Exit");
        fileMenu.add(newGame);
        fileMenu.add(exit);
        menuBar.add(fileMenu);
        return menuBar;
    }

    /*
    The following methods are used to add listeners to the JMenubar.
     */
    public void addNewGameListener(ActionListener a) {
        newGame.addActionListener(a);
    }

    public void addExitListener(ActionListener a) {
        exit.addActionListener(a);
    }

    public JButton[] getGems(){
        return this.gems;
    }

    public JButton getGold(){
        return this.gold;
    }

    public JButton[][] getCards(){
        return this.cards;
    }

    private void setLayout() {

        game = new JPanel();
        game.setPreferredSize(new Dimension(13*ratio,9*ratio));
        game.setLayout(null);
        setGameBoard();
        window.add(game,BorderLayout.WEST);

        playerArea = new JPanel();
        playerArea.setPreferredSize(new Dimension(3*ratio,9*ratio));
        setPlayerArea();
        window.add(playerArea,BorderLayout.EAST);
    }

    private void setGameBoard() {

        int offset = ratio * 2;
        nobles = new JPanel[NUM_PLAYER+1];
        for (int i=0;i<NUM_PLAYER+1;i++){
            nobles[i] = new JPanel(new BorderLayout());
            nobles[i].setBounds(offset+150 * (i+1) *ratio/100, 50*ratio/100, ratio,ratio);
            nobles[i].setBackground(Color.black);
            game.add(nobles[i]);
        }

        gold = new JButton("gold");
        gold.setBounds(75*ratio/100,200*ratio/100-50, ratio, ratio);

        game.add(gold);
        gems = new JButton[5];
        for (int i=0;i<5;i++) {
            gems[i] = new JButton("tmp");
            gems[i].setBounds(75*ratio/100, (775-125*i)*ratio/100, ratio, ratio);
            game.add(gems[i]);
        }

        decks = new JPanel[NUM_CARD_RANK];
        for(int i = 0 ; i< NUM_CARD_RANK; i++){
            decks[i] = new JPanel();
            //load image
            decks[i].setBounds(offset+ratio, (200+225*i)*ratio/100, 150*ratio/100,200*ratio/100);
            decks[i].setBackground(Color.orange);
            game.add(decks[i]);
        }


        cards = new JButton[NUM_CARD_RANK][NUM_CARD_PER_RANK];
        for (int i=0;i<NUM_CARD_RANK;i++){
            for (int j=0;j<NUM_CARD_PER_RANK;j++) {
                cards[i][j] = new JButton();
                cards[i][j].setBounds(offset+(250+150*j)*ratio/100, (200+225*i)*ratio/100, 150*ratio/100,200*ratio/100);
                game.add(cards[i][j]);
            }
        }

        collect = new JButton("Collect");
        collect.setBounds(11*ratio, 2*ratio, ratio, ratio);
        game.add(collect);

        reset = new JButton("Reset");
        reset.setBounds(11*ratio, 3*ratio, ratio, ratio);
        game.add(reset);

        buy = new JButton("Buy");
        buy.setBounds(11*ratio, 4*ratio, ratio, ratio);
        game.add(buy);

        reserve = new JButton("Reserve");
        reserve.setBounds(11*ratio, 5*ratio, ratio, ratio);
        game.add(reserve);
    }

    private void setPlayerArea() {

        playerArea.setLayout(new BoxLayout(playerArea,BoxLayout.Y_AXIS));
        PlayerPanel players[] = new PlayerPanel[NUM_PLAYER];
        for (int i=0;i<NUM_PLAYER;i++){
            players[i] = new PlayerPanel();
            players[i].setPreferredSize(new Dimension(3*ratio,9*ratio/NUM_PLAYER));

            playerArea.add(players[i]);
        }
    }

    public class PlayerPanel extends JPanel{

        //need the input of controller

        JPanel status;
        JPanel gems;
        JPanel cards;

        private PlayerPanel() {

            //need input of controller
            setStatus(1,5,true);
            setGems(new GemInfo(0),0);
            setCards(new GemInfo(0));

            this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
            this.add(status);
            this.add(gems);
            this.add(cards);
        }

        private PlayerPanel(Player player,boolean current) {

            //need input of controller
            setStatus(player.getId(),player.getScore(),current);
            setGems(new GemInfo(0),0);
            setCards(new GemInfo(0));

            this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
            this.add(status);
            this.add(gems);
            this.add(cards);
        }


        private void setStatus(int id, int score, boolean indicator){

            int width = ratio;
            int height = ratio*3/NUM_PLAYER;

            status = new JPanel();
            status.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));

            JLabel idLabel = new JLabel(Integer.toString(id),JLabel.CENTER);
            idLabel.setPreferredSize(new Dimension(width,height));
            status.add(idLabel);

            JLabel scoreLabel = new JLabel(Integer.toString(score),JLabel.CENTER);
            scoreLabel.setPreferredSize(new Dimension(width,height));
            status.add(scoreLabel);

            JLabel indicatorLabel;
            BufferedImage buffered = new BufferedImage(width,height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = buffered.createGraphics();
            if(indicator) {
                g2d.setColor(Color.GREEN);
            }
            else
                g2d.setColor(Color.RED);
            g2d.fillOval(width*3/8,height*3/8,width/4,width/4);
            ImageIcon icon= new ImageIcon(buffered);
            indicatorLabel = new JLabel(icon, JLabel.CENTER);
            indicatorLabel.setPreferredSize(new Dimension(ratio,ratio*3/NUM_PLAYER));
            status.add(indicatorLabel);
        }


        private void setGems(GemInfo gemInfo, int gold){
            gems = new JPanel();
            gems.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
            for(int i = 1; i <= 5; i++){
                int gem = gemInfo.getByIndex(i);
                JLabel gemLabel = new JLabel(Integer.toString(gem),JLabel.CENTER);
                gemLabel.setPreferredSize(new Dimension(ratio/2,ratio*3/NUM_PLAYER));
                gems.add(gemLabel);
            }
            JLabel goldLabel = new JLabel(Integer.toString(gold),JLabel.CENTER);
            goldLabel.setPreferredSize(new Dimension(ratio/2,ratio*3/NUM_PLAYER));
            gems.add(goldLabel);
            gems.add(goldLabel);
        }


        private void setCards(GemInfo gemInfo){
            cards = new JPanel();
            cards.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
            for(int i = 1; i <= 5; i++){
                int bonus = gemInfo.getByIndex(i);
                JLabel bonusLabel = new JLabel(Integer.toString(bonus),JLabel.CENTER);
                bonusLabel.setPreferredSize(new Dimension(ratio*3/5,ratio*3/NUM_PLAYER));
                cards.add(bonusLabel);
            }
        }

    }



    public static void main(String[] args) throws IOException {
        new BoardUI();
    }
}