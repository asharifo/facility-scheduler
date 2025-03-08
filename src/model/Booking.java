package model;

public class Booking {

    private String facility;
    private int personCount;
    private int period;
    private String date;
    private String username;
    
    public Booking(String facility, int personCount, int period, String date, String username){
        this.facility = facility;
        this.personCount = personCount;
        this.period = period;
        this.date = date;
        this.username = username;

    }

    public String displayBooking(){
        return facility + " " + personCount + " " +  period + " " +  date;
    }

    public String getFacility() {
        return facility;
    }
    public int getPeriod() {
        return period;
    }
    public String getDate() {
        return date;
    }
    public int getPersonCount() {
        return personCount;
    }

    public String getUsername(){return username;}

}

