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

    public Controller (Model _model, View _view) {
        model = _model;
        view = _view;
        frame.setContentPane(view);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        timer = new Timer(500, this);
    }

    public void displayGUI() {
        frame.setVisible(true);
        timer.start();
    }

    public void actionPerformed(ActionEvent e) {
        view.repaint();
    }

}