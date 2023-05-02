import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BoardPanel extends JPanel {
    private Model model;
    private List<String> messages;

    public BoardPanel(Model model) {
        this.model = model;
        this.messages = new ArrayList<>();
        setPreferredSize(new Dimension(900, 956));
    }

    public void setMessage(String message) {
        messages.add(message);
        if (messages.size() > 5) {
            messages.remove(0);
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    int numSpaces = 40; // The number of spaces on the board
    int spaceSize = 80; // The size of each space

    for (int i = 0; i < numSpaces; i++) {
        Space space = model.getSpace(i);
        Hotel hotel = space.getHotel();
        
        // Calculate the position of each space
        int x, y;

        if (i < 10) {
            x = getWidth() - spaceSize * (i + 1);
            y = getHeight() - spaceSize;
        } else if (i < 20) {
            x = 0;
            y = getHeight() - spaceSize * (i - 10 + 1);
        } else if (i < 30) {
            x = spaceSize * (i - 20);
            y = 0;
        } else {
            x = getWidth() - spaceSize;
            y = spaceSize * (i - 30);
        }

        // Draw the rectangle for the space
        g.drawRect(x, y, spaceSize, spaceSize);

        // Draw the hotel name, value, and stars if a hotel is present
        if (hotel != null) {
            g.drawString(hotel.getName(), x + 10, y + 20);
            g.drawString("Value: " + hotel.getValue(), x + 10, y + 35);
            g.drawString("Stars: " + hotel.getStars(), x + 10, y + 50);

            // Draw the owner name if an owner is present
            String ownerName = hotel.getOwnerName();
            if (ownerName != null){
                g.drawString("Owner: " + hotel.getOwnerName(), x + 10, y + 65);
            }
        }
    }

    // Draw the mode name
    int modeX = 240;
    int modeY = 100;
    g.setColor(Color.BLACK);
    g.drawString("Mode: " + model.getMode(), modeX, modeY);

    // Draw the player status
    int playerX = 660;
    int playerY = 100;
    g.setColor(Color.BLACK);
    for (Player player : model.getPlayers()) {
        g.drawString(player.getName() + ": has: " + player.getMoney() + " pounds", playerX, playerY);
        playerY += 20;
    }

    // Draw the current player
    int currentPlayerX = 660;
    int currentPlayerY = 140;
    g.setColor(Color.BLACK);
    g.drawString("Current Player: " + model.getCurrentPlayer().getName(), currentPlayerX, currentPlayerY);

    // Draw the turn counter
    int turncounterX = 120;
    int turncounterY = 100;
    g.setColor(Color.BLACK);
    g.drawString("Turn: " + model.getTurn(), turncounterX, turncounterY);

    // Draw the messages
    int messageX = 120;
    int messageY = 120;
    g.setColor(Color.BLACK);
    for (String message : messages) {
        g.drawString(message, messageX, messageY);
        messageY += 20;
    }
}

}
