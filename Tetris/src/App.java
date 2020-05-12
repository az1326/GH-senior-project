import javax.swing.SwingUtilities;

/**
 * Wrapper/Main class for the project
 */
public class App {
    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                View view = new View();
                Model model = new Model();
                Controller controller = new Controller(model, view);
                controller.displayGUI();
            }
        });
    }
}