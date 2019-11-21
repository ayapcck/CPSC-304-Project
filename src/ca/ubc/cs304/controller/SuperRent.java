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
public class SuperRent implements ProcessViewDelegate, CusEnterViewDelegate, LoginWindowDelegate,
		CustomerTransactionDelegate {

	private DatabaseConnectionHandler dbHandler = null;
	private LoginWindow loginWindow = null;
	private MainOperations mainOperations = null;
	private CustomerWindow customerWindow = null;
	private ViewAvailableVehiclesWindow viewAvailableVehiclesWindow = null;
	private ViewAvailableVehicleResultWindow viewAvailableVehicleResultWindow = null;
	private DatabaseManipulationWindow databaseManipulationWindow = null;

	private DatabaseController databaseController = null;

	public SuperRent() {
		dbHandler = new DatabaseConnectionHandler();
		databaseController = new DatabaseController(dbHandler);
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

	@Override
	public void showCustomerWindow() {
		mainOperations.dispose();
		customerWindow = new CustomerWindow();
		customerWindow.showMenu(this);
	}

	@Override
	public void showClerkWindow() {
	}

	@Override
	public void showDatabaseWindow() {
		mainOperations.dispose();
		databaseManipulationWindow = new DatabaseManipulationWindow();
		databaseManipulationWindow.showMenu(databaseController);
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
	public void submitView() {
		customerWindow.dispose();
		viewAvailableVehiclesWindow = new ViewAvailableVehiclesWindow();
		viewAvailableVehiclesWindow.showMenu(this);
	}

    @Override
    public void back() {
        customerWindow.dispose();
		mainOperations.showMenu(this);
    }


    @Override
	public void processView(String carType, String location, String city, java.sql.Date fromDate, java.sql.Date toDate) {
		viewAvailableVehiclesWindow.dispose();
		int count = dbHandler.checkVehicleNum(carType, location, city, fromDate, toDate);
        viewAvailableVehicleResultWindow = new ViewAvailableVehicleResultWindow();
        viewAvailableVehicleResultWindow.showMenu(this, count, carType, location, city, fromDate, toDate);
	}

    @Override
    public void backToPrevious() {
        viewAvailableVehicleResultWindow.dispose();
        customerWindow.showMenu(this);
    }

    @Override
	public void showDetailCountResult(String carType, String location, String city, java.sql.Date fromDate, java.sql.Date toDate) {
		JTable resultTable = dbHandler.showVehicleDetails(carType, location, city, fromDate, toDate);
        viewAvailableVehicleResultWindow.showMoreDetail(resultTable);
	}

}
