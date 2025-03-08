package ui;

import model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


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
        this.setSize(500,500);
        this.setLayout(null);

        back = new JButton("Back");
        back.addActionListener(this);
        back.setFocusable(false);
        back.setBounds(10,400,100,25);

        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        loginButton.setFocusable(false);
        loginButton.setBounds(125,200,100,25);

        resetButton = new JButton("Reset");
        resetButton.addActionListener(this);
        resetButton.setFocusable(false);
        resetButton.setBounds(225,200,100,25);

        signUpButton = new JButton("Create an account");
        signUpButton.addActionListener(this);
        signUpButton.setFocusable(false);
        signUpButton.setBounds(125,250,200,25);

        username = new JLabel("Username: ");
        username.setBounds(50,100,75,25);

        password = new JLabel("Password: ");
        password.setBounds(50,150,75,25);

        usernameField = new JTextField();
        usernameField.setBounds(125,100,200,25);

        passwordField = new JPasswordField();
        passwordField.setBounds(125,150,200,25);

        this.add(username);
        this.add(password);
        this.add(usernameField);
        this.add(passwordField);
        this.add(loginButton);
        this.add(resetButton);
        this.add(signUpButton);
        this.add(back);
        this.setResizable(false);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String line;
        String[] lineArr;
        boolean userFound = false;
        if (e.getSource() == loginButton) {
            if (!usernameField.getText().isEmpty() && !passwordField.getText().isEmpty()) {
                try {
                    BufferedReader br = new BufferedReader(new FileReader(Main.userListPath));
                    while (true) {
                        try {
                            if ((line = br.readLine()) == null)
                                break;
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        lineArr = line.split(",");
                        if (lineArr[1].equals(usernameField.getText().trim()) && lineArr[2].equals(passwordField.getText().trim())) {
                            User user = new User(usernameField.getText().trim(),passwordField.getText().trim(), Main.getID(usernameField.getText()));
                            userFound = true;
                            Main.currentUser = user;
                            this.dispose();
                            new UserMainForm();
                        }
                    }

                } catch (IOException j) {
                    j.printStackTrace();
                }
                if(!userFound){
                    JOptionPane.showMessageDialog(null,"Incorrect username and/or password",null,JOptionPane.WARNING_MESSAGE);
                }
            }else{
                JOptionPane.showMessageDialog(null,"Please enter a username and password",null,JOptionPane.WARNING_MESSAGE);
            }
    }

        else if(e.getSource()==resetButton){
            usernameField.setText("");
            passwordField.setText("");

        }else if(e.getSource()==signUpButton){
            this.dispose();
            new UserSignUpForm();

        }
        else if(e.getSource()==back){
            this.dispose();
            new UserTypeSelectionForm();
        }

    }
}
