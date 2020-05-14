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

    /**
     * Handles keyboard inputs
     */
    private class CustomDispatcher implements KeyEventDispatcher {
        public boolean dispatchKeyEvent (KeyEvent e) {
            if (e.getID() == KeyEvent.KEY_PRESSED) {
                if (e.getKeyCode() == 32) { //Spacebar: no input

                } else if (e.getKeyCode() == 38) { //Up arrow: no input

                } else if (e.getKeyCode() == 40) { //Down arrow: drop
                    model.shift(Model.Direction.DOWN);
                    updateView();
                } else if (e.getKeyCode() == 39) { //Right arrow: shift right
                    model.shift(Model.Direction.RIGHT);
                    updateView();
                } else if (e.getKeyCode() == 37) { //Left arrow: shift left
                    model.shift(Model.Direction.LEFT);
                    updateView();
                } else if (e.getKeyCode() == 90) { //z: rotate
                    model.rotate(false);
                    updateView();
                } else if (e.getKeyCode() == 88) { //x: rotate
                    model.rotate(true);
                    updateView();
                }
                else {
                    //System.out.println(e.getKeyCode());
                }
            }
            return false;
        }
    }

    /**
     * ActionListener for view button
     */
    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (model.getState().equals(Model.GameState.START_UP)) { //Start game
                timer.start();
                model.start();
                updateView();
            } else { //Reset game
                model.reset();
                timer.stop();
                updateView();
            }
        }
    }

    /**
     * Constructor method for Controller. Accepts the model and the view to control as arguments.
     * @param _model the Model to control
     * @param _view the View to control
     */
    public Controller (Model _model, View _view) {
        model = _model;
        view = _view;
        view.registerButtonListener(new ButtonListener());
        frame.setContentPane(view);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new CustomDispatcher());
        timer = new Timer(20, this);
    }

    /**
     * Displays the frame
     */
    public void displayGUI() {
        frame.setVisible(true);
        //timer.start();
    }

    /**
     * Updates each component of the view
     */
    public void updateView() {
        view.updateBoard(model.getBackgroundPieces(), model.getActivePiece(),
            model.getState() == Model.GameState.GAME_OVER);
        view.updateNext(model.getNextPiece());
        view.updateScore(model.getScore());
        view.updateState(model.getState());
    }

    /**
     * Handle timer tick
     * @param e the timer event
     */
    public void actionPerformed(ActionEvent e) {
        if (model.tick())
            updateView();
        if (model.getState() == Model.GameState.GAME_OVER)
            timer.stop();
    }

}