package ca.ubc.cs304.controller;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.delegates.*;
import ca.ubc.cs304.ui.*;

import javax.swing.*;
import java.sql.Date;
import java.sql.ResultSet;

/**
 * This is the main controller class that will orchestrate everything.
 */
public class SuperRent implements ProcessViewDelegate, CusEnterViewDelegate, LoginWindowDelegate, TerminalTransactionsDelegate, CustomerTransactionDelegate {
	private DatabaseConnectionHandler dbHandler = null;
	private LoginWindow loginWindow = null;
	private MainOperations mainOperations = null;
	private CustomerWindow customerWindow = null;
	private CustomerViewWindow customerViewWindow = null;
	private CustomerViewResultWindow customerViewResultWindow = null;
	private DatabaseConnectionHandler handler = null;
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

			mainOperations = new MainOperations();
			mainOperations.showMenu(this);
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
    	dbHandler.dropAllRequiredTables();
		dbHandler.addRequiredTables();
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
		dbHandler.rentVehicleWithReservation(terminalTransactions);
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
		mainOperations.dispose();
		customerWindow = new CustomerWindow();
		customerWindow.showMenu(this);
	}

	@Override
	public void clerkTransaction() {
		mainOperations.dispose();
		customerWindow = new CustomerWindow();
		customerWindow.showMenu(this);
	}

    @Override
    public void databaseManipulation() {
		mainOperations.dispose();
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
		mainOperations.showMenu(this);
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
	public void showDetailCountResult(String carType, String location, String city, java.sql.Date fromDate, java.sql.Date toDate) {
		JTable resultTable = dbHandler.showVehicleDetails(carType, location, city, fromDate, toDate);
		customerViewResultWindow.showMoreDetail(resultTable);
	}

    @Override
    public void backToMain() {
        databaseManipulationWindow.dispose();
		mainOperations.showMenu(this);
    }

}
