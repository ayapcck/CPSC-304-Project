package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.RentVehicleDelegate;
import ca.ubc.cs304.model.Branch;
import ca.ubc.cs304.model.Rental;
import ca.ubc.cs304.model.Reservation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class RentalReceiptWindow extends Window implements ActionListener {
    private RentVehicleDelegate rentVehicleDelegate = null;

    private JButton backToClerkMenu;

    public RentalReceiptWindow() {
        super("Rental Receipt Information");

        backToClerkMenu = new JButton("Back");
    }

    public void showMenu(RentVehicleDelegate rentVehicleDelegate, Rental rental) {
        this.rentVehicleDelegate = rentVehicleDelegate;

        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);

        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        contentPane.setLayout(gb);
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel confirmationNumberLabel = new JLabel("Confirmation Number: " + rental.getConfNo());
        JLabel fromDateLabel = new JLabel("Pickup Date: " + rental.getFromDate());
        JLabel toDateLabel = new JLabel("Return Date: " + rental.getToDate());

        placeLabelRemainder(confirmationNumberLabel, contentPane, gb, c);
//        placeLabelRemainder(vehicleTypeLabel, contentPane, gb, c);
//        placeLabelRemainder(locationField, contentPane, gb, c);
//        placeLabelRemainder(cityField, contentPane, gb, c);
        placeLabelRemainder(fromDateLabel, contentPane, gb, c);
        placeLabelRemainder(toDateLabel, contentPane, gb, c);

        List<JButton> buttons = new ArrayList<>();
        buttons.add(backToClerkMenu);
        PanelConstraints setConstraints = (JButton button) -> { setButtonConstraints(gb, c, button); };
        new Panel(buttons, this, this, contentPane, gb, setConstraints);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backToClerkMenu) {
            rentVehicleDelegate.backToClerk();
        }
    }
}
