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

    public void enableCheatButton(){
        cheatButton.setEnabled(true);
    }

    public void disableCheatButton(){
        cheatButton.setEnabled(false);
    }

    public void enableDiceInputField(){
        diceInputField.setEnabled(true);
    }

    public void disableDiceInputField(){
        diceInputField.setEnabled(false);
    }

    public void enableRollDiceButton(){
        rollDiceButton.setEnabled(true);
    }

    public void disableRollDiceButton(){
        rollDiceButton.setEnabled(false);
    }

    public void enableBuyHotelButton(){
        buyHotelButton.setEnabled(true);
    }

    public void disableBuyHotelButton(){
        buyHotelButton.setEnabled(false);
    }

    public void enableUpgradeHotelButton(){
        upgradeHotelButton.setEnabled(true);
    }

    public void disableUpgradeHotelButton(){
        upgradeHotelButton.setEnabled(false);
    }

    public void enablePayRentButton(){
        payRentButton.setEnabled(true);
    }

    public void disablePayRentButton(){
        payRentButton.setEnabled(false);
    }

    public void enableNextTurnButton(){
        nextTurnButton.setEnabled(true);
    }

    public void disableNextTurnButton(){
        nextTurnButton.setEnabled(false);
    }

    public void enableExitGameButton(){
        exitGameButton.setEnabled(true);
    }

    public void disableExitGameButton(){
        exitGameButton.setEnabled(false);
    }

    public void enableChangeModeButton(){
        changeModeButton.setEnabled(true);
    }

    public void disableChangeModeButton(){
        changeModeButton.setEnabled(false);
    }

    public void setModeGameOver(){
        rollDiceButton.setEnabled(false);
        buyHotelButton.setEnabled(false);
        upgradeHotelButton.setEnabled(false);
        payRentButton.setEnabled(false);
        nextTurnButton.setEnabled(false);
        exitGameButton.setEnabled(true);
        changeModeButton.setEnabled(false);
        diceInputField.setEnabled(false);
        cheatButton.setEnabled(false);
    }

    public void setModeRollDice(){
        rollDiceButton.setEnabled(true);
        buyHotelButton.setEnabled(false);
        upgradeHotelButton.setEnabled(false);
        payRentButton.setEnabled(false);
        nextTurnButton.setEnabled(false);
        exitGameButton.setEnabled(true);
        changeModeButton.setEnabled(true);
        diceInputField.setEnabled(false);
        cheatButton.setEnabled(false);
    }


    @Override
    public void update(Observable observable, Object arg) {
        boardPanel.repaint();
    }
}
