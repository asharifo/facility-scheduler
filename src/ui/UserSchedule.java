package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserSchedule implements ActionListener {
    protected final JLabel label;
    protected final JButton display;
    protected JTable table;
    protected String[] col;
    protected String[][] data;
    protected final JButton back;
    protected final JButton delete;
    protected final JFrame jFrame;


    public UserSchedule(){

        col = new String[]{"Period","Monday","Tuesday","Wednesday","Thursday","Friday"};

        jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(1000,300);
        jFrame.setLayout(null);

        label = new JLabel("Display Your Schedule:");
        label.setBounds(10,10,200,25);

        back = new JButton("Back");
        back.addActionListener(this);
        back.setFocusable(false);
        back.setBounds(10,150,100,25);

        delete = new JButton("Delete");
        delete.addActionListener(this);
        delete.setFocusable(false);
        delete.setBounds(10,100,100,25);

        display = new JButton("Display");
        display.addActionListener(this);
        display.setFocusable(false);
        display.setBounds(10,50,100,25);

        table = new JTable();
        table.setDefaultEditor(Object.class,null);


        jFrame.add(table);
        jFrame.add(delete);
        jFrame.add(label);
        jFrame.add(display);
        jFrame.add(back);
        jFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //if the display button is pressed display the user's schedule
        if (e.getSource() == display) {
            data = Main.getUserSchedule(Main.currentUser.getUsername());
            //create table with user's schedule
            table = new JTable(data, col);
            table.setBounds(250, 50, 600, 100);
            //allow user to select cells in the table
            table.setDefaultEditor(Object.class,null);
            //add table to JFrame
            jFrame.add(table);
            jFrame.revalidate();
        }
        else if(e.getSource()==back){
            jFrame.dispose();
            new UserMainForm();
        }
        else if(e.getSource()==delete){
            if(table.getSelectedRowCount()==1){
                if(table.getValueAt(table.getSelectedRow(),table.getSelectedColumn())!=null&&(table.getValueAt(table.getSelectedRow(),table.getSelectedColumn()).equals("Sports Center")||table.getValueAt(table.getSelectedRow(),table.getSelectedColumn()).equals("Covered Courts")||table.getValueAt(table.getSelectedRow(),table.getSelectedColumn()).equals("Tennis Court")||table.getValueAt(table.getSelectedRow(),table.getSelectedColumn()).equals("Football Field"))){
                    table.setValueAt(null,table.getSelectedRow(), table.getSelectedColumn());
                    Main.deleteBooking(table.getSelectedRow(),table.getSelectedColumn(), Main.currentUser.getUsername());
                    JOptionPane.showMessageDialog(null,"Your booking has been successfully removed",null,JOptionPane.INFORMATION_MESSAGE);
                }else{
                    JOptionPane.showMessageDialog(null,"Please select a valid booking",null,JOptionPane.WARNING_MESSAGE);
                }
            }
            if(table.getSelectedRowCount()==0||table.getSelectedRowCount()>1){
                JOptionPane.showMessageDialog(null,"Please select a single booking to delete",null,JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}
