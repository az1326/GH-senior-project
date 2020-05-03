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
                if (e.getKeyCode() == 32) { //Spacebar
                    model.generate();
                    updateBoard();
                } else if (e.getKeyCode() == 38) { //Up arrow
                    model.shift(Model.Direction.UP);
                    updateBoard();
                } else if (e.getKeyCode() == 40) { //Down arrow
                    model.shift(Model.Direction.DOWN);
                    updateBoard();
                } else if (e.getKeyCode() == 39) { //Right arrow
                    model.shift(Model.Direction.RIGHT);
                    updateBoard();
                } else if (e.getKeyCode() == 37) { //Left arrow
                    model.shift(Model.Direction.LEFT);
                    updateBoard();
                } else if (e.getKeyCode() == 90) { //z
                    model.rotate(false);
                    updateBoard();
                } else if (e.getKeyCode() == 88) { //x
                    model.rotate(true);
                    updateBoard();
                }
                else {
                    System.out.println(e.getKeyCode());
                }
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
        timer = new Timer(500, this);
    }

    public void displayGUI() {
        frame.setVisible(true);
        timer.start();
    }

    public void updateBoard() {
        view.updateBoard(model.getBackgroundPieces(), model.getActivePiece());
    }

    public void actionPerformed(ActionEvent e) {
        model.tick();
        /* Handle tick */
    }

}