import javax.swing.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.Dimension;

public class View extends JPanel{
    private Controller cont;
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
        rules.setPreferredSize(new Dimension(175, 200));
        nonBoardPanel.add(rules);
        state.setPreferredSize(new Dimension(175, 100));
        nonBoardPanel.add(state);
        nonBoardPanel.setPreferredSize(new Dimension(175, 700));

        add(nonBoardPanel, BorderLayout.LINE_END);
    }

    public void registerController(Controller source) {
        cont = source;
    }

    public void updateBoardBase() {
        board.updateBase();
    }

    public void updateBoardActive() {
        board.updateActive();
    }

    public void updateNext() {
        score.updateNext();
    }
}