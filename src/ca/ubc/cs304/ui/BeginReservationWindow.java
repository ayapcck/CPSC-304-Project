package ca.ubc.cs304.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import ca.ubc.cs304.controller.BeginReservationController;
import ca.ubc.cs304.delegates.BeginReservationDelegate;

/**
 * The class is only responsible for displaying and handling the login GUI.
 */
public class BeginReservationWindow extends JFrame implements ActionListener {

    private JButton newCustomer;
    private JButton existingCustomer;
    private BeginReservationDelegate beginReservationDelegate;

    public BeginReservationWindow() {
        super("Enter reservation: are you new?");

        newCustomer = new JButton("New customer");
        existingCustomer = new JButton("Existing customer");
    }

    public void showMenu(BeginReservationController makeReservationDelegate) {
        this.beginReservationDelegate = makeReservationDelegate;
        List<JButton> buttons = new ArrayList<>();
        buttons.add(newCustomer);
        buttons.add(existingCustomer);
        new Panel(buttons, this, this);
    }

    /**
     * ActionListener Methods
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newCustomer) {
            beginReservationDelegate.newCustomerReservation();
        } else if (e.getSource() == existingCustomer) {
            beginReservationDelegate.existingCustomerReservation();
        }
     }
}
