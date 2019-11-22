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
public class SuperRent implements ProcessViewDelegate, CusEnterViewDelegate, LoginWindowDelegate, TerminalTransactionsDelegate, CustomerTransactionDelegate {
	private DatabaseConnectionHandler dbHandler = null;
	private LoginWindow loginWindow = null;
	private TransactionWindow transaction = null;
	private CustomerWindow customerWindow = null;
	private CustomerViewWindow customerViewWindow = null;
	private CustomerViewResultWindow customerViewResultWindow = null;
	private CustomerReserveWindow customerReserveWindow = null;
	private NewCusRegWindow newCusRegWindow = null;
	private ActualReserveWindow actualReserveWindow = null;
	private DatabaseManipulationWindow databaseManipulationWindow = null;

	public SuperRent() {
		dbHandler = new DatabaseConnectionHandler();
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
	public void login(String username, String password) {
		boolean didConnect = dbHandler.login(username, password);

		if (didConnect) {
			// Once connected, remove login window and start text transaction flow
			loginWindow.dispose();

			transaction = new TransactionWindow();
			transaction.showMenu(this);
		} else {
			loginWindow.handleLoginFailed();

			if (loginWindow.hasReachedMaxLoginAttempts()) {
				loginWindow.dispose();
				System.out.println("You have exceeded your number of allowed attempts");
				System.exit(-1);
			}
		}
	}

	public void addRequiredTablesAndData() {
		dbHandler.addRequiredTablesAndData();
	}

	public void dropRequiredTables() {
		dbHandler.dropAllRequiredTables();
	}

	@Override
	public void setupDatabase() {
	    // TODO
    	dbHandler.dropAllRequiredTables();
		dbHandler.addRequiredTablesAndData();


	}

	@Override
	public void viewAllTables() {
		String[] tables = dbHandler.getAllTables();
		for (String table : tables) {
			System.out.println(table);
		}
	}

	@Override
	public void rentAVehicle(TerminalTransactions terminalTransactions) {
		int confNo = -9999;
		System.out.println("Enter confirmation number");
		confNo = terminalTransactions.readInteger(false);
		dbHandler.rentVehicleWithReservation(terminalTransactions, confNo);
	}

	@Override
	public void rentAVehicleNoRes(TerminalTransactions terminalTransactions) {
//		dbHandler.rentVehicleWithNoReservation(terminalTransactions);
	}

	@Override
	public void returnVehicle(TerminalTransactions terminalTransactions) {
		System.out.println(dbHandler.returnVehicle(terminalTransactions)); // prints error returned from returnVehicle
	}

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

	@Override
	public void customerTransaction() {
		transaction.dispose();
		customerWindow = new CustomerWindow();
		customerWindow.showMenu(this);
	}

	@Override
	public void clerkTransaction() {
		transaction.dispose();
		customerWindow = new CustomerWindow();
		customerWindow.showMenu(this);
	}

    @Override
    public void databaseManipulation() {
        transaction.dispose();
        databaseManipulationWindow = new DatabaseManipulationWindow();
        databaseManipulationWindow.showMenu(this);
    }

    @Override
	public void submitView() {
		customerWindow.dispose();
		customerViewWindow = new CustomerViewWindow();
		customerViewWindow.showMenu(this);
	}

    @Override
    public void back() {
        customerWindow.dispose();
        transaction.showMenu(this);
    }

	@Override
	public void reserve() {
		customerWindow.dispose();
		customerReserveWindow = new CustomerReserveWindow();
		customerReserveWindow.showMenu(this);
	}


	@Override
	public void processView(String carType, String location, String city, java.sql.Date fromDate, java.sql.Date toDate) {
		customerViewWindow.dispose();
		int count = dbHandler.checkVehicleNum(carType, location, city, fromDate, toDate);
		customerViewResultWindow = new CustomerViewResultWindow();
		customerViewResultWindow.showMenu(this, count, carType, location, city, fromDate, toDate);
	}

    @Override
    public void backToPrevious() {
        customerViewResultWindow.dispose();
        customerWindow.showMenu(this);
    }

    @Override
	public void showDetailCountResult(int count, String carType, String location, String city, java.sql.Date fromDate, java.sql.Date toDate) {
		if (count == 0) {
			customerViewResultWindow.dispose();
//			JOptionPane.showMessageDialog(null, "There is no cars to be shown!", "Error: " + "no such info", JOptionPane.INFORMATION_MESSAGE);
			ErrorWindow.infoBox("No cars to be shown!", "no info");
		} else {
			JTable resultTable = dbHandler.showVehicleDetails(carType, location, city, fromDate, toDate);
			customerViewResultWindow.showMoreDetail(resultTable);
		}
	}

    @Override
    public void backToMain() {
        databaseManipulationWindow.dispose();
        transaction.showMenu(this);
    }

	@Override
	public void newCusReserve() {
		customerReserveWindow.dispose();
		newCusRegWindow = new NewCusRegWindow();
		newCusRegWindow.showMenu(this);
	}

	@Override
	public void newCusRigDone(String name, String phone, String licence, String addr) {
		dbHandler.insertCustomer(name, phone, licence, addr);
		newCusRegWindow.dispose();
		actualReserveWindow = new ActualReserveWindow();
		actualReserveWindow.showMenu(this, licence);
	}


	@Override
	public void oldCusReserve() {

	}

	@Override
	public void backToNewCus() {
		actualReserveWindow.dispose();;
		customerWindow.showMenu(this);
	}

	@Override
	public void backToCus() {
		newCusRegWindow.dispose();
		customerWindow.showMenu(this);
	}

	@Override
	public void makeActualReserve(String license, String location, String city, String vtname, String fromDate, String fromTime, String toDate, String toTime, int ReservationNum) {
		dbHandler.insertReservation(license, location, city, vtname, fromDate, fromTime, toDate, toTime, ReservationNum);
	}


}
