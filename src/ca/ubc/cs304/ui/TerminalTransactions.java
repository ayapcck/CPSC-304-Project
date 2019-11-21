package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.TerminalTransactionsDelegate;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import ca.ubc.cs304.delegates.TerminalTransactionsDelegate;
import ca.ubc.cs304.model.BranchModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import ca.ubc.cs304.delegates.LoginWindowDelegate;

/**
 * The class is only responsible for handling terminal text inputs. 
 */
public class TerminalTransactions {
	private static final String EXCEPTION_TAG = "[EXCEPTION]";
	private static final String WARNING_TAG = "[WARNING]";
	private static final int INVALID_INPUT = Integer.MIN_VALUE;
	private static final int EMPTY_INPUT = 0;
	
	private BufferedReader bufferedReader = null;
	private TerminalTransactionsDelegate delegate = null;
	private JTextField usernameField;
	private JPasswordField passwordField;

	public TerminalTransactions() {

	}

	/**
	 * Displays simple text interface
	 */ 
	public void showMainMenu(TerminalTransactionsDelegate delegate) {
		this.delegate = delegate;
		
	    bufferedReader = new BufferedReader(new InputStreamReader(System.in));
	    handleMainInteractions();

	}

	private void handleMainInteractions() {
		int choice = INVALID_INPUT;

		while (choice != 4) {
			System.out.println();

			System.out.println("1. Database Manipulations (Including Setup)");
			System.out.println("2. Customer Transactions");
			System.out.println("3. Clerk Transactions");
			System.out.println("4. Quit");
			System.out.print("Please choose one of the above 4 options: ");

			choice = readInteger(false);

			System.out.println(" ");

			if (choice != INVALID_INPUT) {
				switch (choice) {
					case 1:
						handleDatabaseInteractions();
						break;
					case 2:
						handleCustomerInteracions();
						break;
					case 3:
						handleClerkInteractions();
						break;
					case 4:
						handleQuitOption();
						break;
					default:
						System.out.println(WARNING_TAG + " The number that you entered was not a valid option.");
						break;
				}
			}
		}
	}

	public void handleClerkInteractions() {
		int choice = INVALID_INPUT;
		while (choice != 6) {
			System.out.println("1: Rent a Vehicle");
			System.out.println("2: Return vehicle");
			System.out.println("5: Main Menu");
			System.out.println("6. Quit");
			choice = readInteger(false);
			System.out.println(" ");

			if (choice != INVALID_INPUT) {
				switch (choice) {
					case 1:
                        System.out.println("1: Customer already has reservation");
                        System.out.println("2: Customer has no reservation");
                        System.out.println("5: Main Menu");
                        choice = readInteger(false);
                        System.out.println(" ");
                        switch (choice) {
                            case 1:
                                delegate.rentAVehicle(this);
                                break;
                            case 2:
                                delegate.rentAVehicleNoRes(this);
                                break;
                            case 5:
                                handleMainInteractions();
                                break;
                        }
						break;
					case 2:
						break;
					case 5:
						handleMainInteractions();
					case 6:
						handleQuitOption();
						break;
				}
			}
		}
	}

	private void handleCustomerInteracions() {}

	private void handleDatabaseInteractions() {

		int choice = INVALID_INPUT;

		while (choice != 6) {
			System.out.println();

			System.out.println("1. Setup Database (Option 2, then 3, together)");
			System.out.println("2. Drop Required Tables");
			System.out.println("3. Add Required Tables and Data");
			System.out.println("4. View all tables");
			System.out.println("5. Main Menu");
			System.out.println("6. Quit");
			System.out.print("Please choose one of the above 6 options: ");

			choice = readInteger(false);

			System.out.println(" ");

			if (choice != INVALID_INPUT) {
				switch (choice) {
					case 1:
						delegate.setupDatabase();
						break;
					case 2:
						delegate.dropRequiredTables();
						break;
					case 3:
						delegate.addRequiredTablesAndData();
						break;
					case 4:
						delegate.viewAllTables();
						break;
					case 5:
						handleMainInteractions();
						break;
					case 6:
						handleQuitOption();
						break;
					default:
						System.out.println(WARNING_TAG + " The number that you entered was not a valid option.");
						break;
				}
			}
		}
	}

	private void handleQuitOption() {
		System.out.println("Good Bye!");
		
		if (bufferedReader != null) {
			try {
				bufferedReader.close();
			} catch (IOException e) {
				System.out.println("IOException!");
			}
		}
		
		delegate.terminalTransactionsFinished();
	}
	
	public int readInteger(boolean allowEmpty) {
		String line = null;
		int input = INVALID_INPUT;
		try {
			line = bufferedReader.readLine();
			input = Integer.parseInt(line);
		} catch (IOException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		} catch (NumberFormatException e) {
			if (allowEmpty && line.length() == 0) {
				input = EMPTY_INPUT;
			} else {
				System.out.println(WARNING_TAG + " Your input was not an integer");
			}
		}
		return input;
	}
	
	public String readLine() {
		String result = null;
		try {
			result = bufferedReader.readLine();
		} catch (IOException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
		return result;
	}
}
