package ca.ubc.cs304.controller;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.delegates.NewCustomerDelegate;
import ca.ubc.cs304.ui.MakeReservationWindow;
import ca.ubc.cs304.ui.CustomerWindow;

import javax.swing.*;

public class NewCustomerController implements NewCustomerDelegate {
    private DatabaseConnectionHandler dbHandler = DatabaseConnectionHandler.getDBHandlerInstance();
    private JFrame currentWindow = null;

    public NewCustomerController(JFrame currentWindow) {
        this.currentWindow = currentWindow;
    }

    @Override
    public void finishRegistration(String name, String phone,
                                   String license, String address) {
        dbHandler.insertCustomer(name, phone, license, address);
        currentWindow.dispose();
        MakeReservationWindow makeReservationWindow = new MakeReservationWindow();
        MakeReservationController makeReservationController = new MakeReservationController(makeReservationWindow);
        makeReservationWindow.showMenu(makeReservationController, license);
    }

    @Override
    public void returnToCustomer() {
        currentWindow.dispose();
        CustomerWindow customerWindow = new CustomerWindow();
        CustomerController customerController = new CustomerController(customerWindow);
        customerWindow.showMenu(customerController);
    }
}
