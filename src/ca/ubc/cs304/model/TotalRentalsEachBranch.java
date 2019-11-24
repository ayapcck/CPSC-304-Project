package ca.ubc.cs304.model;

public class TotalRentalsEachBranch {

    private String location;
    private String city;
    private int totalRentals;
    public TotalRentalsEachBranch(String location, String city, int totalRentals) {
        this.location = location;
        this.city= city;
        this.totalRentals = totalRentals;
    }

    public int gettotalRentals() {
        return totalRentals;
    }


    public String getCity() {
        return city;
    }

    public String getLocation() {
        return location;
    }
}
