package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.ClerkTransactionDelegate;
import ca.ubc.cs304.delegates.CustomerTransactionDelegate;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * The class is only responsible for displaying and handling the login GUI.
 */
public class MainOperations extends JFrame implements ActionListener {
    private static final int TEXT_FIELD_WIDTH = 10;
    private JButton customer;
    private JButton clerk;
    private JButton database;
    private CustomerTransactionDelegate customerDelegate = null;
    private ClerkTransactionDelegate clerkDelegate = null;

    public MainOperations() {
        super("Choose an option");
        customer = new JButton("Customer Transactions");
        clerk = new JButton("Clerk Transactions");
        database = new JButton("Database Manipulations");
    }

    public void showMenu(CustomerTransactionDelegate customerDelegate) {
        this.customerDelegate = customerDelegate;
//        this.clerkDelegate = clerkDelegate;
        List<JButton> buttons = new ArrayList<>();
        buttons.add(customer);
        buttons.add(clerk);
        buttons.add(database);
        new Panel(buttons, this, this);
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == customer) {
            customerDelegate.customerTransaction();
        } else if (actionEvent.getSource() == clerk) {
            customerDelegate.customerTransaction();
        } else if (actionEvent.getSource() == database) {
            customerDelegate.databaseManipulation();
        }
    }
}
