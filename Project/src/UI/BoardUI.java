package UI;

import Model.utils.GemInfo;

import java.awt.*;
import javax.swing.*;
import java.io.IOException;

import static Model.utils.GameUtils.*;

/**
 * Created by Yu on 4/16/17.
 */
public class BoardUI {

    private JFrame window;
    private JPanel game;
    private JPanel playerArea;

    private JPanel noblesPanel;
    private JPanel gemsPanel;
    private JPanel cardsPanel;

    private JPanel nobles[];
    private JButton gold;
    private JButton gems[];
    private JButton cards[][];
    private PlayerPanel players[];


    private int eb = 5;
    private int ratio;

    public BoardUI() {
        ratio = 70;
        window = new JFrame("Splendor");
        window.setPreferredSize(new Dimension(16*ratio,9*ratio));
        window.setMaximumSize(new Dimension(16*ratio,9*ratio));
        window.setMinimumSize(new Dimension(16*ratio,9*ratio));

        setLayout();

        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

//        noblesPanel = new JPanel();
//        noblesPanel.setLayout(new BoxLayout(noblesPanel, BoxLayout.X_AXIS));
//        noblesPanel.setSize(16*ratio,ratio);
        nobles = new JPanel[NUM_PLAYER+1];
        for (int i=0;i<NUM_PLAYER+1;i++){
            nobles[i] = new JPanel(new BorderLayout());
            nobles[i].setBounds(150 * (i+1) *ratio/100, 50*ratio/100, ratio,ratio);
            nobles[i].setBackground(Color.black);
//            nobles[i].setSize(ratio,ratio);
//            nobles[i].setLocation(150 * (i+1) *ratio/100, 50*ratio/100);
            game.add(nobles[i]);
        }
        //game.add(noblesPanel, BorderLayout.PAGE_START);

//        gemsPanel = new JPanel();
//        gemsPanel.setLayout(new BoxLayout(gemsPanel, BoxLayout.Y_AXIS));
//        gemsPanel.setSize(625, 100);
        gold = new JButton("gold");
        gold.setBounds(75*ratio/100,200*ratio/100, 10, 10);
//        gold.setLocation(0,0);
//        gemsPanel.add(gold);
        game.add(gold);
        gems = new JButton[5];
        for (int i=0;i<5;i++) {
            gems[i] = new JButton("tmp");
            gems[i].setBounds(75*ratio/100, (825-125*i)*ratio/100, 10, 10);
            //gemsPanel.add(gems[i]);
            game.add(gems[i]);
        }
//        gemsPanel.setLocation(75*ratio/100,200*ratio/100);
//        game.add(gemsPanel);
//        game.add(gemsPanel, BorderLayout.LINE_START);

        //cardsPanel = new JPanel();
        cards = new JButton[NUM_CARD_RANK][NUM_CARD_PER_RANK];
        for (int i=0;i<NUM_CARD_RANK;i++){
            for (int j=0;j<NUM_CARD_PER_RANK;j++) {
                cards[i][j] = new JButton();
                cards[i][j].setBounds((250+150*j)*ratio/100, (200+225*i)*ratio/100, 150*ratio/100,200*ratio/100);
                game.add(cards[i][j]);
            }
        }
        //game.add(cardsPanel);
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

        private void setStatus(int id, int score, boolean indicator){
            status = new JPanel();
            status.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));

            JLabel idLabel = new JLabel(Integer.toString(id),JLabel.CENTER);
            idLabel.setPreferredSize(new Dimension(ratio,ratio*3/NUM_PLAYER));
            status.add(idLabel);

            JLabel scoreLabel = new JLabel(Integer.toString(score),JLabel.CENTER);
            scoreLabel.setPreferredSize(new Dimension(ratio,ratio*3/NUM_PLAYER));
            status.add(scoreLabel);

            JLabel indicatorLabel;
            if(indicator)
                indicatorLabel = new JLabel("true",JLabel.CENTER);
            else
                indicatorLabel = new JLabel("false",JLabel.CENTER);
            indicatorLabel.setPreferredSize(new Dimension(ratio,ratio*3/NUM_PLAYER));
            status.add(indicatorLabel);
        }


        private void setGems(GemInfo gemInfo, int gold){
            gems = new JPanel();
            gems.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
            for(int i = 1; i <= 5; i++){
                int bonus = gemInfo.getByIndex(i);
                JLabel gemLabel = new JLabel(Integer.toString(bonus),JLabel.CENTER);
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
