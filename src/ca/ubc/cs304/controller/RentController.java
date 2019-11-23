package ca.ubc.cs304.controller;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.delegates.RentVehicleDelegate;
import ca.ubc.cs304.ui.ClerkWindow;

import javax.swing.*;

public class RentController implements RentVehicleDelegate {
    private DatabaseConnectionHandler dbHandler = null;
    private JFrame currentWindow;

    public RentController(JFrame currentWindow) {
        this.currentWindow = currentWindow;
        dbHandler = DatabaseConnectionHandler.getDBHandlerInstance();
    }

    @Override
    public void backToClerk() {
        currentWindow.dispose();
        ClerkWindow clerkWindow = new ClerkWindow();
        ClerkController clerkController = new ClerkController(clerkWindow);
        clerkWindow.showMenu(clerkController);
    }

    @Override
    public void rentWithReservation() {
        int confNo = -9999;
        dbHandler.rentVehicleWithReservation(confNo);
    }

    @Override
    public void rentWithoutReservation() {
        dbHandler.rentVehicleWithNoReservation();
    }
}
