package ca.ubc.cs304.controller;

import ca.ubc.cs304.delegates.MainOperationsDelegate;
import ca.ubc.cs304.ui.ClerkWindow;
import ca.ubc.cs304.ui.CustomerWindow;
import ca.ubc.cs304.ui.DatabaseManipulationWindow;

import javax.swing.*;

public class MainController implements MainOperationsDelegate {
    private JFrame currentWindow = null;

    public MainController(JFrame currentWindow) {
        this.currentWindow = currentWindow;
    }

    @Override
    public void showCustomerWindow() {
        currentWindow.dispose();
        CustomerWindow customerWindow = new CustomerWindow();
        CustomerController customerController = new CustomerController(customerWindow);
        customerWindow.showMenu(customerController);
        this.currentWindow = customerWindow;
    }

    @Override
    public void showClerkWindow() {
        currentWindow.dispose();
        ClerkWindow clerkWindow = new ClerkWindow();
        ClerkController clerkController = new ClerkController(clerkWindow);
        clerkWindow.showMenu(clerkController);
        this.currentWindow = clerkWindow;
    }

    @Override
    public void showDatabaseWindow() {
        currentWindow.dispose();
        DatabaseManipulationWindow databaseManipulationWindow = new DatabaseManipulationWindow();
        DatabaseMenuController databaseMenuController = new DatabaseMenuController(databaseManipulationWindow);
        databaseManipulationWindow.showMenu(databaseMenuController);
        this.currentWindow = databaseManipulationWindow;
    }
}
