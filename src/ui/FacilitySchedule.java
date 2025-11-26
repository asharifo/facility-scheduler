package ui;

import db.BookingDAO;
import model.Booking;
import model.Facilities;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FacilitySchedule extends JFrame implements ActionListener {

    private final JComboBox<Facilities> comboFacility;
    private final JComboBox<String> comboDay;
    private final JButton btnDisplay;
    private final JButton btnBack;

    private final JTable table;
    private final DefaultTableModel model;

    public FacilitySchedule() {

        setTitle("Facility Schedule");
        setSize(800,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel lblFac = new JLabel("Facility:");
        lblFac.setBounds(20,20,80,25);
        add(lblFac);

        comboFacility = new JComboBox<>();
        for (Facilities f : Main.FACILITIES) comboFacility.addItem(f);
        comboFacility.setBounds(100,20,200,25);
        add(comboFacility);

        JLabel lblDay = new JLabel("Day:");
        lblDay.setBounds(20,60,80,25);
        add(lblDay);

        String[] days = {"Monday","Tuesday","Wednesday","Thursday","Friday"};
        comboDay = new JComboBox<>(days);
        comboDay.setBounds(100,60,200,25);
        add(comboDay);

        btnDisplay = new JButton("Display");
        btnDisplay.setBounds(20,100,100,25);
        btnDisplay.addActionListener(this);
        add(btnDisplay);

        btnBack = new JButton("Back");
        btnBack.setBounds(20,400,100,25);
        btnBack.addActionListener(this);
        add(btnBack);

        model = new DefaultTableModel(new Object[]{"ID","UserID","Day","Period","Students"}, 0){
            @Override public boolean isCellEditable(int r,int c){return false;}
        };
        table = new JTable(model);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(350,20,400,350);
        add(scroll);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource()==btnDisplay) {

            Facilities facility = (Facilities) comboFacility.getSelectedItem();
            String day = (String) comboDay.getSelectedItem();

            if (facility == null || day == null) {
                JOptionPane.showMessageDialog(this,"Select facility and day.");
                return;
            }

            model.setRowCount(0);

            List<Booking> list = BookingDAO.getBookingsForFacilityAndDay(facility.getFacilityId(), day);

            for (Booking b : list) {
                model.addRow(new Object[]{
                        b.getBookingId(),
                        b.getUserId(),
                        b.getDayOfWeek(),
                        b.getPeriod(),
                        b.getPersonCount()
                });
            }
        }

        else if (e.getSource()==btnBack) {
            this.dispose();
            if (Main.currentUser != null) new UserMainForm();
            else new UserTypeSelectionForm();
        }
    }
}
