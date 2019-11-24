package ca.ubc.cs304.controller;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.delegates.MakeReservationDelegate;
import ca.ubc.cs304.model.Branch;
import ca.ubc.cs304.model.Reservation;
import ca.ubc.cs304.ui.CustomerWindow;
import ca.ubc.cs304.ui.ErrorWindow;
import ca.ubc.cs304.ui.MakeReservationWindow;

import javax.swing.*;

public class MakeReservationController implements MakeReservationDelegate {
    private DatabaseConnectionHandler dbHandler = null;
    private JFrame currentWindow = null;

    public MakeReservationController(JFrame currentWindow) {
        this.currentWindow = currentWindow;
        this.dbHandler = DatabaseConnectionHandler.getDBHandlerInstance();
    }

    @Override
    public void createReservation(Reservation reservation, Branch branch) {
        if (dbHandler.insertReservation(reservation, branch)) {
            currentWindow.dispose();
            MakeReservationWindow reservationConfirmWindow = new MakeReservationWindow();
            MakeReservationController customerController = new MakeReservationController(reservationConfirmWindow);
            reservationConfirmWindow.showConfirm(customerController, reservation, branch);
        } else {
            currentWindow.dispose();
            ErrorWindow errorWindow = new ErrorWindow();
            errorWindow.infoBox("Sorry, no vehicle matches your search", "No Available Vehicle");
        }
    }

    @Override
    public void returnToCustomer() {
        currentWindow.dispose();
        CustomerWindow customerWindow = new CustomerWindow();
        CustomerController customerController = new CustomerController(customerWindow);
        customerWindow.showMenu(customerController);
    }
}
