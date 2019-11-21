package ca.ubc.cs304.delegates;

import ca.ubc.cs304.ui.TerminalTransactions;

/**
 * This interface uses the delegation design pattern where instead of having
 * the TerminalTransactions class try to do everything, it will only
 * focus on handling the UI. The actual logic/operation will be delegated to the 
 * controller class (in this case Bank).
 * 
 * TerminalTransactions calls the methods that we have listed below but 
 * Bank is the actual class that will implement the methods.
 */
public interface TerminalTransactionsDelegate {
	public void addRequiredTablesAndData();
	public void dropRequiredTables();
	public void setupDatabase();
	public void viewAllTables();
	public void rentAVehicle(TerminalTransactions terminalTransactions);
	public void rentAVehicleNoRes(TerminalTransactions terminalTransactions);
	public void returnVehicle(TerminalTransactions terminalTransactions);
	public void terminalTransactionsFinished();
}
