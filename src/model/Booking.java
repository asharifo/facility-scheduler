package model;

public class Booking {

    private final int bookingId;
    private final int userId;
    private final int facilityId;
    private final String dayOfWeek;
    private final int period;
    private final int personCount;

    public Booking(int bookingId, int userId, int facilityId,
                   String dayOfWeek, int period, int personCount) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.facilityId = facilityId;
        this.dayOfWeek = dayOfWeek;
        this.period = period;
        this.personCount = personCount;
    }

    public int getBookingId() {
        return bookingId;
    }

    public int getUserId() {
        return userId;
    }

    public int getFacilityId() {
        return facilityId;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public int getPeriod() {
        return period;
    }

    public int getPersonCount() {
        return personCount;
    }
}
