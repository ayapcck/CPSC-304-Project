package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.DatabaseManipulationsDelegate;
import ca.ubc.cs304.delegates.LoginWindowDelegate;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.xml.crypto.Data;

/**
 * The class is only responsible for displaying and handling the login GUI.
 */
public class DatabaseManipulationWindow extends JFrame implements ActionListener {
    private static final int TEXT_FIELD_WIDTH = 10;
    private JButton setup;
    private JButton drop;
    private JButton add;
    private JButton viewTables;
    private JButton mainMenu;
    private DatabaseManipulationsDelegate databaseManipulationsDelegate;

    public DatabaseManipulationWindow() {
        super("Database Manipulations");

        add = new JButton("Add required tables");
        drop = new JButton("Drop required tables");
        mainMenu = new JButton("Main menu");
        setup = new JButton("Setup Database(drop required tables and add required tables and data)");
        viewTables = new JButton("View all tables");
    }

    public void showMenu(DatabaseManipulationsDelegate databaseManipulationsDelegate) {
        this.databaseManipulationsDelegate = databaseManipulationsDelegate;
        List<JButton> buttons = new ArrayList<>();
        buttons.add(setup);
        buttons.add(drop);
        buttons.add(add);
        buttons.add(viewTables);
        buttons.add(mainMenu);
        new Panel(buttons, this, this);
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == add) {
            databaseManipulationsDelegate.addRequiredTablesAndData();
        } else if (actionEvent.getSource() == drop) {
            databaseManipulationsDelegate.dropRequiredTables();
        } else if (actionEvent.getSource() == mainMenu) {
            databaseManipulationsDelegate.mainMenu();
        } else if (actionEvent.getSource() == setup) {
            databaseManipulationsDelegate.setupDatabase();
        } else if (actionEvent.getSource() == viewTables) {
            databaseManipulationsDelegate.viewAllTables();
        }
    }
}
