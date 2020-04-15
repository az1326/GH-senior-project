package app;

import javax.swing.*;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class View {
    private JFrame frame = new JFrame("Test");
    private KeyListener keyListener = new KeyListener() {
        public void keyPressed(KeyEvent e) {
            System.out.println(KeyEvent.getKeyText(e.getKeyCode()));
        }

        public void keyReleased(KeyEvent e) {}
        public void keyTyped(KeyEvent e) {}
    };


    public View() {
        frame.setSize(200,300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(keyListener);
        frame.setVisible(true);
    }


}