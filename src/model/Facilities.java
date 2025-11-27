package model;

public class Facilities {

    private final int facilityId;
    private final String name;
    private final boolean outside;
    private final int capacity;

    public Facilities(int facilityId, String name, boolean outside, int capacity) {
        this.facilityId = facilityId;
        this.name = name;
        this.outside = outside;
        this.capacity = capacity;
    }

    public int getFacilityId() {
        return facilityId;
    }

    public String getName() {
        return name;
    }

    public boolean isOutside() {
        return outside;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public String toString() {
        return name;
    }
}
