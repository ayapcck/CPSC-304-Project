package ca.ubc.cs304.controller;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.delegates.NewCustomerDelegate;
import ca.ubc.cs304.model.Customer;
import ca.ubc.cs304.ui.ErrorWindow;
import ca.ubc.cs304.ui.MakeReservationWindow;
import ca.ubc.cs304.ui.CustomerWindow;
import ca.ubc.cs304.ui.NewCustomerWindow;

import javax.swing.*;
import java.util.ArrayList;

public class NewCustomerController implements NewCustomerDelegate {
    private DatabaseConnectionHandler dbHandler = DatabaseConnectionHandler.getDBHandlerInstance();
    private JFrame currentWindow = null;

    public NewCustomerController(JFrame currentWindow) {
        this.currentWindow = currentWindow;
    }

    @Override
    public void finishRegistration(Customer customer) {
        if (customer.getDriversLicense().equals("")) {
            currentWindow.dispose();
            ErrorWindow errorWindow = new ErrorWindow();
            errorWindow.infoBox("Driver's license cannot be empty", "Driver's license missing");
            NewCustomerWindow newCustomerWindow = new NewCustomerWindow();
            NewCustomerController newCustomerController = new NewCustomerController(newCustomerWindow);
            newCustomerWindow.showMenu(newCustomerController);
        } else {
            dbHandler.insertCustomer(customer);
            currentWindow.dispose();
            MakeReservationWindow makeReservationWindow = new MakeReservationWindow();
            MakeReservationController makeReservationController = new MakeReservationController(makeReservationWindow);
            ArrayList<String> locationList = dbHandler.findAllBranch();
            ArrayList<String> cityList = dbHandler.findAllCity();
            ArrayList<String> vtList = dbHandler.findAllVT();

            makeReservationWindow.showMenu(vtList, locationList, cityList, makeReservationController, customer.getDriversLicense());
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
