package ui;

import db.BookingDAO;
import model.Booking;
import model.Facilities;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class UserMainForm extends JFrame implements ActionListener {

    private final JComboBox<Facilities> comboFacility;
    private final JComboBox<String> comboDay;
    private final JComboBox<Integer> comboPeriod;
    private final JTextField textPersonCount;
    private final JButton btnSave;
    private final JButton btnLogout;
    private final JButton btnDelete;

    private final JTable table;
    private final DefaultTableModel model;

    public UserMainForm() {

        setTitle("Sports Booking - User Dashboard (" + Main.currentUser.getUsername() + ")");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setSize(800,500);

        JLabel lblFacility = new JLabel("Facility:");
        lblFacility.setBounds(30,30,80,25);

        comboFacility = new JComboBox<>();
        for (Facilities f : Main.FACILITIES) {
            comboFacility.addItem(f);
        }
        comboFacility.setBounds(120,30,200,25);

        JLabel lblDay = new JLabel("Day:");
        lblDay.setBounds(30,70,80,25);

        String[] days = {"Monday","Tuesday","Wednesday","Thursday","Friday"};
        comboDay = new JComboBox<>(days);
        comboDay.setBounds(120,70,200,25);

        JLabel lblPeriod = new JLabel("Period:");
        lblPeriod.setBounds(30,110,80,25);

        Integer[] periods = {1,2,3,4,5};
        comboPeriod = new JComboBox<>(periods);
        comboPeriod.setBounds(120,110,200,25);

        JLabel lblCount = new JLabel("Students:");
        lblCount.setBounds(30,150,80,25);

        textPersonCount = new JTextField();
        textPersonCount.setBounds(120,150,200,25);

        btnSave = new JButton("Create Booking");
        btnSave.setBounds(30,200,140,30);
        btnSave.addActionListener(this);

        btnDelete = new JButton("Delete Selected");
        btnDelete.setBounds(180,200,140,30);
        btnDelete.addActionListener(this);

        btnLogout = new JButton("Logout");
        btnLogout.setBounds(30,400,100,30);
        btnLogout.addActionListener(this);

        // Table of bookings
        model = new DefaultTableModel(new Object[]{"ID","Facility","Day","Period","Students"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(350,30,400,350);

        add(lblFacility);
        add(comboFacility);
        add(lblDay);
        add(comboDay);
        add(lblPeriod);
        add(comboPeriod);
        add(lblCount);
        add(textPersonCount);
        add(btnSave);
        add(btnDelete);
        add(btnLogout);
        add(scroll);

        setLocationRelativeTo(null);
        setVisible(true);

        loadUserBookings();
    }

    private void loadUserBookings() {
        model.setRowCount(0);
        List<Booking> bookings = BookingDAO.getBookingsForUser(Main.currentUser.getUserId());

        for (Booking b : bookings) {
            Facilities f = Main.FACILITIES.stream()
                    .filter(x -> x.getFacilityId() == b.getFacilityId())
                    .findFirst()
                    .orElse(null);

            String facilityName = (f != null) ? f.getName() : ("ID " + b.getFacilityId());

            model.addRow(new Object[]{
                    b.getBookingId(),
                    facilityName,
                    b.getDayOfWeek(),
                    b.getPeriod(),
                    b.getPersonCount()
            });
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnSave) {
            String countText = textPersonCount.getText().trim();
            if (countText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter student count", null, JOptionPane.WARNING_MESSAGE);
                return;
            }

            int count;
            try {
                count = Integer.parseInt(countText);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Student count must be a number", null, JOptionPane.WARNING_MESSAGE);
                return;
            }

            Facilities facility = (Facilities) comboFacility.getSelectedItem();
            if (facility == null) {
                JOptionPane.showMessageDialog(this, "Please select a facility", null, JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (count > facility.getMaxCapacity()) {
                JOptionPane.showMessageDialog(this, "This facility does not support that many students (max " + facility.getMaxCapacity() + ")", null, JOptionPane.WARNING_MESSAGE);
                return;
            }

            String day = (String) comboDay.getSelectedItem();
            int period = (Integer) comboPeriod.getSelectedItem();

            Booking booking = new Booking(
                    0,
                    Main.currentUser.getUserId(),
                    facility.getFacilityId(),
                    day,
                    period,
                    count
            );

            int result = BookingDAO.createBooking(booking);

            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Booking added successfully", null, JOptionPane.INFORMATION_MESSAGE);
                loadUserBookings();
            } else if (result == -2) {
                JOptionPane.showMessageDialog(this, "Conflict: either you already have a booking at this time, or the facility is already booked.", null, JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Error creating booking", null, JOptionPane.ERROR_MESSAGE);
            }
        }

        else if (e.getSource() == btnDelete) {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Please select a booking to delete", null, JOptionPane.WARNING_MESSAGE);
                return;
            }

            int bookingId = (int) model.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Delete selected booking?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                BookingDAO.deleteBooking(bookingId, Main.currentUser.getUserId());
                loadUserBookings();
            }
        }

        else if (e.getSource() == btnLogout) {
            Main.currentUser = null;
            this.dispose();
            new UserLoginForm();
        }
    }
}
