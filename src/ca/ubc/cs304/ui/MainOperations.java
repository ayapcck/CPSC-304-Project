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
public class MainOperations extends JFrame implements ActionListener {
    private static final int TEXT_FIELD_WIDTH = 10;
    private JButton customer;
    private JButton clerk;
    private JButton database;
    private LoginWindowDelegate loginWindowDelegate = null;

    public MainOperations() {
        super("Choose an option");
        customer = new JButton("Customer Transactions");
        clerk = new JButton("Clerk Transactions");
        database = new JButton("Database Manipulations");
    }

    public void showMenu(LoginWindowDelegate delegate) {
        this.loginWindowDelegate = delegate;
        List<JButton> buttons = new ArrayList<>();
        buttons.add(customer);
        buttons.add(clerk);
        buttons.add(database);
        new Panel(buttons, this, this);
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == customer) {
            loginWindowDelegate.showCustomerWindow();
        } else if (actionEvent.getSource() == clerk) {
            loginWindowDelegate.showClerkWindow();
        } else if (actionEvent.getSource() == database) {
            loginWindowDelegate.showDatabaseWindow();
        }
    }
}
