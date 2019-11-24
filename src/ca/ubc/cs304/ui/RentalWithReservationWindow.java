package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.RentVehicleDelegate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class RentalWithReservationWindow extends Window implements ActionListener {
    private RentVehicleDelegate rentVehicleDelegate;

    private JButton submit;
    private JButton backToClerk;
    private JTextField confirmationNumberField;
    private JTextField cardNameField;
    private JTextField cardNumberField;

    public RentalWithReservationWindow() {
        super("Complete Rental");

        confirmationNumberField = new JTextField(TEXT_FIELD_WIDTH);
        cardNameField = new JTextField(TEXT_FIELD_WIDTH);
        cardNumberField = new JTextField(TEXT_FIELD_WIDTH);

        submit = new JButton("Submit");
        backToClerk = new JButton("Back");
    }

    public void showMenu(RentVehicleDelegate rentVehicleDelegate) {
        this.rentVehicleDelegate = rentVehicleDelegate;

        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);

        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        contentPane.setLayout(gb);
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        placeFieldAndLabel("Confirmation Number", confirmationNumberField, contentPane, gb, c);
        placeFieldAndLabel("Card Type", cardNameField, contentPane, gb, c);
        placeFieldAndLabel("Card Number", cardNumberField, contentPane, gb, c);

        List<JButton> buttons = new ArrayList<>();
        buttons.add(submit);
        buttons.add(backToClerk);
        PanelConstraints setConstraints = (JButton button) -> { setButtonConstraints(gb, c, button); };
        new Panel(buttons, this, this, contentPane, gb, setConstraints);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {
            int confirmationNumber =  Integer.parseInt(confirmationNumberField.getText());
            String cardName = cardNameField.getText();
            int cardNumber = Integer.parseInt(cardNumberField.getText());
            rentVehicleDelegate.rentWithReservation(confirmationNumber, cardName, cardNumber);
        } else if (e.getSource() == backToClerk) {
            rentVehicleDelegate.backToClerk();
        }
    }
}
