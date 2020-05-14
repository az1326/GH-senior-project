package view;

import javax.swing.*;
import java.awt.Graphics;
import java.awt.Dimension;

@SuppressWarnings("serial")
public class View extends JPanel{
    private Field field;
    private Health health;
    private Info info;
    private Score score;
    private Rules rules;

    public View() {
        field = new Field();
        health = new Health();
        info = new Info();
        score = new Score();
        rules = new Rules();

        field.setPreferredSize(new Dimension(800, 400));
        health.setPreferredSize(new Dimension(100, 100));
        info.setPreferredSize(new Dimension(170, 100));
        score.setPreferredSize(new Dimension(100, 100));
        rules.setPreferredSize(new Dimension(400, 100));

        JPanel hPanel1 = new JPanel();
        JPanel hPanel2 = new JPanel();

        hPanel1.setLayout(new BoxLayout(hPanel1, BoxLayout.LINE_AXIS));
        hPanel1.add(Box.createRigidArea(new Dimension(10, 400)));
        hPanel1.add(field);
        hPanel1.add(Box.createRigidArea(new Dimension(10, 400)));

        hPanel2.setLayout(new BoxLayout(hPanel2, BoxLayout.LINE_AXIS));
        hPanel2.add(Box.createRigidArea(new Dimension(10, 100)));
        hPanel2.add(health);
        hPanel2.add(Box.createRigidArea(new Dimension(10, 100)));
        hPanel2.add(info);
        hPanel2.add(Box.createRigidArea(new Dimension(10, 100)));
        hPanel2.add(score);
        hPanel2.add(Box.createRigidArea(new Dimension(10, 100)));
        hPanel2.add(rules);
        hPanel2.add(Box.createRigidArea(new Dimension(10, 100)));

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(Box.createRigidArea(new Dimension(820, 10)));
        add(hPanel1);
        add(Box.createRigidArea(new Dimension(820, 10)));
        add(hPanel2);
        add(Box.createRigidArea(new Dimension(820, 10)));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}