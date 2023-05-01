import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class Controller {

    private Model model;
    private View view;

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


    }

    public void rollDice() {

        int dice = model.rollDice();
        model.movePlayer(dice);
        Player currentPlayer = model.getCurrentPlayer();
        Player passiPlayer = model.getPassivePlayer();
        int currentSpace = currentPlayer.getPosition();
        Hotel currentHotel = model.getSpace(currentSpace).getHotel();
        Space hasHotel = model.getSpace(currentSpace);

        view.rollDiceButton.setEnabled(false);
        view.nextTurnButton.setEnabled(true);
        view.showMessage(currentPlayer + "you rolled a " + dice);

        if (currentHotel == null){
            view.showMessage(("Player " + currentPlayer + " landed on " + hasHotel));
        }

        if (currentHotel != null && currentHotel.getOwner() == null) {
            view.showMessage("Player " + currentPlayer + " landed on " + currentHotel);
            view.showMessage("You can buy the hotel or go next turn");
            view.buyHotelButton.setEnabled(true);
        }
        if (currentHotel != null && currentHotel.getOwner() == currentPlayer) {
            view.showMessage("Player " + currentPlayer + " landed on " + currentHotel);
            view.showMessage("You can upgrade the hotel or go next turn");
            view.upgradeHotelButton.setEnabled(true);
        }
        if (currentHotel != null && currentHotel.getOwner() == passiPlayer) {
            view.showMessage("Player " + currentPlayer + " landed on " + currentHotel);
            int rent = model.calculateRent();
            view.showMessage("You must pay" + rent + " rent");
            view.payRentButton.setEnabled(true);
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
    

    private void payRent() {
        model.payRent();
        view.showMessage("Rent paid!");
        view.payRentButton.setEnabled(false);
    }

    private void nextTurn() {
        model.startNextTurn();
        view.rollDiceButton.setEnabled(true);
        view.buyHotelButton.setEnabled(false);
        view.upgradeHotelButton.setEnabled(false);
        view.nextTurnButton.setEnabled(false);
    }

    private void exitGame() {
        System.exit(0);
    }
}

