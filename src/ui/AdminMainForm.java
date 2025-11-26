package ui;

import db.BookingDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AdminMainForm extends JFrame implements ActionListener {

    private final JTable table;
    private final DefaultTableModel model;
    private final JButton btnRefresh;
    private final JButton btnDelete;
    private final JButton btnUserSchedules;
    private final JButton btnBack;
    private final JLabel lblEnv;

    public AdminMainForm() {

        setTitle("Admin Dashboard");
        setSize(900,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        lblEnv = new JLabel();
        lblEnv.setBounds(20,20,700,25);
        add(lblEnv);

        btnRefresh = new JButton("Refresh endangered bookings");
        btnRefresh.setBounds(20,60,250,30);
        btnRefresh.addActionListener(this);
        add(btnRefresh);

        btnDelete = new JButton("Delete Selected");
        btnDelete.setBounds(280,60,150,30);
        btnDelete.addActionListener(this);
        add(btnDelete);

        btnUserSchedules = new JButton("Manage User Schedules");
        btnUserSchedules.setBounds(450,60,200,30);
        btnUserSchedules.addActionListener(this);
        add(btnUserSchedules);

        btnBack = new JButton("Logout");
        btnBack.setBounds(20,400,100,30);
        btnBack.addActionListener(this);
        add(btnBack);

        model = new DefaultTableModel(new Object[]{"ID","User","Facility","Day","Period","Students"},0){
            @Override
            public boolean isCellEditable(int r,int c){return false;}
        };

        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(20,110,840,260);
        add(scroll);

        setLocationRelativeTo(null);
        setVisible(true);

        refreshEnvLabel();
        loadEndangeredBookings();
    }

    private void refreshEnvLabel() {
        lblEnv.setText("AQI: " + Main.AQI + "  |  Heat Index: " + Main.heatIndex
                + "  (Thresholds: AQI ≥ 150 OR Heat ≥ 40°C)");
    }

    private void loadEndangeredBookings() {
        model.setRowCount(0);

        if (Main.AQI < 150 && Main.heatIndex < 40) return;

        String today = Main.getCurrentDayOfWeek();

        List<Object[]> rows = BookingDAO.getOutsideBookingsForDay(today);
        for (Object[] row : rows) model.addRow(row);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnRefresh) {
            refreshEnvLabel();
            loadEndangeredBookings();
        }

        else if (e.getSource() == btnDelete) {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this,"Select a booking first");
                return;
            }

            int bookingId = (int) model.getValueAt(row, 0);
            BookingDAO.deleteBookingById(bookingId);
            model.removeRow(row);

            JOptionPane.showMessageDialog(this,"Booking deleted.");
        }

        else if (e.getSource() == btnUserSchedules) {
            this.dispose();
            new AdminUserSchedule();
        }

        else if (e.getSource() == btnBack) {
            this.dispose();
            new UserTypeSelectionForm();
        }
    }
}
