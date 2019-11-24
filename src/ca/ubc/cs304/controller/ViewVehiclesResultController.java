package ca.ubc.cs304.controller;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.delegates.ViewVehiclesDelegate;
import ca.ubc.cs304.delegates.ViewVehiclesResultDelegate;
import ca.ubc.cs304.ui.CustomerWindow;
import ca.ubc.cs304.ui.ErrorWindow;
import ca.ubc.cs304.ui.ViewAvailableVehicleResultWindow;
import ca.ubc.cs304.ui.ViewAvailableVehiclesWindow;

import javax.swing.*;
import java.sql.Date;

public class ViewVehiclesResultController implements ViewVehiclesResultDelegate {
    private DatabaseConnectionHandler dbHandler = null;
    private JFrame currentWindow = null;

    public ViewVehiclesResultController(JFrame currentWindow) {
        this.currentWindow = currentWindow;
        this.dbHandler = DatabaseConnectionHandler.getDBHandlerInstance();
    }

    @Override
    public void showDetailCountResult(int count, String carType, String location, String city) {
        currentWindow.dispose();
        if (count == 0) {
			ErrorWindow.infoBox("No cars to be shown!", "no info");
		} else {
            JTable resultTable = dbHandler.showVehicleDetails(carType, location, city);
			ViewAvailableVehicleResultWindow viewAvailableVehicleResultWindow = new ViewAvailableVehicleResultWindow();
			ViewVehiclesResultController viewVehiclesResultController = new ViewVehiclesResultController(viewAvailableVehicleResultWindow);
			viewAvailableVehicleResultWindow.showMoreDetail(viewVehiclesResultController, resultTable);
		}
    }

    @Override
    public void backToCustomer() {
        currentWindow.dispose();
        CustomerWindow customerWindow = new CustomerWindow();
        CustomerController customerController = new CustomerController(customerWindow);
        customerWindow.showMenu(customerController);
    }

    @Override
    public void backToView() {
        // TODO car
        currentWindow.dispose();
        ViewAvailableVehiclesWindow viewWindow = new ViewAvailableVehiclesWindow();
        ViewAvailableVehiclesWindow viewAvailableVehiclesWindow = new ViewAvailableVehiclesWindow();
        ViewVehiclesController viewVehiclesController = new ViewVehiclesController(viewAvailableVehiclesWindow);
        viewWindow.showMenu(viewVehiclesController);

    }
}
