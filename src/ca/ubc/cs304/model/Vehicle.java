package ca.ubc.cs304.model;

import java.sql.ResultSet;

public class Vehicle {
    private Integer vid;
    private String vLicense;
    private String make;
    private String model;
    private Integer year;
    private String color;
    private Integer odometer;
    private String status;
    private String vtName;
    private String location;
    private String city;

    public Vehicle(Integer vid, String vLicense, String make, String model, Integer year, String color, Integer odometer
    , String status, String vtName, String location, String city) {
        // TODO: enforce that vid cannot be null
        this.vid = vid;
        this.vLicense = vLicense;
        this.make = make;
        this.model = model;
        this.year = year;
        this.color = color;
        this.odometer = odometer;
        this.status = status;
        this.vtName = vtName;
        this.location = location;
        this.city = city;
    }

    public static Vehicle parseVehicleFromResultSet(ResultSet resultSet) {
        // TODO: finish
        return null;
    }

    public Integer getVid() {
        return vid;
    }

    public String getvLicense() {
        return vLicense;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public Integer getYear() {
        return year;
    }

    public String getColor() {
        return color;
    }

    public Integer getOdometer() {
        return odometer;
    }

    public String getStatus() {
        return status;
    }

    public String getVtName() {
        return vtName;
    }

    public String getLocation() {
        return location;
    }

    public String getCity() {
        return city;
    }
}
