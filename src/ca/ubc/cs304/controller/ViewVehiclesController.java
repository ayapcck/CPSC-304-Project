package ca.ubc.cs304.controller;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.delegates.ViewVehiclesDelegate;
import ca.ubc.cs304.ui.CustomerWindow;
import ca.ubc.cs304.ui.ErrorWindow;
import ca.ubc.cs304.ui.ViewAvailableVehicleResultWindow;
import ca.ubc.cs304.ui.ViewAvailableVehiclesWindow;

import javax.swing.*;
import java.util.ArrayList;

public class ViewVehiclesController implements ViewVehiclesDelegate {
    private DatabaseConnectionHandler dbHandler = null;
    private JFrame currentWindow = null;

    public ViewVehiclesController(JFrame currentWindow) {
        this.currentWindow = currentWindow;
        this.dbHandler = DatabaseConnectionHandler.getDBHandlerInstance();
    }

    @Override
    public void submit(String carType, String location, String city, String fromDate, String toDate) {
		if (checkDateIsValid(fromDate, toDate)) {
            currentWindow.dispose();
            int count = dbHandler.checkVehicleNum(carType, location, city);
            ViewAvailableVehicleResultWindow viewAvailableVehicleResultWindow = new ViewAvailableVehicleResultWindow();
            ViewVehiclesResultController viewVehiclesResultController = new ViewVehiclesResultController(viewAvailableVehicleResultWindow);
            viewAvailableVehicleResultWindow.showMenu(viewVehiclesResultController, count, carType, location, city);
        } else {
//            currentWindow.dispose();
            ErrorWindow errorWindow = new ErrorWindow();
            errorWindow.infoBox("Invalid date!", "Invalid input");
            ViewAvailableVehiclesWindow viewWindow = new ViewAvailableVehiclesWindow();
            ViewAvailableVehiclesWindow viewAvailableVehiclesWindow = new ViewAvailableVehiclesWindow();
            ViewVehiclesController viewVehiclesController = new ViewVehiclesController(viewAvailableVehiclesWindow);
            ArrayList<String> branchList = dbHandler.findAllBranch();
            ArrayList<String> cityList = dbHandler.findAllCity();
            ArrayList<String> vtList = dbHandler.findAllVT();
            viewWindow.showMenu(vtList, branchList, cityList, viewVehiclesController);
        }
    }

    @Override
    public void returnToCustomer() {
        currentWindow.dispose();
        CustomerWindow customerWindow = new CustomerWindow();
        CustomerController customerController = new CustomerController(customerWindow);
        customerWindow.showMenu(customerController);
    }

    public boolean checkDateIsValid(String fromDate, String toDate) {
        if (fromDate.equals("") || (toDate.equals(""))) return false;
        int fromyear = Integer.parseInt(fromDate.split("-")[0]);
        int fromMonth = Integer.parseInt(fromDate.split("-")[1]);
        int fromDay = Integer.parseInt(fromDate.split("-")[2]);
        int toyear = Integer.parseInt(toDate.split("-")[0]);
        int toMonth = Integer.parseInt(toDate.split("-")[1]);
        int toDay = Integer.parseInt(toDate.split("-")[2]);

        if ((fromyear > toyear)) return false;
        if ((fromyear == toyear) && (fromMonth > toMonth)) return false;
        if ((fromyear == toyear) && (fromMonth == toMonth) && (fromDay > toDay)) return false;
        if (fromyear < 2019) return false;
        return true;
    }
}
