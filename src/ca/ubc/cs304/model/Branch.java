package ca.ubc.cs304.model;

public class Branch {
    private String location;
    private String city;

    public Branch(String location, String city) {
        this.location = location;
        this.city = city;
    }

    public String getLocation() {
        return location;
    }

    public String getCity() {
        return city;
    }
}
