package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AdminMainForm extends FacilitySchedule implements ActionListener {
    private final JButton delete;
    private final JButton userSchedule;
    private final JButton back;
    private final JLabel warningLabel;
    private final DefaultTableModel model = new DefaultTableModel();
    private final JTable table;

    public AdminMainForm(){
        super();
        ArrayList<String> endangeredBookings = Main.getEndangeredBookings();
        table = new JTable(model);
        model.addColumn("Endangered bookings");
        for (int i = 0; i < endangeredBookings.size(); i ++){
            model.addRow(new Object[]{endangeredBookings.get(i)});
        }

        delete = new JButton("Delete");
        delete.addActionListener(this);
        delete.setFocusable(false);
        delete.setBounds(250,180,100,25);
        userSchedule = new JButton("View Users' Schedules");
        userSchedule.addActionListener(this);
        userSchedule.setFocusable(false);
        userSchedule.setBounds(10,250,200,25);
        back = new JButton("Back");
        back.addActionListener(this);
        back.setFocusable(false);
        back.setBounds(10,400,100,25);
        warningLabel = new JLabel("The current AQI is " + Main.AQI + " and the heat index is " + Main.heatIndex + ". Contact the owners of the following bookings:");
        warningLabel.setBounds(280,250,900,25);
        table.setBounds(280,280,300,16*endangeredBookings.size());
        super.jFrame.add(delete);
        super.jFrame.add(warningLabel);
        super.jFrame.add(table);
        super.jFrame.add(userSchedule);
        super.table.setEnabled(true);
        super.table.setDefaultEditor(Object.class,null);
        super.jFrame.remove(super.back);
        super.jFrame.revalidate();
        super.jFrame.add(back);

    }
    @Override
    public void actionPerformed(ActionEvent e){
        super.actionPerformed(e);
        super.table.setEnabled(true);
        super.table.setDefaultEditor(Object.class,null);
        if(e.getSource()==delete){
            if(super.table.getSelectedRowCount()==1){
                if(!super.table.getValueAt(super.table.getSelectedRow(),super.table.getSelectedColumn()).equals("x")&&!super.table.getValueAt(super.table.getSelectedRow(),super.table.getSelectedColumn()).equals("Period")&&!super.table.getValueAt(super.table.getSelectedRow(),super.table.getSelectedColumn()).equals("Monday")&&!super.table.getValueAt(super.table.getSelectedRow(),super.table.getSelectedColumn()).equals("Tuesday")&&!super.table.getValueAt(super.table.getSelectedRow(),super.table.getSelectedColumn()).equals("Wednesday")&&!super.table.getValueAt(super.table.getSelectedRow(),super.table.getSelectedColumn()).equals("Thursday")&&!super.table.getValueAt(super.table.getSelectedRow(),super.table.getSelectedColumn()).equals("Friday")&&!super.table.getValueAt(super.table.getSelectedRow(),super.table.getSelectedColumn()).equals("1")&&!super.table.getValueAt(super.table.getSelectedRow(),super.table.getSelectedColumn()).equals("2")&&!super.table.getValueAt(super.table.getSelectedRow(),super.table.getSelectedColumn()).equals("3")&&!super.table.getValueAt(super.table.getSelectedRow(),super.table.getSelectedColumn()).equals("4")&&!super.table.getValueAt(super.table.getSelectedRow(),super.table.getSelectedColumn()).equals("5")){
                    Main.deleteBooking(super.table.getSelectedRow(),super.table.getSelectedColumn(),super.table.getValueAt(super.table.getSelectedRow(),super.table.getSelectedColumn()).toString());
                    super.table.setValueAt("x",super.table.getSelectedRow(),super.table.getSelectedColumn());
                    JOptionPane.showMessageDialog(null,"The booking has been successfully removed",null,JOptionPane.INFORMATION_MESSAGE);
                }else{
                    JOptionPane.showMessageDialog(null,"Please select a valid booking",null,JOptionPane.WARNING_MESSAGE);
                }
            }
            else if(super.table.getSelectedRowCount()==0||super.table.getSelectedRowCount()>1){
                JOptionPane.showMessageDialog(null,"Please select a single booking to delete",null,JOptionPane.WARNING_MESSAGE);
            }
        }
        if(e.getSource()==userSchedule){
            new AdminUserSchedule();
            super.jFrame.dispose();
        }
        if(e.getSource()==back){
            super.jFrame.dispose();
            new AdminLoginForm();
        }
    }
}
