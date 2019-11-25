package ca.ubc.cs304.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ForRent {
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

    public ForRent(Integer vid, String vLicense, String make, String model, Integer year, String color, Integer odometer
    , String status, String vtName, String location, String city) {
        this.vid = vid;
        this.vLicense = vLicense;
        this.make = make;
        this.model = model;
        this.year = year;
        this.color = color;
        this.odometer = odometer;
        this.status = status; // can be available or notavaiable
        this.vtName = vtName;
        this.location = location;
        this.city = city;
    }

    public static List<ForRent> createForRentModel(ResultSet resultSet){
        int vId = -99;
        int year = -99;
        int odometer = -99;
        List<ForRent> vehicles = new ArrayList<>();
        try {
            while (resultSet.next()) {
                vId = resultSet.getInt(1);
                String vLicense = resultSet.getString(2);
                String make = resultSet.getString(3);
                String model = resultSet.getString(4);
                year = resultSet.getInt(5);
                String color = resultSet.getString(6);
                odometer = resultSet.getInt(7);
                String status = resultSet.getString(8);
                String vtName = resultSet.getString(9);
                String location = resultSet.getString(10);
                String city = resultSet.getString(11);
                ForRent forRent = new ForRent(vId, vLicense, make, model, year, color,
                        odometer, status, vtName, location, city);
                vehicles.add(forRent);
            }
            return vehicles;
        } catch (SQLException e) {
            System.out.println("Exception when parsing for rent vehicle from database\n" + e.getMessage());
            return null;
        }
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
