package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.RentVehicleDelegate;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class RentVehicleWindow extends JFrame implements ActionListener {
    private JButton rentWithReservation;
    private JButton rentWithoutReservation;
    private JButton backToClerk;

    private RentVehicleDelegate rentVehicleDelegate = null;

    public RentVehicleWindow() {
        super("Do you have a reservation?");

        backToClerk = new JButton("Back");
        rentWithReservation = new JButton("Has reservation");
        rentWithoutReservation = new JButton("No reservation");
    }

    public void showMenu(RentVehicleDelegate rentVehicleDelegate) {
        this.rentVehicleDelegate = rentVehicleDelegate;

        List<JButton> buttons = new ArrayList<>();
        buttons.add(rentWithReservation);
        buttons.add(rentWithoutReservation);
        buttons.add(backToClerk);
        new Panel(buttons, this, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == rentWithReservation) {
            rentVehicleDelegate.navToRentalWithReservation();
        } else if (e.getSource() == rentWithoutReservation) {
            rentVehicleDelegate.navToRentalNoReservation();
        } else if (e.getSource() == backToClerk) {
            rentVehicleDelegate.backToClerk();
        }
    }
}
