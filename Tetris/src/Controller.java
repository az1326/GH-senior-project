import javax.swing.*;

import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;

public class Controller implements ActionListener{
    private Model model;
    private View view;
    private JFrame frame = new JFrame("Tetris");
    Timer timer;

    //Keyboard Listener
    private class CustomDispatcher implements KeyEventDispatcher {
        public boolean dispatchKeyEvent (KeyEvent e) {
            if (e.getID() == KeyEvent.KEY_PRESSED) {
                /* Handle inputs */
            }
            return false;
        }
    }

    public Controller (Model _model, View _view) {
        model = _model;
        view = _view;
        view.registerController(this);
        frame.setContentPane(view);
        frame.setSize(500, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new CustomDispatcher());
        timer = new Timer(2000, this);
    }

    public void displayGUI() {
        frame.setVisible(true);
        timer.start();
    }

    public void actionPerformed(ActionEvent e) {
        view.updateBoardActive();
        view.updateNext();
        /* Handle tick */
    }

}