import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Dimension;

import java.awt.event.ActionListener;

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

        setLayout(new BorderLayout());
        board.setPreferredSize(new Dimension(325, 700));

        add(board, BorderLayout.LINE_START);

        JPanel nonBoardPanel = new JPanel();
        nonBoardPanel.setLayout(new BoxLayout(nonBoardPanel, BoxLayout.PAGE_AXIS));
        score.setPreferredSize(new Dimension(175, 200));
        nonBoardPanel.add(score);
        rules.setPreferredSize(new Dimension(175, 400));
        nonBoardPanel.add(rules);
        state.setPreferredSize(new Dimension(175, 100));
        nonBoardPanel.add(state);
        nonBoardPanel.setPreferredSize(new Dimension(175, 700));

        add(nonBoardPanel, BorderLayout.LINE_END);
    }

    public void updateBoard(boolean[][] baseInput, int[][] activeInput, boolean status) {
        board.update(baseInput, activeInput, status);
    }

    public void updateNext(String nextPiece) {
        score.updateNext(nextPiece);
    }

    public void updateScore(int scoreVal) {
        score.updateScore(scoreVal);
    }

    public void updateState(Model.GameState gameState) {
        state.updateState(gameState);
    }

    public void registerButtonListener(ActionListener listener) {
        state.registerButtonListener(listener);
    }
}