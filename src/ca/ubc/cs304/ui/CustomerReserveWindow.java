package ca.ubc.cs304.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import ca.ubc.cs304.delegates.LoginWindowDelegate;

/**
 * The class is only responsible for displaying and handling the login GUI.
 */
public class CustomerReserveWindow extends JFrame implements ActionListener {

    private JButton newCus;
    private JButton old;
    // delegate
    private LoginWindowDelegate delegate;

    public CustomerReserveWindow() {
        super("Enter reservation: are you new?");
    }

    public void showMenu(LoginWindowDelegate delegate) {
        this.delegate = delegate;
        newCus = new JButton("new customer");
        old = new JButton("existing customer");
        JPanel contentPane = new JPanel();
//        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
        FlowLayout flowLayout = new FlowLayout();
        contentPane.setLayout(flowLayout);
        this.setContentPane(contentPane);
        contentPane.add(newCus);
        contentPane.add(old);

        newCus.addActionListener(this);
        old.addActionListener(this);

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

        // place the cursor in the text field for the username
        newCus.requestFocus();
    }

    /**
     * ActionListener Methods
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newCus) {
            delegate.newCusReserve();
        } else if (e.getSource() == old) {
            delegate.oldCusReserve();
        }
     }
}
