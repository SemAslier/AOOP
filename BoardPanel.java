import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel {
    private Model model;

    public BoardPanel(Model model) {
        this.model = model;
        setPreferredSize(new Dimension(900, 956));
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
            g.drawString("Owner: "+ hotel.getOwner(), x + 10, y + 65);
        }
    }
}

}
