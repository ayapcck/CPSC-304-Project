package ca.ubc.cs304.controller;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.delegates.LoginWindowDelegate;
import ca.ubc.cs304.delegates.TerminalTransactionsDelegate;
import ca.ubc.cs304.ui.LoginWindow;
import ca.ubc.cs304.ui.TerminalTransactions;

/**
 * This is the main controller class that will orchestrate everything.
 */
public class SuperRent implements LoginWindowDelegate, TerminalTransactionsDelegate {
	private DatabaseConnectionHandler dbHandler = null;
	private LoginWindow loginWindow = null;

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

			TerminalTransactions transaction = new TerminalTransactions();
			transaction.showMainMenu(this);
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
		dbHandler.rentVehicleWithNoReservation(terminalTransactions);
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
}
