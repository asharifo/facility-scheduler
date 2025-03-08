package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminUserList extends JFrame implements ActionListener {
    private final JButton back;
    private final String[][] data;
    private final String [] col;
    private final JTable table;

    public AdminUserList(){

        col = new String[]{"ID", "Username"};
        data = Main.getUserList();

        table = new JTable(data, col);
        table.setBounds(130,10,200,16*data.length);
        table.setEnabled(false);

        back = new JButton("Back");
        back.addActionListener(this);
        back.setFocusable(false);
        back.setBounds(10,400,100,25);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500,500);
        this.setLayout(null);
        this.setVisible(true);
        this.add(back);
        this.add(table);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==back){
            this.dispose();
            new AdminUserSchedule();
        }
    }
}
