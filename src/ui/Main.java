package ui;

import db.FacilityDAO;
import model.Facilities;
import model.User;

import javax.swing.*;
import java.util.Calendar;
import java.util.List;

public class Main {

    public static User currentUser;         
    public static List<Facilities> FACILITIES;  

    public static int AQI = 0;
    public static double heatIndex = 0.0;

    public static void main(String[] args) {

        FACILITIES = FacilityDAO.loadAllFacilities();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        new UserTypeSelectionForm();
    }

    public static boolean verifyUsernameOriginality(String username) {
        return !db.UserDAO.usernameExists(username);
    }

    // Monday–Friday only
    public static String getCurrentDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        int dow = cal.get(Calendar.DAY_OF_WEEK); // 1=Sunday, ..., 7=Saturday

        switch (dow) {
            case Calendar.MONDAY:    return "Monday";
            case Calendar.TUESDAY:   return "Tuesday";
            case Calendar.WEDNESDAY: return "Wednesday";
            case Calendar.THURSDAY:  return "Thursday";
            case Calendar.FRIDAY:    return "Friday";
            default:                 return "Monday";
        }
    }
}
