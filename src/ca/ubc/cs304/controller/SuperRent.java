package ca.ubc.cs304.controller;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.delegates.*;
import ca.ubc.cs304.ui.*;

import javax.swing.*;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Time;

/**
 * This is the main controller class that will orchestrate everything.
 */
public class SuperRent implements LoginWindowDelegate {

	private DatabaseConnectionHandler dbHandler = null;
	private LoginWindow loginWindow = null;
	private MainOperations mainOperations = null;

	private MainController mainController = null;

	public SuperRent() {
		dbHandler = DatabaseConnectionHandler.getDBHandlerInstance();
	}
	
	private void start() {
		loginWindow = new LoginWindow();
		loginWindow.showFrame(this);
	}
	
	/**
	 * LoginWindowDelegate Implementation
	 * 
     * connects to Oracle database with supplied username and password
     */
	@Override
	public void login(String username, String password) {
		boolean didConnect = dbHandler.login(username, password);

		if (didConnect) {
			// Once connected, remove login window and start text transaction flow
			loginWindow.dispose();

			mainOperations = new MainOperations();
			mainController = new MainController(mainOperations);
			mainOperations.showMenu(mainController);
		} else {
			loginWindow.handleLoginFailed();

			if (loginWindow.hasReachedMaxLoginAttempts()) {
				loginWindow.dispose();
				System.out.println("You have exceeded your number of allowed attempts");
				System.exit(-1);
			}
		}
	}

//	@Override
//	public void rentAVehicle(TerminalTransactions terminalTransactions) {
//		int confNo = -9999;
//		System.out.println("Enter confirmation number");
//		confNo = terminalTransactions.readInteger(false);
//		dbHandler.rentVehicleWithReservation(terminalTransactions, confNo);
//	}
//
//	@Override
//	public void rentAVehicleNoRes(TerminalTransactions terminalTransactions) {
////		dbHandler.rentVehicleWithNoReservation(terminalTransactions);
//	}
//
//	@Override
//	public void returnVehicle(TerminalTransactions terminalTransactions) {
//		System.out.println(dbHandler.returnVehicle(terminalTransactions)); // prints error returned from returnVehicle
//	}

	/**
	 * TerminalTransactionsDelegate Implementation
	 * 
     * The TerminalTransaction instance tells us that it is done with what it's 
     * doing so we are cleaning up the connection since it's no longer needed.
     */ 
    public void terminalTransactionsFinished() {
    	dbHandler.close();
    	dbHandler = null;
    	
    	System.exit(0);
    }


    /**
	 * Main method called at launch time
	 */
	public static void main(String args[]) {
		SuperRent superRent = new SuperRent();
		superRent.start();
	}

//    @Override
//	public void submitView() {
//		customerWindow.dispose();
//		customerViewWindow = new CustomerViewWindow();
//		customerViewWindow.showMenu(this);
//	}
//
//
//	@Override
//	public void processView(String carType, String location, String city, java.sql.Date fromDate, java.sql.Date toDate) {
//		customerViewWindow.dispose();
//		int count = dbHandler.checkVehicleNum(carType, location, city, fromDate, toDate);
//		customerViewResultWindow = new CustomerViewResultWindow();
//		customerViewResultWindow.showMenu(this, count, carType, location, city, fromDate, toDate);
//	}
//
//    @Override
//    public void backToPrevious() {
//        customerViewResultWindow.dispose();
//        customerWindow.showMenu(this);
//    }
//
//    @Override
//	public void showDetailCountResult(int count, String carType, String location, String city, java.sql.Date fromDate, java.sql.Date toDate) {
//		if (count == 0) {
//			customerViewResultWindow.dispose();
////			JOptionPane.showMessageDialog(null, "There is no cars to be shown!", "Error: " + "no such info", JOptionPane.INFORMATION_MESSAGE);
//			ErrorWindow.infoBox("No cars to be shown!", "no info");
//		} else {
//			JTable resultTable = dbHandler.showVehicleDetails(carType, location, city, fromDate, toDate);
//			customerViewResultWindow.showMoreDetail(resultTable);
//		}
//	}

}
