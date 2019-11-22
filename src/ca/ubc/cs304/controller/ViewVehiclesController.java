package ca.ubc.cs304.controller;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.delegates.ViewVehiclesDelegate;
import ca.ubc.cs304.ui.CustomerWindow;
import ca.ubc.cs304.ui.ViewAvailableVehicleResultWindow;

import javax.swing.*;

public class ViewVehiclesController implements ViewVehiclesDelegate {
    private DatabaseConnectionHandler dbHandler = null;
    private JFrame currentWindow = null;

    public ViewVehiclesController(JFrame currentWindow) {
        this.currentWindow = currentWindow;
        this.dbHandler = DatabaseConnectionHandler.getDBHandlerInstance();
    }

    @Override
    public void submit(String carType, String location, String city, java.sql.Date fromDate, java.sql.Date toDate) {
		currentWindow.dispose();
		int count = dbHandler.checkVehicleNum(carType, location, city, fromDate, toDate);
		ViewAvailableVehicleResultWindow viewAvailableVehicleResultWindow = new ViewAvailableVehicleResultWindow();
		ViewVehiclesResultController viewVehiclesResultController = new ViewVehiclesResultController(viewAvailableVehicleResultWindow);
		viewAvailableVehicleResultWindow.showMenu(viewVehiclesResultController, count, carType, location, city, fromDate, toDate);
    }

    @Override
    public void returnToCustomer() {
        currentWindow.dispose();
        CustomerWindow customerWindow = new CustomerWindow();
        CustomerController customerController = new CustomerController(customerWindow);
        customerWindow.showMenu(customerController);
    }
}
