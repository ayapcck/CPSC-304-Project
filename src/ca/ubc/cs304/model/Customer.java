package ca.ubc.cs304.model;

public class Customer {
    private String cellNum;
    private String name;
    private String address;
    private String driversLicense;

    public Customer(String cellNum, String name, String address, String driversLicense) {
        this.cellNum = cellNum;
        this.address = address;
        this.driversLicense = driversLicense;
        this.name = name;
    }

    public String getCellNum() {
        return cellNum;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getDriversLicense() {
        return driversLicense;
    }
}
