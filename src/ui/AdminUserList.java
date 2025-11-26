package ui;

import db.UserDAO;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AdminUserList extends JFrame implements ActionListener {

    private final JTable table;
    private final DefaultTableModel model;
    private final JButton btnBack;

    public AdminUserList() {

        setTitle("All Users");
        setSize(500,400);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        model = new DefaultTableModel(new Object[]{"User ID","Username"},0){
            @Override public boolean isCellEditable(int r,int c){return false;}
        };

        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(20,20,450,280);
        add(scroll);

        btnBack = new JButton("Back");
        btnBack.setBounds(20,320,100,25);
        btnBack.addActionListener(this);
        add(btnBack);

        loadUsers();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadUsers() {
        model.setRowCount(0);
        List<User> users = UserDAO.getAllUsers();
        for (User u : users) model.addRow(new Object[]{u.getUserId(),u.getUsername()});
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==btnBack) {
            this.dispose();
            new AdminUserSchedule();
        }
    }
}
