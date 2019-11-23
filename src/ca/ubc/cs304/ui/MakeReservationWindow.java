package ca.ubc.cs304.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.*;

import ca.ubc.cs304.delegates.MakeReservationDelegate;

/**
 * The class is only responsible for displaying and handling the login GUI.
 */
public class MakeReservationWindow extends Window implements ActionListener {
    private JButton submit;
    private JButton backToCustomer;
    private JTextField locationField;
    private JTextField vtField;
    private JTextField cityField;
    private String license;
    private JSpinner timeSpinnerFrom;
    private JSpinner timeSpinnerTo;

    // delegate
    private MakeReservationDelegate makeReservationDelegate;

    public MakeReservationWindow() {
        super("Reserve your dream car");

        submit = new JButton("Submit");
        backToCustomer = new JButton("Back");
    }

    public void showMenu(MakeReservationDelegate makeReservationDelegate, String license) {
        this.makeReservationDelegate = makeReservationDelegate;
        this.license = license;

        JLabel locationLabel = new JLabel("Location: ");
        JLabel cityLabel = new JLabel("City: ");
        JLabel vtLabel = new JLabel("Vehicle Type: ");
        JLabel fromTimeLabel = new JLabel("Pickup Time: ");
        JLabel toTimeLabel = new JLabel("Return Time: ");

        locationField = new JTextField(TEXT_FIELD_WIDTH);
        vtField = new JTextField(TEXT_FIELD_WIDTH);
        cityField = new JTextField(TEXT_FIELD_WIDTH);

        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);

        // layout components using the GridBag layout manager
        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        contentPane.setLayout(gb);
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // place the location label and field
        placeLabel(locationLabel, contentPane, gb, c, 10, 5);
        placeTextField(locationField, contentPane, gb, c, TEXT_FIELD_INSET);

        // place city label and field
        placeLabel(cityLabel, contentPane, gb, c, 0, 10);
        placeTextField(cityField, contentPane, gb, c, new Insets(0, 0, 10, 10));

        // place vtype label and field
        placeLabel(vtLabel, contentPane, gb, c, 0, 10);
        placeTextField(vtField, contentPane, gb, c, new Insets(0, 0, 10, 10));

        // place fromTime label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(0, 10, 10, 0);
        gb.setConstraints(fromTimeLabel, c);
        contentPane.add(fromTimeLabel);

        // place the fromTime field
        timeSpinnerFrom = new JSpinner( new SpinnerDateModel() );
        JSpinner.DateEditor fromTimeField = new JSpinner.DateEditor(timeSpinnerFrom, "HH:mm:ss");
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(0, 0, 10, 10);
        gb.setConstraints(timeSpinnerFrom, c);
        contentPane.add(timeSpinnerFrom);

        // place toTime label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(0, 10, 10, 0);
        gb.setConstraints(toTimeLabel, c);
        contentPane.add(toTimeLabel);

        // place the toTime field
        // TODO
        timeSpinnerTo = new JSpinner( new SpinnerDateModel() );
        JSpinner.DateEditor toTimeField = new JSpinner.DateEditor(timeSpinnerTo, "HH:mm:ss");
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(0, 0, 10, 10);
        gb.setConstraints(timeSpinnerTo, c);
        contentPane.add(timeSpinnerTo);

        List<JButton> buttons = new ArrayList<>();
        buttons.add(submit);
        buttons.add(backToCustomer);
        PanelConstraints setConstraints = (JButton button) -> { setButtonConstraints(gb, c, button); };
        new Panel(buttons, this, this, contentPane, gb, setConstraints);

        // place the cursor in the text field for the username
        locationField.requestFocus();
    }

    /**
     * ActionListener Methods
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            DateFormat tf = new SimpleDateFormat("HH:mm");
            Date fromDate = (Date) timeSpinnerFrom.getValue();
            Date toDate = (Date) timeSpinnerTo.getValue();
            String fromDateString = df.format(fromDate);
            String fromTimeString = tf.format(fromDate);
            String toDateString = df.format(toDate);
            String toTimeString = tf.format(toDate);
            String city = cityField.getText();
            String location = locationField.getText();
            String vehicleType = vtField.getText();
            int reservationNum = (int) (Math.random());
            makeReservationDelegate.createReservation(license, location, city, vehicleType, fromDateString,
                    fromTimeString, toDateString, toTimeString, reservationNum);
        } else if (e.getSource() == backToCustomer) {
            makeReservationDelegate.returnToCustomer();
        }
    }
}
