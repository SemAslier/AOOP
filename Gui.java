import javax.swing.*;

public class Gui {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Model model = new Model(0);
            View view = new View(model);
            Controller controller = new Controller(model, view);

            model.addObserver(view);
            view.setVisible(true);
        });
    }
}
