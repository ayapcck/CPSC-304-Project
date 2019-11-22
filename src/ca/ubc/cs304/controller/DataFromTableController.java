package ca.ubc.cs304.controller;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.delegates.DataFromTableDelegate;
import ca.ubc.cs304.ui.DatabaseManipulationWindow;

import javax.swing.*;
import java.util.List;

public class DataFromTableController implements DataFromTableDelegate {
    private DatabaseConnectionHandler dbHandler = null;
    private JFrame currentWindow;

    public DataFromTableController(JFrame currentWindow) {
        this.currentWindow = currentWindow;
        dbHandler = DatabaseConnectionHandler.getDBHandlerInstance();
    }

    @Override
    public void submit(String tableName, String columns) {
        dbHandler.getDataFromTable(tableName, columns);
    }

    @Override
    public void backToDatabase() {
        currentWindow.dispose();
        DatabaseManipulationWindow databaseManipulationWindow = new DatabaseManipulationWindow();
        DatabaseController databaseController = new DatabaseController(databaseManipulationWindow);
        databaseManipulationWindow.showMenu(databaseController);
    }
}
