package Controller;

import Game.Game;
import Model.Card;
import Model.utils.GemInfo;
import View.BoardUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by boyinzhang on 4/17/17.
 */
public class Controller {
    private Game game;
    private BoardUI boardUI;
    private Card selectedCard;
    private GemInfo currentGemInfo;

    public Controller(){
        this.game = new Game();
        this.boardUI = new BoardUI();
        this.currentGemInfo = new GemInfo(0);
        addGemsListener();
        addCardListeners();
    }


    private void addGemsListener(){

        this.boardUI.getGems()[4].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentGemInfo.updateInfo(1,0,0,0,0);
            }
        });

        this.boardUI.getGems()[3].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentGemInfo.updateInfo(0,1,0,0,0);
            }
        });

        this.boardUI.getGems()[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentGemInfo.updateInfo(0,0,1,0,0);
            }
        });

        this.boardUI.getGems()[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentGemInfo.updateInfo(0,0,0,1,0);
            }
        });

        this.boardUI.getGems()[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentGemInfo.updateInfo(0,0,0,0,1);
            }
        });
    }

    private void addCardListeners(){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 4; j ++){
                int finalI = i;
                int finalJ = j;
                this.boardUI.getCards()[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        selectedCard = game.getGameBoard().getCards()[finalI][finalJ];
                    }
                });
            }
        }
    }


    public static void main(String[] args) throws IOException {
        new Controller();
    }
}
