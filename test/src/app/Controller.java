package app;

import javax.swing.*;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;

public class Controller {
    private Model model;
    private View view;
    private JFrame frame = new JFrame("Test");
    private class CustomDispatcher implements KeyEventDispatcher {
        public boolean dispatchKeyEvent (KeyEvent e) {
            if (e.getID() == KeyEvent.KEY_PRESSED) {
                System.out.println(KeyEvent.getKeyText(e.getKeyCode()));
            }
            return false;
        }
    }

    public Controller (Model _model, View _view) {
        model = _model;
        view = _view;
        frame.add(view.getPanel());
        frame.setSize(200,300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new CustomDispatcher());
        frame.setVisible(true);
    }

}