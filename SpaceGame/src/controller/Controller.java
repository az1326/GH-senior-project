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
        public void mouseDragged(MouseEvent e) {
            model.updateMouseLocation(e.getPoint());
        }
        public void mousePressed(MouseEvent e) {
            if (model.getGameStatus() == Model.GameState.IN_PROGRESS)
                model.fireLaser();
        }
        public void mouseClicked(MouseEvent e) {
            if (model.getGameStatus() == Model.GameState.GAME_OVER) {
                model.reset();
                view.updateViewReset();
            }
            else if (model.getGameStatus() == Model.GameState.RESET) {
                timer.start();
                model.start();
            }
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
    }

    public void actionPerformed(ActionEvent e) {
        model.tick();
        if (model.getGameStatus() == Model.GameState.IN_PROGRESS) {
            view.updateViewInProgress(model.getAsteroids(), model.getLasers(), model.getShip(),
            model.getPickupLocation(), model.getPickupType(), model.getShipHealth(), model.getBaseHealth(),
            model.getAsteroidsDestroyed(), model.getFireStatus() == Model.FireState.RAPID);
        } else if (model.getGameStatus() == Model.GameState.GAME_OVER) {
            timer.stop();
            view.updateViewGameOver(model.getShipHealth(), model.getBaseHealth());
        }
    }

}