package controller;

import model.*;
import view.*;

import javax.swing.*;

import javax.swing.event.MouseInputAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener{
    private Model model;
    private View view;
    private JFrame frame = new JFrame("Space Game");
    Timer timer;

    //MouseListener
    private class CustomMouseListener extends MouseInputAdapter {
        public void mouseMoved(MouseEvent e) {
            model.updateMouseLocation(e.getPoint());
        }
        public void mouseReleased(MouseEvent e) {
            model.fireLaser();
        }
    }

    public Controller (Model _model, View _view) {
        model = _model;
        view = _view;
        CustomMouseListener l = new CustomMouseListener();
        view.getField().addMouseListener(l);
        view.getField().addMouseMotionListener(l);
        frame.setContentPane(view);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        timer = new Timer(20, this);
    }

    public void displayGUI() {
        frame.setVisible(true);
        timer.start();
        model.start();
    }

    public void actionPerformed(ActionEvent e) {
        model.tick();
        view.updateField(model.getAsteroids(), model.getLasers(), model.getShip());
    }

}