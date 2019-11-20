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
public class CustomerWindow extends JFrame implements ActionListener {
    private static final int TEXT_FIELD_WIDTH = 10;
    private static final int MAX_LOGIN_ATTEMPTS = 3;
    private JButton view;
    private JButton reserve;
    private CustomerTransactionDelegate customerDelegate = null;
    private ClerkTransactionDelegate clerkDelegate = null;

    public CustomerWindow() {
        super("Who are you?");
    }

    public void showMenu(CustomerTransactionDelegate customerDelegate) {
        this.customerDelegate = customerDelegate;
//        this.clerkDelegate = clerkDelegate;
        view = new JButton("view available vehicles");
        reserve = new JButton("reserve");

        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);

        // layout components using the GridBag layout manager
        FlowLayout flowLayout = new FlowLayout();

        contentPane.setLayout(flowLayout);
        contentPane.add(view);
        contentPane.add(reserve);
        contentPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        // register login button with action event handler
        view.addActionListener(this);
        reserve.addActionListener(this);

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
        if (actionEvent.getSource() == view) {
            customerDelegate.customerTransaction();
        } else if (actionEvent.getSource() == reserve) {
            customerDelegate.customerTransaction();
        }
    }
}
