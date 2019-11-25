package ca.ubc.cs304.controller;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.delegates.CustomerTransactionDelegate;
import ca.ubc.cs304.ui.BeginReservationWindow;
import ca.ubc.cs304.ui.MainOperations;
import ca.ubc.cs304.ui.ViewAvailableVehiclesWindow;

import javax.swing.*;
import java.util.ArrayList;

public class CustomerController implements CustomerTransactionDelegate {
        private JFrame currentWindow = null;
        private DatabaseConnectionHandler dbHandler = DatabaseConnectionHandler.getDBHandlerInstance();

    public CustomerController(JFrame currentWindow) {
        this.currentWindow = currentWindow;
    }

    @Override
    public void mainMenu() {
        currentWindow.dispose();
        MainOperations mainOperations = new MainOperations();
        MainController mainController = new MainController(mainOperations);
        mainOperations.showMenu(mainController);
    }

    @Override
    public void makeReservation() {
        currentWindow.dispose();
        BeginReservationWindow beginReservationWindow = new BeginReservationWindow();
        BeginReservationController beginReservationController = new BeginReservationController(beginReservationWindow);
        beginReservationWindow.showMenu(beginReservationController);
    }

    @Override
    public void viewVehicles() {
        currentWindow.dispose();
        ViewAvailableVehiclesWindow viewAvailableVehiclesWindow = new ViewAvailableVehiclesWindow();
        ViewVehiclesController viewVehiclesController = new ViewVehiclesController(viewAvailableVehiclesWindow);
        ArrayList<String> branchList = dbHandler.findAllBranch();
        ArrayList<String> cityList = dbHandler.findAllCity();
        ArrayList<String> vtList = dbHandler.findAllVT();

        viewAvailableVehiclesWindow.showMenu(vtList, branchList, cityList, viewVehiclesController);
    }
}
