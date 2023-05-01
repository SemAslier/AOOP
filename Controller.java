import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class Controller {

    private Model model;
    private View view;
    int dice = -1;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        initController();
    }

    private void initController(){

        view.rollDiceButton.addActionListener(e -> rollDice());
        view.buyHotelButton.addActionListener(e -> buyHotel());
        view.upgradeHotelButton.addActionListener(e -> upgradeHotel());
        view.nextTurnButton.addActionListener(e -> nextTurn());
        view.payRentButton.addActionListener(e -> payRent());
        view.exitGameButton.addActionListener(e -> exitGame());
        view.changeModeButton.addActionListener(e -> changeMode());
        view.cheatButton.addActionListener(e -> cheatMove());


    }

    public void rollDice(){
        int dice = model.rollDice();
        handlePlayerMove(dice);
    }

    private void cheatMove(){
        try {
            dice = Integer.parseInt(view.getDiceInputField().getText());
            if (dice >= 1 && dice <= 12) {
                dice = Integer.parseInt(view.getDiceInputField().getText());
                handlePlayerMove(dice);
                view.cheatButton.setEnabled(false);
                view.diceInputField.setEnabled(false);
                model.setModeNormal();
            } else {
                view.showMessage("Please enter a valid number between 1 and 12.");
            }
        } catch (NumberFormatException ex) {
            view.showMessage("Please enter a valid number between 1 and 12.");
        }
    }


    public void handlePlayerMove(int dice) {
        model.movePlayer(dice);
        Player currentPlayer = model.getCurrentPlayer();
        String currentPlayerName = currentPlayer.getName();
        Player passivePlayer = model.getPassivePlayer();
        int currentSpace = currentPlayer.getPosition();
        Hotel currentHotel = model.getSpace(currentSpace).getHotel();
        Space hasHotel = model.getSpace(currentSpace);

        view.rollDiceButton.setEnabled(false);
        view.changeModeButton.setEnabled(false);
        view.cheatButton.setEnabled(false);
        view.diceInputField.setEnabled(false);
        view.nextTurnButton.setEnabled(true);
        view.showMessage(currentPlayerName + " you rolled a " + dice);

        if (currentHotel == null){
            view.showMessage((currentPlayerName + " landed on " + hasHotel));
        }

        if (currentHotel != null && currentHotel.getOwner() == null) {
            view.showMessage(currentPlayerName + " landed on " + currentHotel);
            view.showMessage("You can buy the hotel or go next turn");
            view.buyHotelButton.setEnabled(true);
        }
        if (currentHotel != null && currentHotel.getOwner() == currentPlayer) {
            view.showMessage(currentPlayerName + " landed on " + currentHotel);
            view.showMessage("You can upgrade the hotel or go next turn");
            view.upgradeHotelButton.setEnabled(true);
        }
        if (currentHotel != null && currentHotel.getOwner() == passivePlayer) {
            view.showMessage(currentPlayerName + " landed on " + currentHotel);
            int rent = model.calculateRent();
            view.showMessage("You must pay " + rent + " rent");
            view.payRentButton.setEnabled(true);
            view.nextTurnButton.setEnabled(false);
        }
    }


    public void buyHotel() {
        model.buyHotel();
        view.showMessage("Hotel purchased!");
        view.buyHotelButton.setEnabled(false);
        view.upgradeHotelButton.setEnabled(true);

    }

    private void upgradeHotel() {
           
        Player currentPlayer = model.getCurrentPlayer();
        int currentSpace = currentPlayer.getPosition();
        Hotel currentHotel = model.getSpace(currentSpace).getHotel();

        if (currentHotel.getStars() < 5){
            model.upgradeHotel();
            view.showMessage("Hotel upgraded!");

        } else{
            view.showMessage("This hotel has reached maximum stars");
            view.getUpgradeHotelButton().setEnabled(false);
        }

    }
    
    private void changeMode() {
        if (model.getModeName() == "normal"){
            model.setModeCheat();
            view.diceInputField.setEnabled(true);
            view.cheatButton.setEnabled(true);
            view.showMessage("Cheat mode activated!");
        } else{
            model.setModeNormal();
            view.diceInputField.setEnabled(false);
            view.cheatButton.setEnabled(false);
            view.showMessage("Normal mode activated!");
        }
    }
    

    private void payRent() {
        model.payRent();
        view.showMessage("Rent paid!");
        view.payRentButton.setEnabled(false);
        view.nextTurnButton.setEnabled(true);
    }

    private void nextTurn() {
        model.startNextTurn();
        view.rollDiceButton.setEnabled(true);
        view.buyHotelButton.setEnabled(false);
        view.upgradeHotelButton.setEnabled(false);
        view.nextTurnButton.setEnabled(false);
        view.changeModeButton.setEnabled(true);
        view.cheatButton.setEnabled(true);
        view.diceInputField.setEnabled(true);
    }

    private void exitGame() {
        System.exit(0);
    }
}

