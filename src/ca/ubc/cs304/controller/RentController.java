package ca.ubc.cs304.controller;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.delegates.RentVehicleDelegate;
import ca.ubc.cs304.ui.ClerkWindow;
import ca.ubc.cs304.ui.RentalWithReservationWindow;

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
    public void navToRentalWithReservation() {
        currentWindow.dispose();
        RentalWithReservationWindow rentalWithReservationWindow = new RentalWithReservationWindow();
        RentController rentController = new RentController(rentalWithReservationWindow);
        rentalWithReservationWindow.showMenu(rentController);
    }

    @Override
    public void navToRentalNoReservation() {

    }

    @Override
    public void rentWithReservation(int confirmationNumber, String cardName, int cardNumber) {
        dbHandler.rentVehicleWithReservation(confirmationNumber, cardName, cardNumber);
    }

    @Override
    public void rentWithoutReservation() {
        dbHandler.rentVehicleWithNoReservation();
    }
}
