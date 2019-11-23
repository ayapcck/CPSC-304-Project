package ca.ubc.cs304.controller;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.delegates.DatabaseMenuDelegate;
import ca.ubc.cs304.ui.DataFromTableWindow;
import ca.ubc.cs304.ui.DeleteFromTableWindow;
import ca.ubc.cs304.ui.MainOperations;

import javax.swing.*;

public class DatabaseMenuController implements DatabaseMenuDelegate {
    private DatabaseConnectionHandler dbHandler = null;
    private JFrame currentWindow = null;

    DatabaseMenuController(JFrame currentWindow) {
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
    public void navToDeleteDataWindow() {
        currentWindow.dispose();
        DeleteFromTableWindow deleteFromTableWindow = new DeleteFromTableWindow();
        DatabaseActionsController databaseActionsController = new DatabaseActionsController(deleteFromTableWindow);
        deleteFromTableWindow.showMenu(databaseActionsController);
    }

    @Override
    public void navToSelectDataWindow() {
        currentWindow.dispose();
        DataFromTableWindow dataFromTableWindow = new DataFromTableWindow();
        DatabaseActionsController databaseActionsController = new DatabaseActionsController(dataFromTableWindow);
        dataFromTableWindow.showMenu(databaseActionsController);
    }

    @Override
    public void mainMenu() {
        currentWindow.dispose();
        MainOperations mainOperations = new MainOperations();
        MainController mainController = new MainController(mainOperations);
        mainOperations.showMenu(mainController);
    }
}
