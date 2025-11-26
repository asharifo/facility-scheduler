package ui;

import db.BookingDAO;
import model.Booking;
import model.Facilities;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class UserSchedule extends JFrame implements ActionListener {

    private final JTable table;
    private final DefaultTableModel model;
    private final JButton back;
    private final JButton delete;

    public UserSchedule(){

        setTitle("Your Schedule - " + Main.currentUser.getUsername());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700,400);
        setLayout(null);

        model = new DefaultTableModel(new Object[]{"Booking ID","Facility","Day","Period","Students"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(20,20,640,250);

        delete = new JButton("Delete Selected");
        delete.addActionListener(this);
        delete.setFocusable(false);
        delete.setBounds(20,290,140,25);

        back = new JButton("Back");
        back.addActionListener(this);
        back.setFocusable(false);
        back.setBounds(20,320,100,25);

        add(scroll);
        add(delete);
        add(back);

        setLocationRelativeTo(null);
        setVisible(true);

        loadBookings();
    }

    private void loadBookings() {
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

        if (e.getSource() == delete) {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Please select a booking to delete", null, JOptionPane.WARNING_MESSAGE);
                return;
            }

            int bookingId = (int) model.getValueAt(row, 0);

            int confirm = JOptionPane.showConfirmDialog(this, "Delete selected booking?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                db.BookingDAO.deleteBookingById(bookingId);
                loadBookings();
                JOptionPane.showMessageDialog(this,"Your booking has been successfully removed",null,JOptionPane.INFORMATION_MESSAGE);
            }
        }
        else if (e.getSource() == back){
            this.dispose();
            new UserMainForm();
        }
    }
}
