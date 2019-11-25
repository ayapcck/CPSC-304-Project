package ca.ubc.cs304.controller;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.delegates.BeginReservationDelegate;
import ca.ubc.cs304.ui.MakeReservationWindow;
import ca.ubc.cs304.ui.NewCustomerWindow;

import javax.swing.*;
import java.util.ArrayList;

public class BeginReservationController implements BeginReservationDelegate {
    private JFrame currentWindow = null;
    private DatabaseConnectionHandler dbHandler = DatabaseConnectionHandler.getDBHandlerInstance();


    public BeginReservationController(JFrame currentWindow) {
        this.currentWindow = currentWindow;
    }

    @Override
    public void newCustomerReservation() {
        currentWindow.dispose();
        NewCustomerWindow newCustomerWindow = new NewCustomerWindow();
        NewCustomerController newCustomerController = new NewCustomerController(newCustomerWindow);
        newCustomerWindow.showMenu(newCustomerController);
    }

    @Override
    public void existingCustomerReservation() {
        currentWindow.dispose();
        MakeReservationWindow reservationWindow = new MakeReservationWindow();
        MakeReservationController makeReservationController = new MakeReservationController(reservationWindow);
        ArrayList<String> branchList = dbHandler.findAllBranch();
        ArrayList<String> cityList = dbHandler.findAllCity();
        ArrayList<String> vtList = dbHandler.findAllVT();
        reservationWindow.showMenu(vtList, branchList, cityList, makeReservationController, null);
    }
}
