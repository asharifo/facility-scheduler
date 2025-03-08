package ui;

import model.Booking;
import model.Facilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//consider adding a menu bar to be more user-friendly
public class UserMainForm extends JFrame implements ActionListener {

    private final Facilities sportCenter = new Facilities(false, 25);
    private final Facilities coveredCourts = new Facilities(true, 25);
    private final Facilities footballField = new Facilities(true, 30);
    private final Facilities tennisCourt = new Facilities(true, 5);

    private final JLabel labelPersonCount;
    private final JLabel labelFacility;
    private final JLabel labelDate;
    private final JLabel labelPeriod;
    private final JLabel labelBooking;
    private final JButton buttonSave;
    private final JButton facilitySchedule;
    private final JButton userSchedule;
    private final JTextField textPersonCount;
    private final JComboBox comboBoxFacility;
    private final JComboBox comboBoxDate;
    private final JComboBox comboBoxPeriod;
    private final JButton back;


    public UserMainForm() {

        Integer[] period = {1, 2, 3, 4, 5};
        String[] facilities = {"sports center", "covered courts", "football field", "tennis court"};
        String[] date = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};

        labelBooking = new JLabel("Create a booking:");
        labelBooking.setBounds(180, 50, 150, 25);
        labelBooking.setFont(new Font("Verdana", Font.BOLD, 12));
        labelPersonCount = new JLabel("How many students?");
        labelPersonCount.setBounds(50, 100, 150, 25);
        labelFacility = new JLabel("What facility?");
        labelFacility.setBounds(50, 150, 150, 25);
        labelDate = new JLabel("What day?");
        labelDate.setBounds(50, 200, 150, 25);
        labelPeriod = new JLabel("What period?");
        labelPeriod.setBounds(50, 250, 150, 25);
        buttonSave = new JButton("Save");
        buttonSave.addActionListener(this);
        buttonSave.setFocusable(false);
        buttonSave.setBounds(50, 300, 100, 40);
        facilitySchedule = new JButton("See facility Schedules");
        facilitySchedule.addActionListener(this);
        facilitySchedule.setFocusable(false);
        facilitySchedule.setBounds(50, 10, 180, 25);
        userSchedule = new JButton("See your schedule");
        userSchedule.addActionListener(this);
        userSchedule.setFocusable(false);
        userSchedule.setBounds(250, 10, 150, 25);
        back = new JButton("Back");
        back.addActionListener(this);
        back.setFocusable(false);
        back.setBounds(10, 400, 100, 25);

        textPersonCount = new JTextField();
        textPersonCount.setBounds(250, 100, 150, 25);
        comboBoxFacility = new JComboBox(facilities);
        comboBoxFacility.setBounds(250, 150, 150, 25);
        comboBoxDate = new JComboBox(date);
        comboBoxDate.setBounds(250, 200, 150, 25);
        comboBoxPeriod = new JComboBox(period);
        comboBoxPeriod.setBounds(250, 250, 150, 25);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setLayout(null);
        this.add(labelBooking);
        this.add(labelPersonCount);
        this.add(labelFacility);
        this.add(labelDate);
        this.add(labelPeriod);
        this.add(textPersonCount);
        this.add(comboBoxFacility);
        this.add(comboBoxDate);
        this.add(comboBoxPeriod);
        this.add(buttonSave);
        this.add(userSchedule);
        this.add(facilitySchedule);
        this.add(back);
        this.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttonSave && !textPersonCount.getText().isEmpty()) {
            if (comboBoxFacility.getSelectedIndex() == 0) {
                try{
                if ((Integer.parseInt(textPersonCount.getText())) <= sportCenter.getMaxStudentCapacity()) {
                    Booking booking;
                    booking = new Booking(comboBoxFacility.getSelectedItem().toString(), Integer.parseInt(textPersonCount.getText()),
                            (Integer) comboBoxPeriod.getSelectedItem(), comboBoxDate.getSelectedItem().toString(), Main.currentUser.getUsername());
                    if (Main.verifyBookingWithUserSchedule(booking) && Main.verifyBookingWithFacilitySchedule(booking)) {
                        sportCenter.addBookingToSportsCenter(booking);
                        JOptionPane.showMessageDialog(null, "Booking added successfully", null, JOptionPane.INFORMATION_MESSAGE);
                    } else if (!Main.verifyBookingWithUserSchedule(booking)) {
                        JOptionPane.showMessageDialog(null, "You already have a booking at this time", null, JOptionPane.WARNING_MESSAGE);
                    } else if (!Main.verifyBookingWithFacilitySchedule(booking)) {
                        JOptionPane.showMessageDialog(null, "The facility is already booked at this time", null, JOptionPane.WARNING_MESSAGE);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "This facility does not support the specified student count", null, JOptionPane.WARNING_MESSAGE);
                }
                }catch(NumberFormatException j){
                    JOptionPane.showMessageDialog(null, "Please enter an appropriate student count", null, JOptionPane.WARNING_MESSAGE);
                }
            } else if (comboBoxFacility.getSelectedIndex() == 1) {
                try {
                    if ((Integer.parseInt(textPersonCount.getText())) <= coveredCourts.getMaxStudentCapacity()) {
                        Booking booking;
                        booking = new Booking(comboBoxFacility.getSelectedItem().toString(), Integer.parseInt(textPersonCount.getText()),
                                (Integer) comboBoxPeriod.getSelectedItem(), comboBoxDate.getSelectedItem().toString(), Main.currentUser.getUsername());
                        if (Main.verifyBookingWithUserSchedule(booking) && Main.verifyBookingWithFacilitySchedule(booking)) {
                            coveredCourts.addBookingToCoveredCourts(booking);
                            JOptionPane.showMessageDialog(null, "Booking added successfully", null, JOptionPane.INFORMATION_MESSAGE);
                        } else if (!Main.verifyBookingWithUserSchedule(booking)) {
                            JOptionPane.showMessageDialog(null, "You already have a booking at this time", null, JOptionPane.WARNING_MESSAGE);
                        } else if (!Main.verifyBookingWithFacilitySchedule(booking)) {
                            JOptionPane.showMessageDialog(null, "The facility is already booked at this time", null, JOptionPane.WARNING_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "This facility does not support the specified student count", null, JOptionPane.WARNING_MESSAGE);
                    }
                }catch (NumberFormatException j){
                    JOptionPane.showMessageDialog(null, "Please enter an appropriate student count", null, JOptionPane.WARNING_MESSAGE);
                }
            } else if (comboBoxFacility.getSelectedIndex() == 2) {
                try {
                    if ((Integer.parseInt(textPersonCount.getText())) <= footballField.getMaxStudentCapacity()) {
                        Booking booking;
                        booking = new Booking(comboBoxFacility.getSelectedItem().toString(), Integer.parseInt(textPersonCount.getText()),
                                (Integer) comboBoxPeriod.getSelectedItem(), comboBoxDate.getSelectedItem().toString(), Main.currentUser.getUsername());
                        if (Main.verifyBookingWithUserSchedule(booking) && Main.verifyBookingWithFacilitySchedule(booking)) {
                            footballField.addBookingToFootballField(booking);
                            JOptionPane.showMessageDialog(null, "Booking added successfully", null, JOptionPane.INFORMATION_MESSAGE);
                        } else if (!Main.verifyBookingWithUserSchedule(booking)) {
                            JOptionPane.showMessageDialog(null, "You already have a booking at this time", null, JOptionPane.WARNING_MESSAGE);
                        } else if (!Main.verifyBookingWithFacilitySchedule(booking)) {
                            JOptionPane.showMessageDialog(null, "The facility is already booked at this time", null, JOptionPane.WARNING_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "This facility does not support the specified student count", null, JOptionPane.WARNING_MESSAGE);
                    }
                }catch (NumberFormatException j){
                    JOptionPane.showMessageDialog(null, "Please enter an appropriate student count", null, JOptionPane.WARNING_MESSAGE);
                }
            } else if (comboBoxFacility.getSelectedIndex() == 3) {
                try {
                    if ((Integer.parseInt(textPersonCount.getText())) <= tennisCourt.getMaxStudentCapacity()) {
                        Booking booking;
                        booking = new Booking(comboBoxFacility.getSelectedItem().toString(), Integer.parseInt(textPersonCount.getText()),
                                (Integer) comboBoxPeriod.getSelectedItem(), comboBoxDate.getSelectedItem().toString(), Main.currentUser.getUsername());
                        if (Main.verifyBookingWithUserSchedule(booking) && Main.verifyBookingWithFacilitySchedule(booking)) {
                            tennisCourt.addBookingToTennisCourt(booking);
                            JOptionPane.showMessageDialog(null, "Booking added successfully", null, JOptionPane.INFORMATION_MESSAGE);
                        } else if (!Main.verifyBookingWithUserSchedule(booking)) {
                            JOptionPane.showMessageDialog(null, "You already have a booking at this time", null, JOptionPane.WARNING_MESSAGE);
                        } else if (!Main.verifyBookingWithFacilitySchedule(booking)) {
                            JOptionPane.showMessageDialog(null, "The facility is already booked at this time", null, JOptionPane.WARNING_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "This facility does not support the specified student count", null, JOptionPane.WARNING_MESSAGE);
                    }
                }catch (NumberFormatException j){
                    JOptionPane.showMessageDialog(null, "Please enter an appropriate student count", null, JOptionPane.WARNING_MESSAGE);
                }
            }
            } else if (e.getSource() == buttonSave && textPersonCount.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter the student count", null, JOptionPane.WARNING_MESSAGE);
            }

            if (e.getSource() == facilitySchedule) {
                this.dispose();
                new FacilitySchedule();
            }
            if (e.getSource() == userSchedule) {
                this.dispose();
                new UserSchedule();
            }
            if (e.getSource() == back) {
                this.dispose();
                new UserLoginForm();
            }

    }

}

