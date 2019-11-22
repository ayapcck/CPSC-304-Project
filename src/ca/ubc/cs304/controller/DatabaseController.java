package ca.ubc.cs304.controller;

import ca.ubc.cs304.delegates.DatabaseManipulationsDelegate;
import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.ui.MainOperations;
import org.omg.CORBA.FREE_MEM;

import javax.swing.*;

public class DatabaseController implements DatabaseManipulationsDelegate {
    private DatabaseConnectionHandler dbHandler = null;
    private JFrame currentWindow = null;

    DatabaseController(JFrame currentWindow) {
        this.dbHandler = DatabaseConnectionHandler.getDBHandlerInstance();
        this.currentWindow = currentWindow;
    }

    @Override
    public void setupDatabase() {
        dbHandler.dropAllRequiredTables();
        dbHandler.addRequiredTablesAndData();
    }

    @Override
    public void dropRequiredTables() {
        dbHandler.dropAllRequiredTables();
    }

    @Override
    public void addRequiredTablesAndData() {
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
    public void mainMenu() {
        currentWindow.dispose();
        MainOperations mainOperations = new MainOperations();
        MainController mainController = new MainController(mainOperations);
        mainOperations.showMenu(mainController);
    }
}