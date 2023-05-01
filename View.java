import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class View extends JFrame implements Observer {

    private Model model;
    private Controller controller;
    private BoardPanel boardPanel;

    JButton rollDiceButton = new JButton("Roll Dice");
    JButton buyHotelButton = new JButton("Buy Hotel");
    JButton upgradeHotelButton = new JButton("Upgrade Hotel");
    JButton payRentButton = new JButton("Pay Rent");
    JButton nextTurnButton = new JButton("Next Turn");
    JButton exitGameButton = new JButton("Exit Game");
    JButton changeModeButton = new JButton("Change Mode");


    JTextField diceInputField = new JTextField(4);
    JButton cheatButton = new JButton("Cheat");
    
    public View(Model model) {
        this.model = model;

        setTitle("Hotels");
        setSize(900, 956);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initializeComponents();
        setVisible(true);
    }

    private void initializeComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        boardPanel = new BoardPanel(model);
        mainPanel.add(boardPanel, BorderLayout.CENTER);

        getContentPane().add(mainPanel);

        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new FlowLayout());

        actionPanel.add(rollDiceButton);
        actionPanel.add(buyHotelButton);
        actionPanel.add(upgradeHotelButton);
        actionPanel.add(payRentButton);
        actionPanel.add(nextTurnButton);
        actionPanel.add(exitGameButton);
        actionPanel.add(changeModeButton);
        actionPanel.add(diceInputField);
        actionPanel.add(cheatButton);

        upgradeHotelButton.setEnabled(false);
        buyHotelButton.setEnabled(false);
        nextTurnButton.setEnabled(false);
        payRentButton.setEnabled(false);
        diceInputField.setEnabled(false);
        cheatButton.setEnabled(false);

        mainPanel.add(actionPanel, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);
    }


    public void showMessage(String message) {
        boardPanel.setMessage(message);
    }

    public JButton getRollDiceButton() {
        return rollDiceButton;
    }

    public JButton getBuyHotelButton() {
        return buyHotelButton;
    }

    public JButton getUpgradeHotelButton() {
        return upgradeHotelButton;
    }

    public JButton getPayRentButton() {
        return payRentButton;
    }

    public JButton getNextTurnButton() {
        return nextTurnButton;
    }

    public JButton getExitGameButton() {
        return exitGameButton;
    }

    public JButton getChangeModeButton() {
        return changeModeButton;
    }

    public JTextField getDiceInputField() {
        return diceInputField;
    }

    public JButton getCheatButton() {
        return cheatButton;
    }

    public BoardPanel getBoardPanel() {
        return boardPanel;
    }


    @Override
    public void update(Observable observable, Object arg) {
        boardPanel.repaint();
    }
}
