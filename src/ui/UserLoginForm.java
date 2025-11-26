package ui;

import db.UserDAO;
import model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserLoginForm extends JFrame implements ActionListener {

    private final JButton loginButton;
    private final JButton resetButton;
    private final JButton signUpButton;
    private final JLabel username;
    private final JLabel password;
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JButton back;

    public UserLoginForm(){

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500,400);
        this.setLayout(null);

        back = new JButton("Back");
        back.addActionListener(this);
        back.setFocusable(false);
        back.setBounds(10,320,100,25);

        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        loginButton.setFocusable(false);
        loginButton.setBounds(125,200,100,25);

        resetButton = new JButton("Reset");
        resetButton.addActionListener(this);
        resetButton.setFocusable(false);
        resetButton.setBounds(235,200,100,25);

        signUpButton = new JButton("Create an account");
        signUpButton.addActionListener(this);
        signUpButton.setFocusable(false);
        signUpButton.setBounds(125,240,210,25);

        username = new JLabel("Username:");
        username.setBounds(50,100,100,25);

        password = new JLabel("Password:");
        password.setBounds(50,150,100,25);

        usernameField = new JTextField();
        usernameField.setBounds(150,100,200,25);

        passwordField = new JPasswordField();
        passwordField.setBounds(150,150,200,25);

        this.add(username);
        this.add(password);
        this.add(usernameField);
        this.add(passwordField);
        this.add(loginButton);
        this.add(resetButton);
        this.add(signUpButton);
        this.add(back);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == loginButton) {

            String user = usernameField.getText().trim();
            String pass = new String(passwordField.getPassword()).trim();

            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this,"Please enter a username and password",null,JOptionPane.WARNING_MESSAGE);
                return;
            }

            User found = UserDAO.findByUsernameAndPassword(user, pass);

            if (found != null) {
                Main.currentUser = found;
                this.dispose();
                new UserMainForm();
            } else {
                JOptionPane.showMessageDialog(this,"Incorrect username and/or password",null,JOptionPane.WARNING_MESSAGE);
            }
        }
        else if(e.getSource()==resetButton){
            usernameField.setText("");
            passwordField.setText("");
        }
        else if(e.getSource()==signUpButton){
            this.dispose();
            new UserSignUpForm();
        }
        else if(e.getSource()==back){
            this.dispose();
            new UserTypeSelectionForm();
        }
    }
}
