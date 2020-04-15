package app;

import javax.swing.*;

public class View{
    private JPanel mainPanel = new JPanel();

    public View() {
        JTextArea ta = new JTextArea(5, 20);
        ta.setEditable(false);
        mainPanel.add(ta);
    }

    public JPanel getPanel() {
        return mainPanel;
    }


}