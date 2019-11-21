package ca.ubc.cs304.controller;

import ca.ubc.cs304.delegates.DatabaseManipulationsDelegate;
import ca.ubc.cs304.database.DatabaseConnectionHandler;

public class DatabaseController implements DatabaseManipulationsDelegate {
    DatabaseConnectionHandler dbHandler = null;

    DatabaseController(DatabaseConnectionHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    @Override
    public void setupDatabase() {
        dbHandler.dropAllRequiredTables();
        dbHandler.addRequiredTables();
    }

    @Override
    public void dropRequiredTables() {

    }

    @Override
    public void addRequiredTablesAndData() {

    }

    @Override
    public void viewAllTables() {
        String[] tables = dbHandler.getAllTables();
        for (String table : tables) {
            System.out.println(table);
        }
    }

    @Override
    public void mainMenu() {

    }
}
