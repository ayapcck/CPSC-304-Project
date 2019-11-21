package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.ClerkTransactionDelegate;
import ca.ubc.cs304.delegates.CustomerTransactionDelegate;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * The class is only responsible for displaying and handling the login GUI.
 */
public class TransactionWindow extends JFrame implements ActionListener {
    private static final int TEXT_FIELD_WIDTH = 10;
    private JButton customer;
    private JButton clerk;
    private JButton database;
    private CustomerTransactionDelegate customerDelegate = null;
    private ClerkTransactionDelegate clerkDelegate = null;

    public TransactionWindow() {
        super("Who are you?");
    }

    public void showMenu(CustomerTransactionDelegate customerDelegate) {
        this.customerDelegate = customerDelegate;
//        this.clerkDelegate = clerkDelegate;

        customer = new JButton("customer");
        clerk = new JButton("clerk");
        database = new JButton("database manipulation");
        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);

        // layout components using the GridBag layout manager
        FlowLayout flowLayout = new FlowLayout();

        contentPane.setLayout(flowLayout);
        contentPane.add(database);
        contentPane.add(customer);
        contentPane.add(clerk);
        contentPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        // register login button with action event handler
        customer.addActionListener(this);
        clerk.addActionListener(this);
        database.addActionListener(this);
        // anonymous inner class for closing the window
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        // size the window to obtain a best fit for the components
        this.pack();

        // center the frame
        Dimension d = this.getToolkit().getScreenSize();
        Rectangle r = this.getBounds();
        this.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );

        // make the window visible
        this.setVisible(true);
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
