package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.RentVehicleDelegate;
import ca.ubc.cs304.model.Branch;
import ca.ubc.cs304.model.Reservation;
import ca.ubc.cs304.model.TimePeriod;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RentalWithoutReservationWindow extends Window implements ActionListener {
    private RentVehicleDelegate rentVehicleDelegate = null;

    private JButton submit;
    private JButton backToClerkMenu;
    private JTextField vehicleTypeField;
    private JTextField cardNameField;
    private JTextField cardNumberField;
    private JTextField licenseField;
    private JTextField locationField;
    private JTextField cityField;
    private JSpinner timeSpinnerFrom;
    private JSpinner timeSpinnerTo;

    public RentalWithoutReservationWindow() {
        super("Fill out rental form");

        submit = new JButton("Submit");
        backToClerkMenu = new JButton("Back");

        vehicleTypeField = new JTextField(TEXT_FIELD_WIDTH);
        cardNameField = new JTextField(TEXT_FIELD_WIDTH);
        cardNumberField = new JTextField(TEXT_FIELD_WIDTH);
        licenseField = new JTextField(TEXT_FIELD_WIDTH);
        locationField = new JTextField(TEXT_FIELD_WIDTH);
        cityField = new JTextField(TEXT_FIELD_WIDTH);
    }

    public void showMenu(RentVehicleDelegate rentVehicleDelegate) {
        this.rentVehicleDelegate = rentVehicleDelegate;

        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);

        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        contentPane.setLayout(gb);
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        placeFieldAndLabel("Vehicle Type", vehicleTypeField, contentPane, gb, c);
        placeFieldAndLabel("Driver's License", licenseField, contentPane, gb, c);
        placeFieldAndLabel("Card Type", cardNameField, contentPane, gb, c);
        placeFieldAndLabel("Card Number", cardNumberField, contentPane, gb, c);
        placeFieldAndLabel("Location", locationField, contentPane, gb, c);
        placeFieldAndLabel("City", cityField, contentPane, gb, c);

        placeLabel("From: ", contentPane, gb, c, 0, 10);
        timeSpinnerFrom = new JSpinner( new SpinnerDateModel() );
        JSpinner.DateEditor fromTimeField = new JSpinner.DateEditor(timeSpinnerFrom, "HH:mm:ss");
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(0, 0, 10, 10);
        gb.setConstraints(timeSpinnerFrom, c);
        contentPane.add(timeSpinnerFrom);

        placeLabel("To: ", contentPane, gb, c, 0, 10);
        timeSpinnerTo = new JSpinner( new SpinnerDateModel() );
        JSpinner.DateEditor toTimeField = new JSpinner.DateEditor(timeSpinnerTo, "HH:mm:ss");
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(0, 0, 10, 10);
        gb.setConstraints(timeSpinnerTo, c);
        contentPane.add(timeSpinnerTo);

        List<JButton> buttons = new ArrayList<>();
        buttons.add(submit);
        buttons.add(backToClerkMenu);
        PanelConstraints setConstraints = (JButton button) -> { setButtonConstraints(gb, c, button); };
        new Panel(buttons, this, this, contentPane, gb, setConstraints);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat tf = new SimpleDateFormat("HH:mm");
            Date fromDateTime = (Date) timeSpinnerFrom.getValue();
            Date toDateTime = (Date) timeSpinnerTo.getValue();
            String fromDate = df.format(fromDateTime);
            String fromTime = tf.format(fromDateTime);
            String toDate = df.format(toDateTime);
            String toTime = tf.format(toDateTime);
            TimePeriod timePeriod = new TimePeriod(fromDate, fromTime, toDate, toTime);

            int confNo = (int) (Math.random() * 100);
            System.out.println(confNo);
            Reservation reservation = new Reservation(confNo, vehicleTypeField.getText(),
                    licenseField.getText(), timePeriod, locationField.getText(), cityField.getText());
            Branch branch = new Branch(locationField.getText(), cityField.getText());
            rentVehicleDelegate.rentWithoutReservation(reservation, branch,
                    cardNameField.getText(), Integer.parseInt(cardNumberField.getText()));
        } else if (e.getSource() == backToClerkMenu) {
            rentVehicleDelegate.backToClerk();
        }
    }
}
