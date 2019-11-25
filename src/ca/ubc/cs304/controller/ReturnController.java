package ca.ubc.cs304.controller;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.delegates.ReturnVehicleDelegate;
import ca.ubc.cs304.model.RentalCost;
import ca.ubc.cs304.model.Return;
import ca.ubc.cs304.ui.ClerkWindow;
import ca.ubc.cs304.ui.ReturnReceiptWindow;
import javafx.util.Pair;

import javax.swing.*;

public class ReturnController implements ReturnVehicleDelegate {
    private DatabaseConnectionHandler dbHandler = null;
    private JFrame currentWindow = null;

    public ReturnController(JFrame currentWindow) {
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
    public void returnVehicle(int returnId) {
        Pair<Return, RentalCost> returnPair = dbHandler.returnVehicle(returnId);
        currentWindow.dispose();
        ReturnReceiptWindow returnReceiptWindow = new ReturnReceiptWindow();
        ReturnController returnController = new ReturnController(returnReceiptWindow);
        returnReceiptWindow.showMenu(returnController, returnPair);
    }
}
