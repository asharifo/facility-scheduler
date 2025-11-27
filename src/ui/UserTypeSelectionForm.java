package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserTypeSelectionForm extends JFrame implements ActionListener {

    private final JButton userButton;
    private final JButton adminButton;

    public UserTypeSelectionForm() {

        userButton = new JButton("User");
        userButton.addActionListener(this);
        userButton.setFocusable(false);
        userButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        userButton.setBounds(125, 50, 150, 50);

        adminButton = new JButton("Admin");
        adminButton.addActionListener(this);
        adminButton.setFocusable(false);
        adminButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        adminButton.setBounds(125, 150, 150, 50);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(400, 300);
        this.add(userButton);
        this.add(adminButton);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == userButton) {
            this.dispose();
            new UserLoginForm();
        } else if (e.getSource() == adminButton) {
            this.dispose();
            new AdminLoginForm();
        }
    }
}
