package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FacilitySchedule implements ActionListener {
    private final JButton sportsCenter;
    private final JButton coveredCourts;
    private final JButton tennisCourt;
    private final JButton footballField;
    private final JLabel label;
    protected JTable table;
    private final String[] col;
    private Object[][] data;
    protected final JButton back;
    protected final JFrame jFrame;

    public FacilitySchedule(){

        col = new String[]{"Period","Monday","Tuesday","Wednesday","Thursday","Friday"};

        jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(1000,600);
        jFrame.setLayout(null);

        label = new JLabel("Display Facility Schedules:");
        label.setBounds(10,10,200,25);

        sportsCenter = new JButton("Sports Center");
        sportsCenter.addActionListener(this);
        sportsCenter.setFocusable(false);
        sportsCenter.setBounds(10,50,200,25);

        back = new JButton("Back");
        back.addActionListener(this);
        back.setFocusable(false);
        back.setBounds(10,300,100,25);

        coveredCourts = new JButton("Covered Courts");
        coveredCourts.addActionListener(this);
        coveredCourts.setFocusable(false);
        coveredCourts.setBounds(10,100,200,25);

        footballField = new JButton("Football Field");
        footballField.addActionListener(this);
        footballField.setFocusable(false);
        footballField.setBounds(10,150,200,25);

        tennisCourt = new JButton("Tennis Court");
        tennisCourt.addActionListener(this);
        tennisCourt.setFocusable(false);
        tennisCourt.setBounds(10,200,200,25);

        table = new JTable();

        jFrame.add(table);
        jFrame.add(label);
        jFrame.add(tennisCourt);
        jFrame.add(footballField);
        jFrame.add(coveredCourts);
        jFrame.add(sportsCenter);
        jFrame.add(back);
        jFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //if the sports center button was pressed
        if(e.getSource()==sportsCenter){
            //remove current table
            jFrame.remove(table);
            data = Main.getFacilitySchedule("Sports Center");
            //create new table with the sport center's schedule
            table = new JTable(data,col);
            table.setBounds(250,50,600,100);
            //remove editing rights
            table.setEnabled(false);
            jFrame.add(table);
            //refresh JFrame
            jFrame.revalidate();
        }
        else if(e.getSource()==coveredCourts){
            jFrame.remove(table);
            data = Main.getFacilitySchedule("Covered Courts");
            table = new JTable(data,col);
            table.setBounds(250,50,600,100);
            table.setEnabled(false);
            jFrame.add(table);
            jFrame.revalidate();
        }
        else if(e.getSource()==tennisCourt){
            jFrame.remove(table);
            data = Main.getFacilitySchedule("Tennis Court");
            table = new JTable(data,col);
            table.setBounds(250,50,600,100);
            table.setEnabled(false);
            jFrame.add(table);
            jFrame.revalidate();
        }
        else if(e.getSource()==footballField){
            jFrame.remove(table);
            data = Main.getFacilitySchedule("Football Field");
            table = new JTable(data,col);
            table.setBounds(250,50,600,100);
            table.setEnabled(false);
            jFrame.add(table);
            jFrame.revalidate();

        }
        else if(e.getSource()==back){
            jFrame.dispose();
            new UserMainForm();
            }
        }
    }



