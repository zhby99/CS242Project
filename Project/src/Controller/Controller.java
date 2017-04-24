package Controller;

import Game.Game;
import Model.*;
import Model.utils.GemInfo;
import View.BoardUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import static Model.utils.GameUtils.*;

/**
 * Created by boyinzhang on 4/17/17.
 */
public class Controller{
    public Game game;
    public BoardUI boardUI;
    private Card selectedCard;
    private GemInfo currentGemInfo;
    private ObjectOutputStream out;
    private int id;

    public Controller(){
        this.game = new Game();
        this.boardUI = new BoardUI(game,0);
        this.currentGemInfo = new GemInfo(0);
        this.id = 0;
        addMenuItemListener();
        addGemsListener();
        addCardListeners();
        addFunctionalListeners();
    }

    public Controller(Game game, ObjectOutputStream out, int id){
        this.game = game;
        this.out = out;
        this.boardUI = new BoardUI(game,id);
        this.currentGemInfo = new GemInfo(0);
        this.id = id;
        addMenuItemListener();
        addGemsListener();
        addCardListeners();
        addFunctionalListeners(true);
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
                requestServer("RESTART");
                //newGame();
            }
        });
    }

    private void newGame() {
        game = new Game();
        boardUI.updateByGame(game);
        currentGemInfo = new GemInfo(0);
        selectedCard = null;
    }

    /**
     * for JMenubar Exit
     */
    private void addExitListener() {
        boardUI.addExitListener(new ActionListener(){
            public void actionPerformed(ActionEvent event) {
                requestServer("EXIT");
                System.exit(0);
            }
        });
    }

    /**
     * Add listeners of gems.
     */
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

    /**
     * Add listeners to the cards on board and reserved cards
     */
    private void addCardListeners(){
        for(int i = 0; i < NUM_CARD_RANK; i++){
            for(int j = 0; j < NUM_CARD_PER_RANK; j ++){
                int finalI = i;
                int finalJ = j;
                this.boardUI.getCards()[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        selectedCard = game.getGameBoard().getCards()[finalI][finalJ];
                        selectedCard.setPosition(finalI,finalJ);
                    }
                });
            }
        }
        for(int i = 0; i < NUM_PLAYER; i++){
            for(int j = 0; j < MAX_RESERVED_CARDS; j++){
                int finalI = i;
                int finalJ = j;
                this.boardUI.getPlayers()[i].getReservedCards()[j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        selectedCard = game.getPlayers()[finalI].getReserves().get(finalJ);
                        selectedCard.setPosition(finalI,finalJ);
                    }
                });
            }
        }
    }

    /**
     * Add listeners to the four major operations
     */
    private void addFunctionalListeners(){
        addResetLisenter(false);
        addCollectListener(false);
        addBuyListener(false);
        addReserveListener(false);
    }

    private void addFunctionalListeners(boolean serverMode){
        addResetLisenter(true);
        addCollectListener(true);
        addBuyListener(true);
        addReserveListener(true);
    }

    /**
     * Add listener to the reset button
     */

    private void addResetLisenter(boolean serverMode){
        this.boardUI.getReset().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(game.getCurrentPlayer().getPlayerId() != id+1 && serverMode) {
                    JOptionPane.showMessageDialog(null, "Wait for your opponent's move",
                            "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                currentGemInfo.reset();
            }
        });
    }

    /**
     * Add listener to the collect button
     */

    private void addCollectListener(boolean serverMode){
        this.boardUI.getCollect().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(game.getCurrentPlayer().getPlayerId() != id+1 && serverMode) {
                    JOptionPane.showMessageDialog(null, "Wait for your opponent's move",
                            "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                boolean status = game.getCurrentPlayer().collectGems(currentGemInfo);
                if(!status){
                    JOptionPane.showMessageDialog(null, "Invalid Collection! Please make another try!",
                            "Warning", JOptionPane.WARNING_MESSAGE);
                    currentGemInfo.reset();
                }
                else{
                    currentGemInfo.reset();
                    //if (checkEnd()) return;
                    game.turnToNextPlayer();

                    requestServer("COLLECT");

                    //boardUI.updateByGame(game);
                }

            }
        });
    }

    /**
     * Add listener to the buy button
     */
    private void addBuyListener(boolean serverMode){
        this.boardUI.getBuy().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(game.getCurrentPlayer().getPlayerId() != id+1 && serverMode) {
                    JOptionPane.showMessageDialog(null, "Wait for your opponent's move",
                            "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
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
                    Card newCard = game.getGameBoard().getNewCard(selectedCard.getPosition()[0]);
                    game.getGameBoard().setCardOnBoard(newCard, selectedCard.getPosition());
                    selectedCard = null;
                    game.getCurrentPlayer().recruitAvailableNobles();
                    if (checkEnd()) {

                        game.turnToNextPlayer();
                        requestServer("VICTORY");
                        return;
                    }
                    game.turnToNextPlayer();
                    requestServer("PURCHASE");

                    //boardUI.updateByGame(game);
                }

            }
        });
    }


    private boolean checkEnd() {

        /*
        if(game.getCurrentPlayer().getPlayerId() == NUM_PLAYER){
            int numberOfWining = game.checkEndofGame();
            if(numberOfWining == 1){
                for(Player player : game.getPlayers()){
                    if(player.hasWon()){
                        int replyNewGame = JOptionPane.showConfirmDialog(null,
                                "Player " +player.getPlayerId()+" win! Do you want to start a new game","Yes?",JOptionPane.YES_NO_OPTION);
                        if(replyNewGame==JOptionPane.YES_OPTION) {
                            newGame();
                            return true;
                        }
                    }
                }
            }
            else if(numberOfWining >1){
                int replyNewGame = JOptionPane.showConfirmDialog(null,
                        "Tie! Do you want to start a new game","Yes?",JOptionPane.YES_NO_OPTION);
                if(replyNewGame==JOptionPane.YES_OPTION) {
                    newGame();
                    return true;
                }
            }
        }
        */
        if(game.currentPlayer.hasWon())
            return true;


        return false;
    }

    /**
     * Add listener to the reserve button
     */
    private void addReserveListener(boolean serverMode){
        this.boardUI.getReserve().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Reserve");
                if(game.getCurrentPlayer().getPlayerId() != id+1 && serverMode) {
                    JOptionPane.showMessageDialog(null, "Wait for your opponent's move",
                            "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
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
                    Card newCard = game.getGameBoard().getNewCard(selectedCard.getPosition()[0]);
                    game.getGameBoard().setCardOnBoard(newCard, selectedCard.getPosition());
                    selectedCard = null;
                    //if (checkEnd()) return;
                    game.turnToNextPlayer();
                    //boardUI.window.setEnabled(false);
                    requestServer("RESERVE");
                    System.out.println("Reserve request made");

                    //boardUI.updateByGame(game);
                }
            }
        });
    }


    void setPanelEnabled(JPanel panel, Boolean isEnabled) {
        panel.setEnabled(isEnabled);

        Component[] components = panel.getComponents();

        for(int i = 0; i < components.length; i++) {
            if(components[i].getClass().getName() == "javax.swing.JPanel") {
                setPanelEnabled((JPanel) components[i], isEnabled);
            }

            components[i].setEnabled(isEnabled);
        }
    }

    private void requestServer(String msg){
        try {
            out.writeObject(msg);
            out.writeObject(game);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void voteForNewGame(){
        int replyNewGame = JOptionPane.showConfirmDialog(null,
                "Do you want to start a new game","Yes?",JOptionPane.YES_NO_OPTION);
        if(replyNewGame==JOptionPane.YES_OPTION) {
            requestServer("AGREE");
        }
        else{
            requestServer("DECLINE");
        }
    }

    public static void main(String[] args) throws IOException {
        new Controller();
    }
}
