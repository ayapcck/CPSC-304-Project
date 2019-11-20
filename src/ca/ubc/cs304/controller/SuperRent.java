package ca.ubc.cs304.controller;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.delegates.*;
import ca.ubc.cs304.ui.*;

import java.sql.Date;

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
	private DatabaseConnectionHandler handler = null;
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


	public void addTablesAndData() {
		dbHandler.addRequiredTablesAndData();
	}

	public void dropTables() {
		dbHandler.dropAllRequiredTables();
	}

	@Override
	public void setupDatabase() {
    	dbHandler.dropAllRequiredTables();
		dbHandler.addRequiredTablesAndData();
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
	public void submitView() {
		customerWindow.dispose();
		customerViewWindow = new CustomerViewWindow();
		customerViewWindow.showMenu(this);
	}


	@Override
	public void processView(String carType, String location, String city, java.sql.Date fromDate, java.sql.Date toDate) {
		customerViewWindow.dispose();
		handler = new DatabaseConnectionHandler();
		int ret = handler.checkVehicleNum(carType, location, city, fromDate, toDate);
		System.out.println(ret);
	}
}
