package ca.ubc.cs304.controller;

import ca.ubc.cs304.model.Branch;
import ca.ubc.cs304.model.TimePeriod;
import ca.ubc.cs304.model.VehicleType;

import java.sql.*;

public class customerTransaction {
    private Connection connection = null;

    public void checkVehicles(TimePeriod timePeriod, VehicleType vehicleType, Branch branch) {
        try {

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT ? FROM ForRent WHERE VTname = " + vehicleType.getVtName() + "");

        } catch (SQLException e) {

        }
    }
}
