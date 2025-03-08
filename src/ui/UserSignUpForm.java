package ui;

import model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class UserSignUpForm extends JFrame implements ActionListener{

    private final JButton resetButton;
    private final JButton signUpButton;
    private final JLabel username;
    private final JLabel password;
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JButton back;

    public UserSignUpForm(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500,500);
        this.setLayout(null);

        signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(this);
        signUpButton.setFocusable(false);
        signUpButton.setBounds(125,200,100,25);

        back = new JButton("Back");
        back.addActionListener(this);
        back.setFocusable(false);
        back.setBounds(10,400,100,25);

        resetButton = new JButton("Reset");
        resetButton.addActionListener(this);
        resetButton.setFocusable(false);
        resetButton.setBounds(225,200,100,25);

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
        this.add(signUpButton);
        this.add(resetButton);
        this.add(back);
        this.setResizable(false);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String line;
        String[] lineArr;
        ArrayList<String> tempArray=new ArrayList<>();
        //if user presses the sign-up button and the fields aren't empty
        if(e.getSource()==signUpButton && !usernameField.getText().isEmpty() && !passwordField.getText().isEmpty()){
            //if the username is original the account information will be saved
            if(Main.verifyUsernameOriginality(usernameField.getText())) {
                //getting the ID
                int count = Main.getUserCount() + 1;
                Main.setUserCount(count);
                User user = new User(usernameField.getText(), passwordField.getText(), count);
                //setting the static currentUser field as user to track who the current user is
                Main.currentUser = user;
                try {
                    //read the contents of the file into an ArrayList and add the new account information
                    BufferedReader br = new BufferedReader(new FileReader(Main.userListPath));
                    while ((line = br.readLine()) != null) {
                        lineArr = line.split(",");
                        if (lineArr[0].equals(String.valueOf(count))) {
                            tempArray.add(
                                    count + "," +
                                            usernameField.getText().trim() + "," +
                                            passwordField.getText().trim());
                        } else {
                            tempArray.add(line);
                        }
                    }
                } catch (IOException b) {
                    b.printStackTrace();
                }
                try {
                    //rewrite the CSV file
                    try (PrintWriter pr = new PrintWriter(Main.userListPath)) {
                        for (String str : tempArray) {
                            pr.println(str);
                        }
                    } catch (Exception k) {

                    }
                } catch (Exception K) {

                }
                this.dispose();
                new UserMainForm();
            }else{
                JOptionPane.showMessageDialog(null,"Username already exists",null,JOptionPane.WARNING_MESSAGE);
            }
        }else if(e.getSource()==signUpButton && (usernameField.getText().isEmpty() || passwordField.getText().isEmpty())){
            JOptionPane.showMessageDialog(null,"Please enter a username and password",null,JOptionPane.WARNING_MESSAGE);
        }
        else if(e.getSource()==resetButton){
            usernameField.setText("");
            passwordField.setText("");
        }
        else if(e.getSource()==back){
            this.dispose();
            new UserLoginForm();
        }
    }
}



