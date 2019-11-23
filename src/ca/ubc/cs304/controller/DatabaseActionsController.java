package ca.ubc.cs304.controller;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.delegates.DatabaseActionsDelegate;
import ca.ubc.cs304.ui.DatabaseManipulationWindow;

import javax.swing.*;
import java.util.List;

public class DatabaseActionsController implements DatabaseActionsDelegate {
    private DatabaseConnectionHandler dbHandler = null;
    private JFrame currentWindow;

    public DatabaseActionsController(JFrame currentWindow) {
        this.currentWindow = currentWindow;
        dbHandler = DatabaseConnectionHandler.getDBHandlerInstance();
    }

    @Override
    public void insertIntoTable(String tableName, String columns, String values) {
        dbHandler.insertIntoTable(tableName, columns, values);
    }

    @Override
    public void deleteFromTable(String tableName, String column, String value) {
        dbHandler.deleteFromTable(tableName, column, value);
    }

    @Override
    public void selectFromTable(String tableName, String columns) {
        dbHandler.getDataFromTable(tableName, columns);
    }

    @Override
    public void updateTable(String tableName, String columns, String values, String whereColumn, String whereValue) {
        dbHandler.updateTable(tableName, columns, values, whereColumn, whereValue);
    }

    @Override
    public void backToDatabaseMenu() {
        currentWindow.dispose();
        DatabaseManipulationWindow databaseManipulationWindow = new DatabaseManipulationWindow();
        DatabaseMenuController databaseMenuController = new DatabaseMenuController(databaseManipulationWindow);
        databaseManipulationWindow.showMenu(databaseMenuController);
    }
}
