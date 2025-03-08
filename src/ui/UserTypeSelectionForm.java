package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserTypeSelectionForm extends JFrame implements ActionListener {

    private final JButton userButton;
    private final JButton adminButton;

    public UserTypeSelectionForm(){

        userButton = new JButton();
        userButton.addActionListener(this);
        userButton.setText("User");
        userButton.setFocusable(false);
        userButton.setFont(new Font("Comic San", Font.BOLD, 14));
        userButton.setBorder(BorderFactory.createEtchedBorder());
        userButton.setBounds(125,50,150,50);

        adminButton = new JButton();
        adminButton.addActionListener(this);
        adminButton.setText("Admin");
        adminButton.setFocusable(false);
        adminButton.setFont(new Font("Comic San", Font.BOLD, 14));
        adminButton.setBorder(BorderFactory.createEtchedBorder());
        adminButton.setBounds(125,150,150,50);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(400,350);
        this.add(userButton);
        this.add(adminButton);
        this.setVisible(true);


    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==userButton){
            this.dispose();
            new UserLoginForm();



        } else if(e.getSource()==adminButton){
            this.dispose();
            new AdminLoginForm();
            JOptionPane.showMessageDialog(null,"Please enter the administrator passcode",null,JOptionPane.INFORMATION_MESSAGE);

        }
    }
}
