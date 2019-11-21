package ca.ubc.cs304.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import ca.ubc.cs304.delegates.TerminalTransactionsDelegate;
import ca.ubc.cs304.model.BranchModel;

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

	private void handleClerkInteractions() {}

	private void handleCustomerInteracions() {}

	private void handleDatabaseInteractions() {

		int choice = INVALID_INPUT;

		while (choice != 6) {
			System.out.println();

			System.out.println("1. Setup Database (Option 2, then 3, together)");
			System.out.println("2. Drop Required Tables");
			System.out.println("3. Add Required Tables and Data");
			System.out.println("4. View all tables");
			System.out.println("5. View data from table");
			System.out.println("6. Main Menu");
			System.out.println("7. Quit");
			System.out.print("Please choose one of the above 7 options: ");

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
						handleGetDataFromTableOption();
						break;
					case 6:
						handleMainInteractions();
						break;
					case 7:
						handleQuitOption();
						break;
					default:
						System.out.println(WARNING_TAG + " The number that you entered was not a valid option.");
						break;
				}
			}
		}
	}

	private void handleGetDataFromTableOption() {
		System.out.println("Please enter the desired table name: ");
		String tableName = readLine();
		System.out.println("Please enter the desired column names, delimited with commas: ");
		String columnsLine = readLine();
		String[] columns = columnsLine.split(",");
		delegate.getDataFromTable(columns, tableName);
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
	
	private int readInteger(boolean allowEmpty) {
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
	
	private String readLine() {
		String result = null;
		try {
			result = bufferedReader.readLine();
		} catch (IOException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
		return result;
	}
}
