package ui;

import model.Booking;
import java.io.*;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;
import model.User;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.*;

public class Main {

    static public int AQI;
    static public double heatIndex;
    static public String sportCenterSheetPath = "./data/sportsCenter.csv";
    static public String footballFieldSheetPath = "./data/footballField.csv";
    static public String coveredCourtsSheetPath = "./data/coveredCourts.csv";
    static public String tennisCourtSheetPath = "./data/tennisCourt.csv";
    static public String userListPath = "./data/userInfo.csv";
    static public int currentDate;
    static public User currentUser;

    public static void main(String[] args) {
        setAqiAndHeatIndeces();
        new UserTypeSelectionForm();
        Calendar calendar = Calendar.getInstance();
        Main.currentDate = calendar.get(Calendar.DAY_OF_WEEK);
    }

    // requests current AQI from Google Air Quality API and temperature from
    // Openweather API and updates public AQI and heatIndex fields
    public static void setAqiAndHeatIndeces() {
        try {
            String urlString = "https://airquality.googleapis.com/v1/currentConditions:lookup?key=AIzaSyDOkv2_09XUumV4s5sjKwLyf6vhBv__4-o";
            String body = "{\n" + //
                    "  \"universalAqi\": true,\n" + //
                    "  \"location\": {\n" + //
                    "    \"latitude\": 21.075160214788035,\n" + //
                    "    \"longitude\": 105.80680163778088\n" + //
                    "  }\n" + //
                    "}";
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            StringBuilder stringBuilder = new StringBuilder();
            try (DataOutputStream dos = new DataOutputStream(conn.getOutputStream())) {
                dos.writeBytes(body);
            }
            try (BufferedReader bf = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String line;
                while ((line = bf.readLine()) != null) {
                    stringBuilder.append(line);
                }
            }
            JSONObject jsonObj = new JSONObject(stringBuilder.toString());
            JSONArray jsonArr = jsonObj.getJSONArray("indexes");
            AQI = Integer.valueOf(jsonArr.getJSONObject(0).get("aqi").toString());
        } catch (Exception e) {
        }

        try {
            String urlString = "\n" + //
                    "https://api.openweathermap.org/data/2.5/weather?lat=21.075160214788035&lon=105.80680163778088&appid=eee2e4aefe0acee6d202c1a3b23846c7";
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            StringBuilder stringBuilder = new StringBuilder();
            Scanner scanner = new Scanner(conn.getInputStream());
            while (scanner.hasNext()) {
                stringBuilder.append(scanner.nextLine());
            }
            JSONObject jsonObj = new JSONObject(stringBuilder.toString());
            JSONObject tempList = jsonObj.getJSONObject("main");
            Object tempKelvin = tempList.get("temp");
            heatIndex = Double.valueOf(tempKelvin.toString()) - 273.15;
            heatIndex = heatIndex * Math.pow(10, 1); 
            heatIndex = Math.floor(heatIndex); 
            heatIndex = heatIndex / Math.pow(10, 1);
        } catch (Exception e) {
        }
    }

    // returns facility schedule of given facility
    static public Object[][] getFacilitySchedule(String s) {
        if (s.equalsIgnoreCase("Sports Center")) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(sportCenterSheetPath));
                ArrayList<String> list = new ArrayList<>();
                String str;
                while ((str = br.readLine()) != null) {
                    list.add(str);
                }
                int column = list.get(0).split(",").length;
                Object[][] data = new Object[list.size()][column];
                for (int i = 0; i < list.size(); i++) {
                    data[i] = list.get(i).split(",");
                }
                br.close();
                return data;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        if (s.equalsIgnoreCase("Football Field")) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(footballFieldSheetPath));
                ArrayList<String> list = new ArrayList<>();
                String str = "";
                while ((str = br.readLine()) != null) {
                    list.add(str);
                }
                int column = list.get(0).split(",").length;
                Object[][] data = new Object[list.size()][column];
                for (int i = 0; i < list.size(); i++) {
                    data[i] = list.get(i).split(",");
                }
                br.close();
                return data;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }
        if (s.equalsIgnoreCase("Tennis Court")) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(tennisCourtSheetPath));
                ArrayList<String> list = new ArrayList<>();
                String str = "";
                while ((str = br.readLine()) != null) {
                    list.add(str);
                }
                int column = list.get(0).split(",").length;
                Object[][] data = new Object[list.size()][column];
                for (int i = 0; i < list.size(); i++) {
                    data[i] = list.get(i).split(",");
                }
                br.close();
                return data;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }
        if (s.equals("Covered Courts")) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(coveredCourtsSheetPath));
                ArrayList<String> list = new ArrayList<>();
                String str = "";
                while ((str = br.readLine()) != null) {
                    list.add(str);
                }
                int column = list.get(0).split(",").length;
                Object[][] data = new Object[list.size()][column];
                for (int i = 0; i < list.size(); i++) {
                    data[i] = list.get(i).split(",");
                }
                br.close();
                return data;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    // returns current user count
    static public int getUserCount() {
        String line;
        String[] lineArr = new String[0];
        try {
            BufferedReader br = new BufferedReader(new FileReader(userListPath));
            line = br.readLine();
            lineArr = line.split(",");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Integer.parseInt(lineArr[3]);
    }

    static public void setUserCount(int userCount) {
        String line;
        String[] lineArr;
        ArrayList<String> temp = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(userListPath));
            while ((line = br.readLine()) != null) {
                lineArr = line.split(",");
                if (lineArr[0].equals("ID")) {
                    temp.add(
                            lineArr[0] + "," +
                                    lineArr[1] + "," +
                                    lineArr[2] + "," +
                                    userCount);
                } else {
                    temp.add(line);
                }
            }
            br.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            try (PrintWriter pr = new PrintWriter(Main.userListPath)) {
                for (String str : temp) {
                    pr.println(str);

                }
            } catch (Exception e) {

            }
        } catch (Exception e) {

        }

    }

    // returns list of users
    static public String[][] getUserList() {
        String line;
        String[] lineArr;
        String[][] data = new String[16][2];
        int row = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(userListPath));
            while ((line = br.readLine()) != null) {
                lineArr = line.split(",");
                for (int i = 0; i < 2; i++) {
                    data[row][i] = lineArr[i];
                }
                row++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    // returns schedule of user with given username
    static public String[][] getUserSchedule(String username) {
        String[][] data = new String[6][6];
        try {
            // first read the sports center file
            BufferedReader br = new BufferedReader(new FileReader(sportCenterSheetPath));
            String str;
            String[] list;
            // loop through all rows in the file
            for (int i = 0; i < 6; i++) {
                str = br.readLine();
                list = str.split(",");
                // loop through all columns in the file
                for (int j = 0; j < 6; j++) {
                    // check if the username is present at the given index
                    if (list[j].equals(username)) {
                        data[i][j] = "Sports Center";
                    } else if (list[j].equals("Period") || list[j].equals("Monday") ||
                            list[j].equals("Tuesday") || list[j].equals("Wednesday") ||
                            list[j].equals("Thursday") || list[j].equals("Friday") ||
                            list[j].equals("1") || list[j].equals("2") || list[j].equals("3")
                            || list[j].equals("4") || list[j].equals("5")) {
                        data[i][j] = list[j];
                    }

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(coveredCourtsSheetPath));
            String str;
            String[] list;
            for (int i = 0; i < 6; i++) {
                str = br.readLine();
                list = str.split(",");
                for (int j = 0; j < 6; j++) {
                    if (list[j].equals(username)) {
                        data[i][j] = "Covered Courts";
                    } else if (list[j].equals("Period") || list[j].equals("Monday") || list[j].equals("Tuesday")
                            || list[j].equals("Wednesday") || list[j].equals("Thursday") || list[j].equals("Friday")
                            || list[j].equals("1") || list[j].equals("2") || list[j].equals("3") || list[j].equals("4")
                            || list[j].equals("5")) {
                        data[i][j] = list[j];
                    }

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(tennisCourtSheetPath));
            String str;
            String[] list;
            for (int i = 0; i < 6; i++) {
                str = br.readLine();
                list = str.split(",");
                for (int j = 0; j < 6; j++) {
                    if (list[j].equals(username)) {
                        data[i][j] = "Tennis Court";
                    } else if (list[j].equals("Period") || list[j].equals("Monday") || list[j].equals("Tuesday")
                            || list[j].equals("Wednesday") || list[j].equals("Thursday") || list[j].equals("Friday")
                            || list[j].equals("1") || list[j].equals("2") || list[j].equals("3") || list[j].equals("4")
                            || list[j].equals("5")) {
                        data[i][j] = list[j];
                    }

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(footballFieldSheetPath));
            String str;
            String[] list;
            for (int i = 0; i < 6; i++) {
                str = br.readLine();
                list = str.split(",");
                for (int j = 0; j < 6; j++) {
                    if (list[j].equals(username)) {
                        data[i][j] = "Football Field";
                    } else if (list[j].equals("Period") || list[j].equals("Monday") || list[j].equals("Tuesday")
                            || list[j].equals("Wednesday") || list[j].equals("Thursday") || list[j].equals("Friday")
                            || list[j].equals("1") || list[j].equals("2") || list[j].equals("3") || list[j].equals("4")
                            || list[j].equals("5")) {
                        data[i][j] = list[j];
                    }

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    // returns user schedule of user with given ID
    static public String[][] getUserSchedule(int ID) {
        String username = getUsername(Integer.valueOf(ID));
        String[][] data = new String[6][6];
        try {
            // first read the sports center file
            BufferedReader br = new BufferedReader(new FileReader(sportCenterSheetPath));
            String str;
            String[] list;
            // loop through all rows in the file
            for (int i = 0; i < 6; i++) {
                str = br.readLine();
                list = str.split(",");
                // loop through all columns in the file
                for (int j = 0; j < 6; j++) {
                    // check if the username is present at the given index
                    if (list[j].equals(username)) {
                        data[i][j] = "Sports Center";
                    } else if (list[j].equals("Period") || list[j].equals("Monday") ||
                            list[j].equals("Tuesday") || list[j].equals("Wednesday") ||
                            list[j].equals("Thursday") || list[j].equals("Friday") ||
                            list[j].equals("1") || list[j].equals("2") || list[j].equals("3")
                            || list[j].equals("4") || list[j].equals("5")) {
                        data[i][j] = list[j];
                    }

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(coveredCourtsSheetPath));
            String str;
            String[] list;
            for (int i = 0; i < 6; i++) {
                str = br.readLine();
                list = str.split(",");
                for (int j = 0; j < 6; j++) {
                    if (list[j].equals(username)) {
                        data[i][j] = "Covered Courts";
                    } else if (list[j].equals("Period") || list[j].equals("Monday") || list[j].equals("Tuesday")
                            || list[j].equals("Wednesday") || list[j].equals("Thursday") || list[j].equals("Friday")
                            || list[j].equals("1") || list[j].equals("2") || list[j].equals("3") || list[j].equals("4")
                            || list[j].equals("5")) {
                        data[i][j] = list[j];
                    }

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(tennisCourtSheetPath));
            String str;
            String[] list;
            for (int i = 0; i < 6; i++) {
                str = br.readLine();
                list = str.split(",");
                for (int j = 0; j < 6; j++) {
                    if (list[j].equals(username)) {
                        data[i][j] = "Tennis Court";
                    } else if (list[j].equals("Period") || list[j].equals("Monday") || list[j].equals("Tuesday")
                            || list[j].equals("Wednesday") || list[j].equals("Thursday") || list[j].equals("Friday")
                            || list[j].equals("1") || list[j].equals("2") || list[j].equals("3") || list[j].equals("4")
                            || list[j].equals("5")) {
                        data[i][j] = list[j];
                    }

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(footballFieldSheetPath));
            String str;
            String[] list;
            for (int i = 0; i < 6; i++) {
                str = br.readLine();
                list = str.split(",");
                for (int j = 0; j < 6; j++) {
                    if (list[j].equals(username)) {
                        data[i][j] = "Football Field";
                    } else if (list[j].equals("Period") || list[j].equals("Monday") || list[j].equals("Tuesday")
                            || list[j].equals("Wednesday") || list[j].equals("Thursday") || list[j].equals("Friday")
                            || list[j].equals("1") || list[j].equals("2") || list[j].equals("3") || list[j].equals("4")
                            || list[j].equals("5")) {
                        data[i][j] = list[j];
                    }

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    // returns true if given username is original (not found in userInfo.csv)
    public static boolean verifyUsernameOriginality(String username) {
        boolean original = true;
        String line;
        String[] lineArr;
        try {
            BufferedReader br = new BufferedReader(new FileReader(userListPath));
            while ((line = br.readLine()) != null) {
                lineArr = line.split(",");
                if (lineArr[1].equals(username)) {
                    original = false;
                } else {
                }
            }
        } catch (FileNotFoundException b) {
            b.printStackTrace();
        } catch (IOException b) {
            b.printStackTrace();
        }
        return original;
    }

    // returns true if given booking does not interfere with other bookings in the
    // current user's schedule
    public static boolean verifyBookingWithUserSchedule(Booking booking) {
        boolean original = true;
        int column = 0;
        if (booking.getDate().equals("Monday")) {
            column = 1;
        } else if (booking.getDate().equals("Tuesday")) {
            column = 2;
        } else if (booking.getDate().equals("Wednesday")) {
            column = 3;
        } else if (booking.getDate().equals("Thursday")) {
            column = 4;
        } else if (booking.getDate().equals("Friday")) {
            column = 5;
        }
        Object[][] schedule = getUserSchedule(currentUser.getUsername());
        if (schedule[booking.getPeriod()][column] != null) {
            original = false;
        }

        return original;
    }

    // returns true if booking does not interfere with other bookings in the booked
    // facility's schedule
    public static boolean verifyBookingWithFacilitySchedule(Booking booking) {
        boolean original = true;
        int column = 0;
        if (booking.getDate().equals("Monday")) {
            column = 1;
        } else if (booking.getDate().equals("Tuesday")) {
            column = 2;
        } else if (booking.getDate().equals("Wednesday")) {
            column = 3;
        } else if (booking.getDate().equals("Thursday")) {
            column = 4;
        } else if (booking.getDate().equals("Friday")) {
            column = 5;
        }
        Object[][] schedule = getUserSchedule(booking.getUsername());
        if (booking.getFacility().equalsIgnoreCase("sports center")) {
            if (!(getFacilitySchedule("Sports Center")[booking.getPeriod()][column]).equals("x")) {
                original = false;
            }
        } else if (booking.getFacility().equalsIgnoreCase("Covered Courts")) {
            if (!(getFacilitySchedule("Covered Courts")[booking.getPeriod()][column]).equals("x")) {
                original = false;
            }
        } else if (booking.getFacility().equalsIgnoreCase("Tennis Court")) {
            if (!(getFacilitySchedule("Tennis Court")[booking.getPeriod()][column]).equals("x")) {
                original = false;
            }
        } else if (booking.getFacility().equalsIgnoreCase("Football Field")) {
            if (!(getFacilitySchedule("Football Field")[booking.getPeriod()][column]).equals("x")) {
                original = false;
            }
        }
        return original;
    }

    // deletes booking from user's and booked facility's schedules
    public static void deleteBooking(int row, int column, String username) {
        Object[][] data = getUserSchedule(username);
        String facility = data[row][column].toString();
        String line;
        String[] lineArr;
        ArrayList<String> tempArray = new ArrayList<>();

        if (facility.equalsIgnoreCase("Sports Center")) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(sportCenterSheetPath));
                while ((line = br.readLine()) != null) {
                    tempArray.add(line);
                }
                line = tempArray.get(row);
                lineArr = line.split(",");
                lineArr[column] = "x";
                tempArray.set(row, lineArr[0] + "," + lineArr[1] + "," + lineArr[2] + "," + lineArr[3] + ","
                        + lineArr[4] + "," + lineArr[5]);

            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                try (PrintWriter pr = new PrintWriter(sportCenterSheetPath)) {
                    for (String str : tempArray) {
                        pr.println(str);
                    }
                } catch (Exception e) {
                }
            } catch (Exception e) {

            }
        }
        if (facility.equalsIgnoreCase("Covered Courts")) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(coveredCourtsSheetPath));
                while ((line = br.readLine()) != null) {
                    tempArray.add(line);
                }
                line = tempArray.get(row);
                lineArr = line.split(",");
                lineArr[column] = "x";
                tempArray.set(row, lineArr[0] + "," + lineArr[1] + "," + lineArr[2] + "," + lineArr[3] + ","
                        + lineArr[4] + "," + lineArr[5]);

            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                try (PrintWriter pr = new PrintWriter(coveredCourtsSheetPath)) {
                    for (String str : tempArray) {
                        pr.println(str);
                    }
                } catch (Exception e) {
                }
            } catch (Exception e) {

            }
        }
        if (facility.equalsIgnoreCase("Football Field")) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(footballFieldSheetPath));
                while ((line = br.readLine()) != null) {
                    tempArray.add(line);
                }
                line = tempArray.get(row);
                lineArr = line.split(",");
                lineArr[column] = "x";
                tempArray.set(row, lineArr[0] + "," + lineArr[1] + "," + lineArr[2] + "," + lineArr[3] + ","
                        + lineArr[4] + "," + lineArr[5]);

            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                try (PrintWriter pr = new PrintWriter(footballFieldSheetPath)) {
                    for (String str : tempArray) {
                        pr.println(str);
                    }
                } catch (Exception e) {
                }
            } catch (Exception e) {

            }
        }
        if (facility.equalsIgnoreCase("Tennis Court")) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(tennisCourtSheetPath));
                while ((line = br.readLine()) != null) {
                    tempArray.add(line);
                }
                line = tempArray.get(row);
                lineArr = line.split(",");
                lineArr[column] = "x";
                tempArray.set(row, lineArr[0] + "," + lineArr[1] + "," + lineArr[2] + "," + lineArr[3] + ","
                        + lineArr[4] + "," + lineArr[5]);

            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                try (PrintWriter pr = new PrintWriter(tennisCourtSheetPath)) {
                    for (String str : tempArray) {
                        pr.println(str);
                    }
                } catch (Exception e) {
                }
            } catch (Exception e) {

            }
        }
    }

    public static String getUsername(int id) {
        String line;
        String[] lineArr = new String[0];
        try {
            BufferedReader br = new BufferedReader(new FileReader(userListPath));
            for (int i = 0; i < id + 1; i++) {
                line = br.readLine();
                lineArr = line.split(",");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lineArr[1];
    }

    static public int getID(String username) {
        String line;
        String[] lineArr;
        int id = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(Main.userListPath));
            while ((line = br.readLine()) != null) {
                lineArr = line.split(",");
                if (lineArr[1].equals(username)) {
                    id = Integer.parseInt(lineArr[0]);
                } else {
                }
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return id;

    }

    // returns list of bookings that are outside when AQI/heatIndex are above
    // threshold values
    static public ArrayList<String> getEndangeredBookings() {
        Object[][] facilitySchedule;
        ArrayList<String> endangeredBookings = new ArrayList<>();

        if (AQI >= 150 || heatIndex >= 40) {
            facilitySchedule = getFacilitySchedule("Football Field");
            for (int i = 1; i < facilitySchedule.length; i++) {
                // Monday
                if (currentDate == 2) {
                    if (!(facilitySchedule[i][1].equals("x"))) {
                        endangeredBookings.add("Facility: Football Field; Period: " + i);
                    }
                }
                // Tuesday
                if (currentDate == 3) {
                    if (!(facilitySchedule[i][2].equals("x"))) {
                        endangeredBookings.add("Facility: Football Field; Period: " + i);
                    }
                }
                // Wednesday
                if (currentDate == 4) {
                    if (!(facilitySchedule[i][3].equals("x"))) {
                        endangeredBookings.add("Facility: Football Field; Period: " + i);
                    }
                }
                // Thursday
                if (currentDate == 5) {
                    if (!(facilitySchedule[i][4].equals("x"))) {
                        endangeredBookings.add("Facility: Football Field; Period: " + i);
                    }
                }
                // Friday
                if (currentDate == 6) {
                    if (!(facilitySchedule[i][5].equals("x"))) {
                        endangeredBookings.add("Facility: Football Field; Period: " + i);
                    }
                }
            }
            facilitySchedule = getFacilitySchedule("Covered Courts");
            for (int i = 1; i < facilitySchedule.length; i++) {
                if (currentDate == 2) {
                    if (!(facilitySchedule[i][1].equals("x"))) {
                        endangeredBookings.add("Facility: Covered Courts; Period: " + i);
                    }
                }
                // Tuesday
                if (currentDate == 3) {
                    if (!(facilitySchedule[i][2].equals("x"))) {
                        endangeredBookings.add("Facility: Covered Courts; Period: " + i);
                    }
                }
                // Wednesday
                if (currentDate == 4) {
                    if (!(facilitySchedule[i][3].equals("x"))) {
                        endangeredBookings.add("Facility: Covered Courts; Period: " + i);
                    }
                }
                // Thursday
                if (currentDate == 5) {
                    if (!(facilitySchedule[i][4].equals("x"))) {
                        endangeredBookings.add("Facility: Covered Courts; Period: " + i);
                    }
                }
                // Friday
                if (currentDate == 6) {
                    if (!(facilitySchedule[i][5].equals("x"))) {
                        endangeredBookings.add("Facility: Covered Courts; Period: " + i);
                    }
                }
            }
            facilitySchedule = getFacilitySchedule("Tennis Court");
            for (int i = 1; i < facilitySchedule.length; i++) {
                if (currentDate == 2) {
                    if (!(facilitySchedule[i][1].equals("x"))) {
                        endangeredBookings.add("Facility: Tennis Court; Period: " + i);
                    }
                }
                // Tuesday
                if (currentDate == 3) {
                    if (!(facilitySchedule[i][2].equals("x"))) {
                        endangeredBookings.add("Facility: Tennis Court; Period: " + i);
                    }
                }
                // Wednesday
                if (currentDate == 4) {
                    if (!(facilitySchedule[i][3].equals("x"))) {
                        endangeredBookings.add("Facility: Tennis Court; Period: " + i);
                    }
                }
                // Thursday
                if (currentDate == 5) {
                    if (!(facilitySchedule[i][4].equals("x"))) {
                        endangeredBookings.add("Facility: Tennis Court; Period: " + i);
                    }
                }
                // Friday
                if (currentDate == 6) {
                    if (!(facilitySchedule[i][5].equals("x"))) {
                        endangeredBookings.add("Facility: Tennis Court; Period: " + i);
                    }
                }
            }
        }
        System.out.println(endangeredBookings);
        return endangeredBookings;
    }
}
