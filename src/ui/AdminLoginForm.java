package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Timer;
import java.util.TimerTask;

public class AdminLoginForm extends JFrame implements ActionListener {

    private final JLabel passwordLabel = new JLabel("password");
    private final JPasswordField passwordField = new JPasswordField();
    private final JLabel loginStatus = new JLabel();
    private final JButton loginButton = new JButton();
    private final JButton resetButton = new JButton();
    private final JButton back;

    private String password = "unis";

    public AdminLoginForm() {

        passwordLabel.setBounds(50, 100, 75, 25);
        loginStatus.setBounds(125, 250, 250, 35);
        loginStatus.setFont(new Font(null, Font.BOLD, 25));
        passwordField.setBounds(125, 100, 200, 25);
        loginButton.setBounds(125, 150, 100, 25);
        loginButton.setText("Login");
        loginButton.setFocusable(false);
        loginButton.addActionListener(this);
        resetButton.setBounds(225, 150, 100, 25);
        resetButton.setText("Reset");
        resetButton.setFocusable(false);
        resetButton.addActionListener(this);
        back = new JButton("Back");
        back.addActionListener(this);
        back.setFocusable(false);
        back.setBounds(10,400,100,25);

        this.add(passwordLabel);
        this.add(back);
        this.add(passwordField);
        this.add(loginStatus);
        this.add(loginButton);
        this.add(resetButton);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String pass = String.valueOf(passwordField.getPassword());

        if (e.getSource() == resetButton) {
            passwordField.setText("");
        }
        if (e.getSource() == loginButton && !passwordField.getText().isEmpty()){
            if(pass.equals(password)){
                this.dispose();
                new AdminMainForm();
            }else{
                loginStatus.setForeground(Color.red);
                loginStatus.setText("Wrong password");
                Timer time = new Timer();
                time.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        removeLoginStatus();
                    }
                }, 2000);
            }
        }
        else if(e.getSource() == loginButton && passwordField.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Please enter the administrator password",null,JOptionPane.WARNING_MESSAGE);
        }
        else if(e.getSource()==back){
            this.dispose();
            new UserTypeSelectionForm();
        }
    }
    public void removeLoginStatus(){
        this.remove(loginStatus);
        this.revalidate();
        this.repaint();
    }
}
