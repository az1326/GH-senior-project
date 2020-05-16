package view;

import javax.swing.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

@SuppressWarnings("serial")
public class Health extends JPanel {
    private JLabel shipHealthLabel;
    private JLabel shipHealthValue;
    private JLabel baseHealthLabel;
    private JLabel baseHealthValue;

    private int shipHealth;
    private int baseHealth;

    public Health() {
        shipHealth = 100;
        baseHealth = 100;

        shipHealthLabel = new JLabel("Ship Health: ");
        shipHealthLabel.setHorizontalAlignment(JLabel.CENTER);
        shipHealthLabel.setVerticalAlignment(JLabel.CENTER);
        shipHealthLabel.setForeground(Color.WHITE);

        baseHealthLabel = new JLabel("Base Health: ");
        baseHealthLabel.setHorizontalAlignment(JLabel.CENTER);
        baseHealthLabel.setVerticalAlignment(JLabel.CENTER);
        baseHealthLabel.setForeground(Color.WHITE);

        shipHealthValue = new JLabel("100%");
        shipHealthValue.setHorizontalAlignment(JLabel.CENTER);
        shipHealthValue.setVerticalAlignment(JLabel.CENTER);
        shipHealthValue.setForeground(Color.WHITE);

        baseHealthValue = new JLabel("100%");
        baseHealthValue.setHorizontalAlignment(JLabel.CENTER);
        baseHealthValue.setVerticalAlignment(JLabel.CENTER);
        baseHealthValue.setForeground(Color.WHITE);

        setBackground(Color.DARK_GRAY);
        setLayout(new GridLayout(5, 1));
        add(shipHealthLabel);
        add(shipHealthValue);
        add(Box.createRigidArea(new Dimension(0,0)));
        add(baseHealthLabel);
        add(baseHealthValue);
    }

    public void updateHealth(int shipHealth, int baseHealth) {
        if (this.shipHealth != shipHealth) {
            this.shipHealth = shipHealth;
            if (this.shipHealth < 0) {this.shipHealth = 0;}
            shipHealthValue.setText(this.shipHealth + "%");
        }
        if (this.baseHealth != baseHealth) {
            this.baseHealth = baseHealth;
            if (this.baseHealth < 0) {this.baseHealth = 0;}
            baseHealthValue.setText(this.baseHealth + "%");
        }
    }
    
}