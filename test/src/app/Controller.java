package app;

import javax.swing.*;

import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;

public class Controller implements ActionListener{
    private Model model;
    private View view;
    private JFrame frame = new JFrame("Test");
    Timer timer;

    //Keyboard controls
    private class CustomDispatcher implements KeyEventDispatcher {
        public boolean dispatchKeyEvent (KeyEvent e) {
            if (e.getID() == KeyEvent.KEY_PRESSED) {
                model.setDirection(e.getKeyCode());
                System.out.println(model.getDirection());
                view.updateDirection(model.getDirection());
            }
            return false;
        }
    }

    public Controller (Model _model, View _view) {
        model = _model;
        view = _view;
        view.registerController(this);
        frame.setContentPane(view);
        frame.setSize(150,150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new CustomDispatcher());
        timer = new Timer(500, this);
    }

    public void displayGUI() {
        frame.setVisible(true);
        timer.start();
    }

    public void actionPerformed(ActionEvent e) {
        view.repaint();
    }

}