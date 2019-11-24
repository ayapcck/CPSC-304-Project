package ca.ubc.cs304.controller;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.delegates.ClerkTransactionDelegate;
import ca.ubc.cs304.ui.MainOperations;
import ca.ubc.cs304.ui.RentVehicleWindow;

import javax.swing.*;
import java.sql.Date;

public class ClerkController implements ClerkTransactionDelegate {
    private DatabaseConnectionHandler dbHandler = null;
    private JFrame currentWindow = null;

    public ClerkController(JFrame currentWindow) {
        this.currentWindow = currentWindow;
        dbHandler = DatabaseConnectionHandler.getDBHandlerInstance();
    }

	@Override
	public void navToRentalWindow() {
		currentWindow.dispose();
        RentVehicleWindow rentVehicleWindow = new RentVehicleWindow();
        RentController rentController = new RentController(rentVehicleWindow);
        rentVehicleWindow.showMenu(rentController);
	}

	@Override
	public void returnVehicle() {
		System.out.println(dbHandler.returnVehicle()); // prints error returned from returnVehicle
	}

    @Override
    public void mainMenu() {
        currentWindow.dispose();
        MainOperations mainOperations = new MainOperations();
        MainController mainController = new MainController(mainOperations);
        mainOperations.showMenu(mainController);
    }

    @Override
    public void dailyReportsRentalsWholeCompany() {
        //TODO: display the reports currently in 2 tables and an int for the total reports. Can put it in one table if we need
        // TODO: add date button so the user can select date (like in view available vehicles)

        long date1 = new java.util.Date().getTime();
        Date date = new Date(date1); // dummy variable

        JTable totalRentalsEachBranches = dbHandler.totalNumRentalsEachBranch(date);
        JTable rentalsByVTS = dbHandler.rentalsAtEachLocationByVT(date);
        int totalRentals = dbHandler.totalRentalsWholeCompany(date);
    }

    @Override
    public void dailyRentalReportsSingleBranch() {
        //TODO: display the reports currently in 1 tables and an int for the total reports. Can put it in one table if we need
        // TODO: add date button, city text box and location text box

        long date1 = new java.util.Date().getTime();
        Date date = new Date(date1); // dummy variable
        String location = "bc";
        String city = "vancouver";

        int totalRentals = dbHandler.totalRentalsOneBranch(date, location, city);
        JTable totalRentalsPerVtType = dbHandler.rentalsPerBranchVT(date, location, city);
    }

    @Override
    public void dailyReturnReportsWholeCompany() {
        //TODO: display the reports currently in 2 tables and an int for the total reports. Can put it in one table if we need
        // TODO: add date button so the user can select date (like in view available vehicles)

        long date1 = new java.util.Date().getTime();
        Date date = new Date(date1); // dummy variable

        JTable totalReturnsByVt = dbHandler.returnForAllBranchesVT(date);
        JTable totalReturnsPerBranch = dbHandler.returnForAllBranches(date);
        int totalRevenue = dbHandler.grandTotalRevenue(date);
    }

    @Override
    public void dailyReturnReportOneCompany() {
        // TODO: display the tables
        // TODO: add buttons so use can select date, location and city
        long date1 = new java.util.Date().getTime();
        Date date = new Date(date1); // dummy variable
        String location = "bc";
        String city = "vancouver";

        JTable returnForOneBranch = dbHandler.returnForOneBranchVTAndRevenue(date, location, city);
        JTable returnTotalRevAndReturn = dbHandler.returnOneBranchTotalVtAndRevenue(date, location, city);

    }

}
