package ca.ubc.cs304.controller;

import ca.ubc.cs304.delegates.BeginReservationDelegate;
import ca.ubc.cs304.delegates.MakeReservationDelegate;
import ca.ubc.cs304.ui.MakeReservationWindow;
import ca.ubc.cs304.ui.NewCustomerWindow;

import javax.swing.*;

public class BeginReservationController implements BeginReservationDelegate {
    private JFrame currentWindow = null;

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
    public void existingReservation() {
        currentWindow.dispose();
        MakeReservationWindow reservationWindow = new MakeReservationWindow();
        MakeReservationController makeReservationController = new MakeReservationController(reservationWindow);
        reservationWindow.showExistingCusMenu(makeReservationController);
    }
}
