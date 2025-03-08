package model;

import java.io.*;
import java.util.ArrayList;
import ui.Main;

public class Facilities {
    private boolean isOutside;
    private int maxStudentCapacity;


    public Facilities(boolean isOutside, int maxStudentCapacity){
        this.isOutside=isOutside;
        this.maxStudentCapacity=maxStudentCapacity;
    }

    public void addBookingToSportsCenter(Booking booking) {
        ArrayList<String> tempArray = new ArrayList<>();
        String line;
        String[] lineArr;
            try {
                BufferedReader br = new BufferedReader(new FileReader(Main.sportCenterSheetPath));
                while((line = br.readLine())!=null){
                    lineArr=line.split(",");
                    if(lineArr[0].equals(String.valueOf(booking.getPeriod()))){
                        if(booking.getDate().equals("Monday")){
                            tempArray.add(
                                    lineArr[0] + "," +
                                            Main.currentUser.getUsername() + "," +
                                    lineArr[2] + "," +
                                    lineArr[3] + "," +
                                    lineArr[4] + "," +
                                    lineArr[5]);
                        } else if(booking.getDate().equals("Tuesday")){
                            tempArray.add(
                                    lineArr[0] + "," +
                                    lineArr[1] + "," +
                                            Main.currentUser.getUsername() + "," +
                                    lineArr[3] + "," +
                                    lineArr[4] + "," +
                                    lineArr[5]);
                        } else if(booking.getDate().equals("Wednesday")){
                            tempArray.add(
                                    lineArr[0] + "," +
                                            lineArr[1] + "," +
                                            lineArr[2] + "," +
                                            Main.currentUser.getUsername() + "," +
                                            lineArr[4] + "," +
                                            lineArr[5]);
                        }else if(booking.getDate().equals("Thursday")){
                            tempArray.add(
                                    lineArr[0] + "," +
                                            lineArr[1] + "," +
                                            lineArr[2] + "," +
                                            lineArr[3] + "," +
                                            Main.currentUser.getUsername() + "," +
                                            lineArr[5]);
                        }else if(booking.getDate().equals("Friday")){
                            tempArray.add(
                                    lineArr[0] + "," +
                                            lineArr[1] + "," +
                                            lineArr[2] + "," +
                                            lineArr[3] + "," +
                                            lineArr[4] + "," +
                                            Main.currentUser.getUsername());
                        }

                    } else{
                        tempArray.add(line);
                    }

                }
                br.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try{
                try(PrintWriter pr = new PrintWriter(Main.sportCenterSheetPath)){
                    for(String str : tempArray){
                        pr.println(str);
                    }
                } catch (Exception e) {
                }
            } catch (Exception e){

            }


    }
    public void addBookingToCoveredCourts(Booking booking){
        ArrayList<String> tempArray = new ArrayList<>();
        String line;
        String[] lineArr;


            try {
                BufferedReader br = new BufferedReader(new FileReader(Main.coveredCourtsSheetPath));
                while((line = br.readLine())!=null){
                    lineArr=line.split(",");
                    if(lineArr[0].equals(String.valueOf(booking.getPeriod()))){
                        if(booking.getDate().equals("Monday")){
                            tempArray.add(
                                    lineArr[0] + "," +
                                            Main.currentUser.getUsername() + "," +
                                            lineArr[2] + "," +
                                            lineArr[3] + "," +
                                            lineArr[4] + "," +
                                            lineArr[5]);
                        } else if(booking.getDate().equals("Tuesday")){
                            tempArray.add(
                                    lineArr[0] + "," +
                                            lineArr[1] + "," +
                                            Main.currentUser.getUsername() + "," +
                                            lineArr[3] + "," +
                                            lineArr[4] + "," +
                                            lineArr[5]);
                        } else if(booking.getDate().equals("Wednesday")){
                            tempArray.add(
                                    lineArr[0] + "," +
                                            lineArr[1] + "," +
                                            lineArr[2] + "," +
                                            Main.currentUser.getUsername() + "," +
                                            lineArr[4] + "," +
                                            lineArr[5]);
                        }else if(booking.getDate().equals("Thursday")){
                            tempArray.add(
                                    lineArr[0] + "," +
                                            lineArr[1] + "," +
                                            lineArr[2] + "," +
                                            lineArr[3] + "," +
                                            Main.currentUser.getUsername() + "," +
                                            lineArr[5]);
                        }else if(booking.getDate().equals("Friday")){
                            tempArray.add(
                                    lineArr[0] + "," +
                                            lineArr[1] + "," +
                                            lineArr[2] + "," +
                                            lineArr[3] + "," +
                                            lineArr[4] + "," +
                                            Main.currentUser.getUsername());
                        }

                    } else{
                        tempArray.add(line);
                    }

                }
                br.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try{
                try(PrintWriter pr = new PrintWriter(Main.coveredCourtsSheetPath)){
                    for(String str : tempArray){
                        pr.println(str);
                    }
                } catch (Exception e) {
                }
            } catch (Exception e){

            }



    }
    public void addBookingToFootballField(Booking booking){
        ArrayList<String> tempArray = new ArrayList<>();
        String line;
        String[] lineArr;


            try {
                BufferedReader br = new BufferedReader(new FileReader(Main.footballFieldSheetPath));
                while((line = br.readLine())!=null){
                    lineArr=line.split(",");
                    if(lineArr[0].equals(String.valueOf(booking.getPeriod()))){
                        if(booking.getDate().equals("Monday")){
                            tempArray.add(
                                    lineArr[0] + "," +
                                            Main.currentUser.getUsername() + "," +
                                            lineArr[2] + "," +
                                            lineArr[3] + "," +
                                            lineArr[4] + "," +
                                            lineArr[5]);
                        } else if(booking.getDate().equals("Tuesday")){
                            tempArray.add(
                                    lineArr[0] + "," +
                                            lineArr[1] + "," +
                                            Main.currentUser.getUsername() + "," +
                                            lineArr[3] + "," +
                                            lineArr[4] + "," +
                                            lineArr[5]);
                        } else if(booking.getDate().equals("Wednesday")){
                            tempArray.add(
                                    lineArr[0] + "," +
                                            lineArr[1] + "," +
                                            lineArr[2] + "," +
                                            Main.currentUser.getUsername() + "," +
                                            lineArr[4] + "," +
                                            lineArr[5]);
                        }else if(booking.getDate().equals("Thursday")){
                            tempArray.add(
                                    lineArr[0] + "," +
                                            lineArr[1] + "," +
                                            lineArr[2] + "," +
                                            lineArr[3] + "," +
                                            Main.currentUser.getUsername() + "," +
                                            lineArr[5]);
                        }else if(booking.getDate().equals("Friday")){
                            tempArray.add(
                                    lineArr[0] + "," +
                                            lineArr[1] + "," +
                                            lineArr[2] + "," +
                                            lineArr[3] + "," +
                                            lineArr[4] + "," +
                                            Main.currentUser.getUsername());
                        }

                    } else{
                        tempArray.add(line);
                    }

                }
                br.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try{
                try(PrintWriter pr = new PrintWriter(Main.footballFieldSheetPath)){
                    for(String str : tempArray){
                        pr.println(str);
                    }
                } catch (Exception e) {
                }
            } catch (Exception e){

            }



    }
    public void addBookingToTennisCourt(Booking booking){
        ArrayList<String> tempArray = new ArrayList<>();
        String line;
        String[] lineArr;


            try {
                BufferedReader br = new BufferedReader(new FileReader(Main.tennisCourtSheetPath));
                while((line = br.readLine())!=null){
                    lineArr=line.split(",");
                    if(lineArr[0].equals(String.valueOf(booking.getPeriod()))){
                        if(booking.getDate().equals("Monday")){
                            tempArray.add(
                                    lineArr[0] + "," +
                                            Main.currentUser.getUsername() + "," +
                                            lineArr[2] + "," +
                                            lineArr[3] + "," +
                                            lineArr[4] + "," +
                                            lineArr[5]);
                        } else if(booking.getDate().equals("Tuesday")){
                            tempArray.add(
                                    lineArr[0] + "," +
                                            lineArr[1] + "," +
                                            Main.currentUser.getUsername() + "," +
                                            lineArr[3] + "," +
                                            lineArr[4] + "," +
                                            lineArr[5]);
                        } else if(booking.getDate().equals("Wednesday")){
                            tempArray.add(
                                    lineArr[0] + "," +
                                            lineArr[1] + "," +
                                            lineArr[2] + "," +
                                            Main.currentUser.getUsername() + "," +
                                            lineArr[4] + "," +
                                            lineArr[5]);
                        }else if(booking.getDate().equals("Thursday")){
                            tempArray.add(
                                    lineArr[0] + "," +
                                            lineArr[1] + "," +
                                            lineArr[2] + "," +
                                            lineArr[3] + "," +
                                            Main.currentUser.getUsername() + "," +
                                            lineArr[5]);
                        }else if(booking.getDate().equals("Friday")){
                            tempArray.add(
                                    lineArr[0] + "," +
                                            lineArr[1] + "," +
                                            lineArr[2] + "," +
                                            lineArr[3] + "," +
                                            lineArr[4] + "," +
                                            Main.currentUser.getUsername());
                        }

                    } else{
                        tempArray.add(line);
                    }

                }
                br.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try{
                try(PrintWriter pr = new PrintWriter(Main.tennisCourtSheetPath)){
                    for(String str : tempArray){
                        pr.println(str);
                    }
                } catch (Exception e) {
                }
            } catch (Exception e){

            }



    }

    public int getMaxStudentCapacity(){
        return maxStudentCapacity;
    }

}
