import javax.swing.*;

import java.awt.Dimension;

import java.awt.event.ActionListener;

/**
 * Class extends JPanel. Represents the part of the view that includes the button
 * and indicates current game status.
 */
@SuppressWarnings("serial")
public class State extends JPanel{
    private JLabel gameState;
    private JButton restart;

    private Model.GameState status;

    /**
     * Contructor method for State. Sets components to default values and adds components to the panel.
     */
    public State() {
        gameState = new JLabel("Click to Start");
        gameState.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        restart = new JButton("Start");
        restart.setAlignmentX(JButton.CENTER_ALIGNMENT);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(gameState);
        add(Box.createRigidArea(new Dimension(175,0)));
        add(restart);
    }

    /**
     * Registers the given ActionListener to the start/reset button.
     * @param listener the listener to register
     */
    public void registerButtonListener(ActionListener listener) {
        restart.addActionListener(listener);
    }

    /**
     * Updates the game state in the view if needed.
     * @param gameState the given GameState
     */
    public void updateState(Model.GameState gameState) {
        if (status == null || status != gameState) { //Proceed only if there is a change in gameState
            status = gameState;
            if (status == Model.GameState.IN_PROGRESS) {
                this.gameState.setText("Click to Reset");
                restart.setText("Reset");
            } else if (status == Model.GameState.GAME_OVER) {
                this.gameState.setText("Game Over! Click to Reset");
                restart.setText("Reset");
            } else {
                this.gameState.setText("Click to Start");
                restart.setText("Start");
            }
        }
    }
}