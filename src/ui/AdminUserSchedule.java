package ui;

import db.BookingDAO;
import db.UserDAO;
import model.Booking;
import model.Facilities;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AdminUserSchedule extends JFrame implements ActionListener {

    private final JTextField inputField;
    private final JButton btnSearch;
    private final JButton btnDelete;
    private final JButton btnUserList;
    private final JButton btnBack;

    private final JTable table;
    private final DefaultTableModel model;

    private User targetUser;

    public AdminUserSchedule() {

        setTitle("Admin - User Schedule");
        setSize(900,450);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel lbl = new JLabel("Enter username or user ID:");
        lbl.setBounds(20,20,250,25);
        add(lbl);

        inputField = new JTextField();
        inputField.setBounds(20,50,200,25);
        add(inputField);

        btnSearch = new JButton("Search");
        btnSearch.setBounds(240,50,100,25);
        btnSearch.addActionListener(this);
        add(btnSearch);

        btnDelete = new JButton("Delete Selected Booking");
        btnDelete.setBounds(20,100,200,25);
        btnDelete.addActionListener(this);
        add(btnDelete);

        btnUserList = new JButton("View All Users");
        btnUserList.setBounds(20,140,200,25);
        btnUserList.addActionListener(this);
        add(btnUserList);

        btnBack = new JButton("Back");
        btnBack.setBounds(20,360,100,25);
        btnBack.addActionListener(this);
        add(btnBack);

        model = new DefaultTableModel(new Object[]{"ID","Facility","Day","Period","Students"},0){
            @Override public boolean isCellEditable(int r,int c){return false;}
        };

        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(350,20,520,350);
        add(scroll);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadBookings(User user) {
        targetUser = user;
        model.setRowCount(0);

        List<Booking> bookings = BookingDAO.getBookingsForUser(user.getUserId());

        for (Booking b : bookings) {
            Facilities f = Main.FACILITIES.stream()
                    .filter(x -> x.getFacilityId() == b.getFacilityId())
                    .findFirst().orElse(null);

            model.addRow(new Object[]{
                    b.getBookingId(),
                    (f != null ? f.getName() : "Facility " + b.getFacilityId()),
                    b.getDayOfWeek(),
                    b.getPeriod(),
                    b.getPersonCount()
            });
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnSearch) {
            String text = inputField.getText().trim();

            User found = null;

            try {
                int id = Integer.parseInt(text);
                found = UserDAO.findById(id);
            } catch (NumberFormatException ignore) {
                found = UserDAO.findByUsername(text);
            }

            if (found == null) {
                JOptionPane.showMessageDialog(this,"User not found.");
                return;
            }

            loadBookings(found);
        }

        else if (e.getSource() == btnDelete) {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this,"Select a booking first.");
                return;
            }

            int bookingId = (int) model.getValueAt(row, 0);

            BookingDAO.deleteBookingById(bookingId);
            model.removeRow(row);

            JOptionPane.showMessageDialog(this,"Booking deleted.");
        }

        else if (e.getSource() == btnUserList) {
            this.dispose();
            new AdminUserList();
        }

        else if (e.getSource() == btnBack) {
            this.dispose();
            new AdminMainForm();
        }
    }
}
