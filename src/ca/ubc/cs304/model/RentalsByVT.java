package ca.ubc.cs304.model;

public class RentalsByVT {
    private String location;
    private String city;
    private String vtName;
    private int numVehicles;
    public RentalsByVT(String location, String city, String vtName, int numVehicles) {
        this.location = location;
        this.city= city;
        this.vtName = vtName;
        this.numVehicles = numVehicles;
    }

    public int getNumVehicles() {
        return numVehicles;
    }

    public String getVtName() {
        return vtName;
    }

    public String getCity() {
        return city;
    }

    public String getLocation() {
        return location;
    }

}
