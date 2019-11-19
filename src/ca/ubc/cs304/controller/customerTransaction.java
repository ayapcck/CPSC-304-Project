package ca.ubc.cs304.controller;

import ca.ubc.cs304.model.Branch;
import ca.ubc.cs304.model.TimePeriod;
import ca.ubc.cs304.model.VehicleType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class customerTransaction {
    private Connection connection = null;

    public void checkVehicles(TimePeriod timePeriod, VehicleType vehicleType, Branch branch) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT ? FROM ForRent WHERE VTname = " + vehicleType);
            ps.executeQuery();
        } catch (SQLException e) {

        }
    }
}
