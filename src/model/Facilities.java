package model;

public class Facilities {

    private final int facilityId;
    private final String name;
    private final boolean outside;
    private final int maxCapacity;

    public Facilities(int facilityId, String name, boolean outside, int maxCapacity) {
        this.facilityId = facilityId;
        this.name = name;
        this.outside = outside;
        this.maxCapacity = maxCapacity;
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

    public int getMaxCapacity() {
        return maxCapacity;
    }

    @Override
    public String toString() {
        // this is what shows in JComboBox
        return name;
    }
}
