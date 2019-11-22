package ca.ubc.cs304.controller;

import ca.ubc.cs304.delegates.MainOperationsDelegate;
import ca.ubc.cs304.ui.ClerkWindow;
import ca.ubc.cs304.ui.CustomerWindow;
import ca.ubc.cs304.ui.DatabaseManipulationWindow;

import javax.swing.*;

public class MainController implements MainOperationsDelegate {
    private ClerkController clerkController = null;
    private ClerkWindow clerkWindow = null;
    private JFrame currentWindow = null;
    private CustomerController customerController = null;
    private CustomerWindow customerWindow = null;
    private DatabaseController databaseController = null;
    private DatabaseManipulationWindow databaseManipulationWindow = null;

    public MainController(JFrame currentWindow) {
        this.currentWindow = currentWindow;
    }

    @Override
    public void showCustomerWindow() {
        currentWindow.dispose();
        customerWindow = new CustomerWindow();
        customerController = new CustomerController(customerWindow);
        customerWindow.showMenu(customerController);
        this.currentWindow = customerWindow;
    }

    @Override
    public void showClerkWindow() {
        currentWindow.dispose();
        clerkWindow = new ClerkWindow();
        clerkController = new ClerkController(clerkWindow);
        clerkWindow.showMenu(clerkController);
        this.currentWindow = clerkWindow;
    }

    @Override
    public void showDatabaseWindow() {
        currentWindow.dispose();
        databaseManipulationWindow = new DatabaseManipulationWindow();
        databaseController = new DatabaseController(databaseManipulationWindow);
        databaseManipulationWindow.showMenu(databaseController);
        this.currentWindow = databaseManipulationWindow;
    }
}
