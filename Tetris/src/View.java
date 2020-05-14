import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Dimension;

import java.awt.event.ActionListener;

/**
 * Class extends JPanel. Represents the content pane encompassing all parts of the view.
 */
@SuppressWarnings("serial")
public class View extends JPanel{
    private Board board;
    private Rules rules;
    private Score score;
    private State state;

    public View() {
        board = new Board();
        rules = new Rules();
        score = new Score();
        state = new State();

        //Set up View
        setLayout(new BorderLayout());

        //Add board component
        board.setPreferredSize(new Dimension(325, 670));
        add(board, BorderLayout.LINE_START);

        //Add other components to placeholder panel
        JPanel nonBoardPanel = new JPanel();
        nonBoardPanel.setLayout(new BoxLayout(nonBoardPanel, BoxLayout.PAGE_AXIS));
        score.setPreferredSize(new Dimension(175, 200));
        nonBoardPanel.add(score);
        rules.setPreferredSize(new Dimension(175, 400));
        nonBoardPanel.add(rules);
        state.setPreferredSize(new Dimension(175, 70));
        nonBoardPanel.add(state);
        nonBoardPanel.setPreferredSize(new Dimension(175, 670));

        //Add placeholder panel
        add(nonBoardPanel, BorderLayout.CENTER);

        //Add spacer at end
        add(Box.createRigidArea(new Dimension(10, 670)), BorderLayout.LINE_END);
    }

    /**
     * Updates the board component of the view
     * @param baseInput a 2d boolean array representing the state of each cell of the board
     * @param activeInput a 2d int array representing the location of the active piece
     * @param status {@code true} if the game is over, {@code false} otherwise
     */
    public void updateBoard(boolean[][] baseInput, int[][] activeInput, boolean status) {
        board.update(baseInput, activeInput, status);
    }

    /**
     * Updates the next piece component of the view
     * @param nextPiece the name of the type of piece
     */
    public void updateNext(String nextPiece) {
        score.updateNext(nextPiece);
    }

    /**
     * Updates the score component of the view
     * @param scoreVal the score
     */
    public void updateScore(int scoreVal) {
        score.updateScore(scoreVal);
    }

    /**
     * Updates the state component of the view
     * @param gameState the current GameState
     */
    public void updateState(Model.GameState gameState) {
        state.updateState(gameState);
    }

    /**
     * Registers an ActionListener to the start/reset button
     * @param listener the ActionListener to register
     */
    public void registerButtonListener(ActionListener listener) {
        state.registerButtonListener(listener);
    }
}