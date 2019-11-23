package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.DatabaseMenuDelegate;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

/**
 * The class is only responsible for displaying and handling the login GUI.
 */
public class DatabaseManipulationWindow extends Window implements ActionListener {
    private static final int TEXT_FIELD_WIDTH = 10;
    private JButton setup;
    private JButton drop;
    private JButton add;
    private JButton viewTables;
    private JButton deleteFromTable;
    private JButton getDataFromTable;
    private JButton insertDataIntoTable;
    private JButton updateTable;
    private JButton mainMenu;
    private DatabaseMenuDelegate databaseMenuDelegate;

    public DatabaseManipulationWindow() {
        super("Database Manipulations");

        add = new JButton("Add required tables");
        deleteFromTable = new JButton("Delete data from table");
        drop = new JButton("Drop required tables");
        getDataFromTable = new JButton("Get data from table");
        insertDataIntoTable = new JButton("Insert data into table");
        mainMenu = new JButton("Main Menu");
        setup = new JButton("Setup Database(drop required tables and add required tables and data)");
        updateTable = new JButton("Update data in table");
        viewTables = new JButton("View all tables");
    }

    public void showMenu(DatabaseMenuDelegate databaseMenuDelegate) {
        this.databaseMenuDelegate = databaseMenuDelegate;
        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);

        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        List<JButton> buttons = new ArrayList<>();
        buttons.add(setup);
        buttons.add(drop);
        buttons.add(add);
        buttons.add(viewTables);
        buttons.add(getDataFromTable);
        buttons.add(insertDataIntoTable);
        buttons.add(updateTable);
        buttons.add(deleteFromTable);
        buttons.add(mainMenu);
        PanelConstraints setConstraints = (JButton button) -> { setButtonConstraints(gb, c, button); };
        new Panel(buttons, this, this, contentPane, gb, setConstraints);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == add) {
            databaseMenuDelegate.addRequiredTablesAndData();
        } else if (e.getSource() == drop) {
            databaseMenuDelegate.dropRequiredTables();
        } else if (e.getSource() == mainMenu) {
            databaseMenuDelegate.mainMenu();
        } else if (e.getSource() == setup) {
            databaseMenuDelegate.setupDatabase();
        } else if (e.getSource() == viewTables) {
            databaseMenuDelegate.viewAllTables();
        } else if (e.getSource() == getDataFromTable) {
            databaseMenuDelegate.navToSelectDataWindow();
        } else if (e.getSource() == deleteFromTable) {
            databaseMenuDelegate.navToDeleteDataWindow();
        } else if (e.getSource() == insertDataIntoTable) {
            databaseMenuDelegate.navToInsertDataWindow();
        } else if (e.getSource() == updateTable) {
            databaseMenuDelegate.navToUpdateDataWindow();
        }
    }
}
