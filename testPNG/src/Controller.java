import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

import java.awt.event.MouseEvent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener{
    private Model model;
    private View view;
    private JFrame frame = new JFrame("Test");
    Timer timer;

    //Mouse controls
    private class CustomMouseListener extends MouseInputAdapter {
        public void mouseMoved(MouseEvent e) {
            view.setCoordinateCont(e.getPoint());
            model.updateMouseLocation(e.getPoint());
        }
        public void mouseReleased(MouseEvent e) {
            model.toggle();
        }
    }

    public Controller (Model _model, View _view) {
        model = _model;
        view = _view;
        CustomMouseListener l = new CustomMouseListener();
        view.addMouseListener(l);
        view.addMouseMotionListener(l);
        frame.setContentPane(view);
        frame.setSize(300,300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        timer = new Timer(20, this);
    }

    public void displayGUI() {
        frame.pack();
        frame.setVisible(true);
        timer.start();
    }

    public void actionPerformed(ActionEvent e) {
        view.updateView(model.getMouseLocation(), model.getToggle());
    }

}