package controller;

import model.*;
import view.*;

import javax.swing.*;

import javax.swing.event.MouseInputAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener{
    private final int FPS = 50;

    private Model model;
    private View view;
    private JFrame frame;
    private Timer timer;

    /**
     * Custom implementation of {@code MouseInputAdapter}. Responds to mouse movement and clicks.
     */
    private class CustomMouseListener extends MouseInputAdapter {
        public void mouseMoved(MouseEvent e) { //Update mouse location in model
            model.updateMouseLocation(e.getPoint());
        }
        public void mouseDragged(MouseEvent e) { //Update mouse location in model
            model.updateMouseLocation(e.getPoint());
        }
        public void mousePressed(MouseEvent e) { //Only fire if game in progress
            if (model.getGameStatus() == Model.GameState.IN_PROGRESS)
                model.fireLaser();
        }
        public void mouseClicked(MouseEvent e) {
            if (model.getGameStatus() == Model.GameState.GAME_OVER) { //Reset if game over
                model.reset();
                view.updateViewReset();
            } else if (model.getGameStatus() == Model.GameState.RESET) { //Start game if just reset
                timer.start();
                model.start();
            }
        }
    }

    /**
     * Creates a controller linking a model and a view.
     * @param _model the model to use
     * @param _view the view to display
     */
    public Controller (Model _model, View _view) {
        //Instantiate fields
        frame = new JFrame("Space Game");
        model = _model;
        view = _view;

        //Link mouse listener
        CustomMouseListener l = new CustomMouseListener();
        view.getField().addMouseListener(l);
        view.getField().addMouseMotionListener(l);

        //Format display
        frame.setContentPane(view);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        //Configure timer
        timer = new Timer(1000 / FPS, this);
    }

    /**
     * Displays the view
     */
    public void displayGUI() {
        frame.setVisible(true);
    }

    /**
     * Invoked when an action occurs. In this case, a timer tick.
     * Ticks the timer in the model and updates view accordingly depending on current game state.
     * @param e the triggering {@code ActionEvent}. In this case, a timer tick.
     */
    public void actionPerformed(ActionEvent e) {
        model.tick(); //Tick model
        if (model.getGameStatus() == Model.GameState.IN_PROGRESS) {
            view.updateViewInProgress(model.getAsteroids(), model.getLasers(), model.getExplosions(), model.getStars(),
            model.getShip(), model.getPickupLocation(), model.getPickupType(), model.getShipHealth(),
            model.getBaseHealth(), model.getAsteroidsDestroyed(), model.getFireStatus() == Model.FireState.RAPID);
        } else if (model.getGameStatus() == Model.GameState.GAME_OVER) {
            timer.stop();
            view.updateViewGameOver(model.getShipHealth(), model.getBaseHealth(), model.getGameTicks() / FPS);
        }
    }
}