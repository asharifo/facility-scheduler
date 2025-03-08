package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminUserSchedule extends UserSchedule implements ActionListener {
    private final JButton back;
    private final JTextField userIdOrUsername;
    private final JButton search;
    private final JButton delete;
    private final JButton userList;
    private final JLabel inputUserOrID;
    private String username;

    public AdminUserSchedule(){
        super();
        inputUserOrID = new JLabel("Input desired user's username or ID");
        inputUserOrID.setBounds(10,10,250,25);
        back = new JButton("Back");
        back.addActionListener(this);
        back.setFocusable(false);
        back.setBounds(10,250,100,25);
        userList = new JButton("View User List");
        userList.addActionListener(this);
        userList.setFocusable(false);
        userList.setBounds(10,200,150,25);
        search = new JButton("Search");
        search.addActionListener(this);
        search.setFocusable(false);
        search.setBounds(10,100,100,25);
        delete = new JButton("Delete");
        delete.addActionListener(this);
        delete.setFocusable(false);
        delete.setBounds(10,150,100,25);
        userIdOrUsername = new JTextField();
        userIdOrUsername.setBounds(10,50,200,25);
        super.jFrame.add(back);
        super.jFrame.add(delete);
        super.jFrame.add(userIdOrUsername);
        super.jFrame.add(search);
        super.jFrame.add(userList);
        super.jFrame.add(inputUserOrID);
        super.jFrame.remove(super.back);
        super.jFrame.remove(super.display);
        super.jFrame.remove(super.label);
        super.jFrame.remove(super.delete);
        super.jFrame.revalidate();

    }
    @Override
    public void actionPerformed(ActionEvent e) {
            try{
                //if search button is pressed
            if (e.getSource() == search) {
                //If the admin enters a number, the program will treat it as an ID. If the admin enters a word
                //the try block will throw an error and the program will treat it as a username
                if (userIdOrUsername.getText().isEmpty() || Integer.parseInt(userIdOrUsername.getText()) > Main.getUserCount()) {
                    JOptionPane.showMessageDialog(null, "Please input a valid user ID", null, JOptionPane.WARNING_MESSAGE);
                } else {
                    //get ID from user's input
                    int ID = Integer.parseInt(userIdOrUsername.getText());
                    //get corresponding username
                    username = Main.getUsername(ID);
                    super.jFrame.remove(super.table);
                    //get desired user's schedule
                    super.data = Main.getUserSchedule(ID);
                    //display user's schedule in a table
                    super.table = new JTable(super.data, super.col);
                    super.table.setBounds(250, 50, 600, 100);
                    super.table.setDefaultEditor(Object.class, null);
                    super.jFrame.add(table);
                    super.jFrame.revalidate();
                }
            }
        }catch(NumberFormatException j){
                //if the runtime error is caught, the program will execute the following code
                username = userIdOrUsername.getText();
                //check that the username belongs to a current user
                if(!Main.verifyUsernameOriginality(username)) {
                    super.jFrame.remove(super.table);
                    //same method is used but with different parameter type to get user's schedule
                    super.data = Main.getUserSchedule(username);
                    super.table = new JTable(super.data, super.col);
                    super.table.setBounds(250, 50, 600, 100);
                    super.table.setDefaultEditor(Object.class, null);
                    super.jFrame.add(table);
                    super.jFrame.revalidate();
                }
                else{
                    //if the username was not found in the UserInfo.csv file, the following message is displayed
                    JOptionPane.showMessageDialog(null, "Please input a valid username", null, JOptionPane.WARNING_MESSAGE);
                }
        }
    if(e.getSource()==back){
        new AdminMainForm();
        super.jFrame.dispose();
    }
    if(e.getSource()==delete) {
        if (super.table.getSelectedRowCount() == 1) {
            if ((super.table.getValueAt(table.getSelectedRow(), super.table.getSelectedColumn()))!=null&&!super.table.getValueAt(super.table.getSelectedRow(),super.table.getSelectedColumn()).equals("Period")&&!super.table.getValueAt(super.table.getSelectedRow(),super.table.getSelectedColumn()).equals("Monday")&&!super.table.getValueAt(super.table.getSelectedRow(),super.table.getSelectedColumn()).equals("Tuesday")&&!super.table.getValueAt(super.table.getSelectedRow(),super.table.getSelectedColumn()).equals("Wednesday")&&!super.table.getValueAt(super.table.getSelectedRow(),super.table.getSelectedColumn()).equals("Thursday")&&!super.table.getValueAt(super.table.getSelectedRow(),super.table.getSelectedColumn()).equals("Friday")&&!super.table.getValueAt(super.table.getSelectedRow(),super.table.getSelectedColumn()).equals("1")&&!super.table.getValueAt(super.table.getSelectedRow(),super.table.getSelectedColumn()).equals("2")&&!super.table.getValueAt(super.table.getSelectedRow(),super.table.getSelectedColumn()).equals("3")&&!super.table.getValueAt(super.table.getSelectedRow(),super.table.getSelectedColumn()).equals("4")&&!super.table.getValueAt(super.table.getSelectedRow(),super.table.getSelectedColumn()).equals("5")){
                Main.deleteBooking(super.table.getSelectedRow(), super.table.getSelectedColumn(), username);
                super.table.setValueAt(null, super.table.getSelectedRow(), super.table.getSelectedColumn());
                JOptionPane.showMessageDialog(null, "The specified user's booking has been successfully removed", null, JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Please select a valid booking", null, JOptionPane.WARNING_MESSAGE);
            }
        }
        else if(super.table.getSelectedRowCount()==0||super.table.getSelectedRowCount()>1){
            JOptionPane.showMessageDialog(null,"Please select a single booking to delete",null,JOptionPane.WARNING_MESSAGE);
            }
        }
    if(e.getSource()==userList){
        super.jFrame.dispose();
        new AdminUserList();
        }
    }
}
