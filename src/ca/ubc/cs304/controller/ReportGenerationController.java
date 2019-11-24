package ca.ubc.cs304.controller;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.delegates.ReportGenerationDelegate;
import ca.ubc.cs304.ui.*;

import javax.swing.*;
import java.sql.Date;

public class ReportGenerationController implements ReportGenerationDelegate {
    private DatabaseConnectionHandler dbHandler = null;
    private JFrame currentWindow = null;

    public ReportGenerationController(JFrame currentWindow) {
        this.currentWindow = currentWindow;
        dbHandler = DatabaseConnectionHandler.getDBHandlerInstance();
    }

    @Override
    public void dailyReportsRentalsWholeCompany(Date date) {
        JTable totalRentalsEachBranches = dbHandler.totalNumRentalsEachBranch(date);
        JTable rentalsByVTS = dbHandler.rentalsAtEachLocationByVT(date);
        int totalRentals = dbHandler.totalRentalsWholeCompany(date);
        currentWindow.dispose();
        DailyRentalsCompanyWindow dailyRentalsCompanyWindow = new DailyRentalsCompanyWindow();
        ReportGenerationController reportGenerationController = new ReportGenerationController(dailyRentalsCompanyWindow);
        dailyRentalsCompanyWindow.showMenu(reportGenerationController, totalRentalsEachBranches, rentalsByVTS, totalRentals);
    }

    @Override
    public void dailyRentalReportsOneBranch(Date date, String location, String city) {
        int totalRentals = dbHandler.totalRentalsOneBranch(date, location, city);
        JTable totalRentalsPerVtType = dbHandler.rentalsPerBranchVT(date, location, city);
        currentWindow.dispose();
        DailyRentalsBranchWindow dailyRentalsBranchWindow = new DailyRentalsBranchWindow();
        ReportGenerationController reportGenerationController = new ReportGenerationController(dailyRentalsBranchWindow);
        dailyRentalsBranchWindow.showMenu(reportGenerationController, totalRentals, totalRentalsPerVtType);
    }

    @Override
    public void dailyReturnReportsWholeCompany(Date date) {
        //TODO: display the reports currently in 2 tables and an int for the total reports. Can put it in one table if we need
        JTable totalReturnsByVt = dbHandler.returnForAllBranchesVT(date);
        JTable totalReturnsPerBranch = dbHandler.returnForAllBranches(date);
        int totalRevenue = dbHandler.grandTotalRevenue(date);
        currentWindow.dispose();
        DailyReturnsCompanyWindow dailyReturnsCompanyWindow = new DailyReturnsCompanyWindow();
        ReportGenerationController reportGenerationController = new ReportGenerationController(dailyReturnsCompanyWindow);
        dailyReturnsCompanyWindow.showMenu(reportGenerationController, totalRevenue, totalReturnsByVt, totalReturnsPerBranch);
    }

    @Override
    public void dailyReturnReportOneBranch(Date date, String location, String city) {
        JTable returnForOneBranch = dbHandler.returnForOneBranchVTAndRevenue(date, location, city);
        JTable returnTotalRevAndReturn = dbHandler.returnOneBranchTotalVtAndRevenue(date, location, city);
        currentWindow.dispose();
        DailyReturnsBranchWindow dailyReturnsBranchWindow = new DailyReturnsBranchWindow();
        ReportGenerationController reportGenerationController = new ReportGenerationController(dailyReturnsBranchWindow);
        dailyReturnsBranchWindow.showMenu(reportGenerationController, returnForOneBranch, returnTotalRevAndReturn);
    }

    @Override
    public void backToClerkMenu() {
        currentWindow.dispose();
        ClerkWindow clerkWindow = new ClerkWindow();
        ClerkController clerkController = new ClerkController(clerkWindow);
        clerkWindow.showMenu(clerkController);
    }
}
