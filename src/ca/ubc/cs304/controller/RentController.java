package ca.ubc.cs304.controller;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.delegates.RentVehicleDelegate;
import ca.ubc.cs304.model.Branch;
import ca.ubc.cs304.model.Reservation;
import ca.ubc.cs304.ui.ClerkWindow;
import ca.ubc.cs304.ui.RentalWithReservationWindow;
import ca.ubc.cs304.ui.RentalWithoutReservationWindow;

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
        currentWindow.dispose();
        RentalWithoutReservationWindow rentalWithoutReservationWindow = new RentalWithoutReservationWindow();
        RentController rentController = new RentController(rentalWithoutReservationWindow);
        rentalWithoutReservationWindow.showMenu(rentController);
    }

    @Override
    public void rentWithReservation(int confirmationNumber, String cardName, int cardNumber) {
        dbHandler.rentVehicleWithReservation(confirmationNumber, cardName, cardNumber);
    }

    @Override
    public void rentWithoutReservation(Reservation reservation, Branch branch, String cardName, int cardNumber) {
        dbHandler.rentVehicleWithNoReservation(reservation, branch, cardName, cardNumber);
    }
}
