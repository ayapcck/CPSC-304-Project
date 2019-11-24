package ca.ubc.cs304.controller;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.delegates.MakeReservationDelegate;
import ca.ubc.cs304.model.Branch;
import ca.ubc.cs304.model.Reservation;
import ca.ubc.cs304.ui.CustomerWindow;

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
        dbHandler.insertReservation(reservation, branch);
    }

    @Override
    public void returnToCustomer() {
        currentWindow.dispose();
        CustomerWindow customerWindow = new CustomerWindow();
        CustomerController customerController = new CustomerController(customerWindow);
        customerWindow.showMenu(customerController);
    }
}
