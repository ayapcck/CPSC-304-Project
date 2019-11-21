package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.LoginWindowDelegate;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * The class is only responsible for displaying and handling the login GUI.
 */
public class DatabaseManipulationWindow extends JFrame implements ActionListener {
    private static final int TEXT_FIELD_WIDTH = 10;
    private JButton setup;
    private JButton drop;
    private JButton add;
    private JButton mainMenu;
    private LoginWindowDelegate loginWindowDelegate = null;

    public DatabaseManipulationWindow() {
        super("database manipulation");

        setup = new JButton("Setup Database(drop required tables and add required tables and data)");
        drop = new JButton("Drop required tables");
        add = new JButton("Add required tables");
        mainMenu = new JButton("Main menu");
    }

    public void showMenu(LoginWindowDelegate loginWindowDelegate) {
        this.loginWindowDelegate = loginWindowDelegate;
        List<JButton> buttons = new ArrayList<>();
        buttons.add(setup);
        buttons.add(drop);
        buttons.add(add);
        buttons.add(mainMenu);
        new Panel(buttons, this, this);
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == setup) {
            loginWindowDelegate.setupDatabase();
        } else if (actionEvent.getSource() == drop) {
            loginWindowDelegate.dropRequiredTables();
        } else if (actionEvent.getSource() == add) {
            loginWindowDelegate.addRequiredTablesAndData();
        } else if (actionEvent.getSource() == mainMenu) {
            loginWindowDelegate.backToMain();
        }
    }
}
