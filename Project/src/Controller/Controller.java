package Controller;

import Game.Game;
import Model.Card;
import Model.Player;
import Model.utils.GemInfo;
import View.BoardUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static View.BoardUI.*;

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
        addMenuItemListener();
        addGemsListener();
        addCardListeners();
        addFunctionalListeners();
    }

    /**
     * Helper function to help initialize the gui
     */
    private void addMenuItemListener(){
        addNewGameListener();
        addExitListener();
    }

    /**
     * for JMenubar New
     */
    private void addNewGameListener() {
        boardUI.addNewGameListener(new ActionListener(){
            public void actionPerformed(ActionEvent event) {
                game = new Game();
                boardUI = new BoardUI();
                currentGemInfo = new GemInfo(0);
                selectedCard = null;
            }
        });
    }

    /**
     * for JMenubar Exit
     */
    private void addExitListener() {
        boardUI.addExitListener(new ActionListener(){
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
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
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 3; j++){
                int finalI = i;
                int finalJ = j;
                this.boardUI.getPlayers()[i].getReservedCards()[j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        selectedCard = game.getPlayers()[finalI].getReserves().get(finalJ);
                    }
                });
            }
        }
    }

    private void addFunctionalListeners(){
        addResetLisenter();
        addCollectListener();
        addBuyListener();
        addReserveListener();
    }

    private void addResetLisenter(){
        this.boardUI.getReset().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentGemInfo.reset();
            }
        });
    }

    private void addCollectListener(){
        this.boardUI.getCollect().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean status = game.getCurrentPlayer().collectGems(currentGemInfo);
                if(!status){
                    JOptionPane.showMessageDialog(null, "Invalid Collection! Please make another try!",
                            "Warning", JOptionPane.WARNING_MESSAGE);
                    currentGemInfo.reset();
                }
                else{
                    currentGemInfo.reset();
                    game.turnToNextPlayer();
                    //Todo : update UI
                }

            }
        });
    }

    private void addBuyListener(){
        this.boardUI.getBuy().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(selectedCard == null){
                    JOptionPane.showMessageDialog(null, "Must select one card!",
                            "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                boolean status = game.getCurrentPlayer().buyCard(selectedCard,selectedCard.isReserved());
                if(!status){
                    JOptionPane.showMessageDialog(null, "Cannot buy that card! Please make another try!",
                            "Warning", JOptionPane.WARNING_MESSAGE);
                    selectedCard = null;
                }
                else{
                    selectedCard = null;
                    game.getCurrentPlayer().recruitAvailableNobles();
                    if(game.getCurrentPlayer().getId() == 4){
                        int numberOfWining = game.checkEndofGame();
                        if(numberOfWining == 1){
                            for(Player player : game.getPlayers()){
                                if(player.hasWon()){
                                    int replyNewGame = JOptionPane.showConfirmDialog(null,
                                            "Player " +player.getId()+" win! Do you want to start a new game","Yes?",JOptionPane.YES_NO_OPTION);
                                    if(replyNewGame==JOptionPane.YES_OPTION) {
                                        //Todo: new game
                                    }
                                }
                            }
                        }
                        else if(numberOfWining >1){
                            int replyNewGame = JOptionPane.showConfirmDialog(null,
                                    "Tie! Do you want to start a new game","Yes?",JOptionPane.YES_NO_OPTION);
                            if(replyNewGame==JOptionPane.YES_OPTION) {
                                //Todo: new game
                            }
                        }
                    }
                    game.turnToNextPlayer();
                    //Todo : update UI
                }

            }
        });
    }

    private void addReserveListener(){
        this.boardUI.getReserve().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(selectedCard == null){
                    JOptionPane.showMessageDialog(null, "Must select one card!",
                            "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                boolean status = game.getCurrentPlayer().reserveCard(selectedCard);
                if(!status){
                    JOptionPane.showMessageDialog(null, "Cannot reserve that card! Please make another try!",
                            "Warning", JOptionPane.WARNING_MESSAGE);
                    selectedCard = null;
                }
                else{
                    selectedCard = null;
                    game.turnToNextPlayer();
                    //Todo : update UI
                }
            }
        });
    }


    public static void main(String[] args) throws IOException {
        new Controller();
    }
}
