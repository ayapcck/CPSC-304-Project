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
import ca.ubc.cs304.model.Branch;
import ca.ubc.cs304.model.Reservation;
import ca.ubc.cs304.model.TimePeriod;

/**
 * The class is only responsible for displaying and handling the login GUI.
 */
public class MakeReservationWindow extends Window implements ActionListener {
    private JButton submit;
    private JButton backToCustomer;
    private JTextField licenseField;
    private JComboBox vtBox;
    private JComboBox locationBox;
    private JComboBox cityBox;
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

        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);

        // layout components using the GridBag layout manager
        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        contentPane.setLayout(gb);
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        if (this.license == null) {
            placeFieldAndLabel("License", licenseField, contentPane, gb, c);
        }

        // place car type
        JLabel carType = new JLabel("Car Type:");
        placeLabel(carType, contentPane, gb, c, 10, 5);
        String[] vtList = {"SUV", "full-size", "truck", "economy", "mid-size", "standard"};
        vtBox = new JComboBox(vtList);
        vtBox.setSelectedIndex(0);
        vtBox.addActionListener(this);
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(10,0,0,0);
        gb.setConstraints(vtBox, c);
        contentPane.add(vtBox);

        // place location label and field
        JLabel location = new JLabel("Location:");
        placeLabel(location, contentPane, gb, c, 0, 10);
        String[] locationList = {"shop_1", "shop_2", "shop_3", "shop_4"};
        locationBox = new JComboBox(locationList);
        locationBox.setSelectedIndex(0);
        locationBox.addActionListener(this);
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(10,0,0,0);
        gb.setConstraints(locationBox, c);
        contentPane.add(locationBox);

        // place city label and field
        JLabel cityLabel = new JLabel("City:");
        placeLabel(cityLabel, contentPane, gb, c, 0, 10);
        String[] cityList = {"Vancouver", "Richmond", "Burnaby", "Coquitlam"};
        cityBox = new JComboBox(cityList);
        cityBox.setSelectedIndex(0);
        cityBox.addActionListener(this);
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(10,0,0,0);
        gb.setConstraints(cityBox, c);
        contentPane.add(cityBox);

        // place fromTime label
        placeLabel("Pickup Time: ", contentPane, gb, c, 0, 10);
        // place the fromTime field
        timeSpinnerFrom = new JSpinner( new SpinnerDateModel() );
        JSpinner.DateEditor fromTimeField = new JSpinner.DateEditor(timeSpinnerFrom, "HH:mm:ss");
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(0, 0, 10, 10);
        gb.setConstraints(timeSpinnerFrom, c);
        contentPane.add(timeSpinnerFrom);

        // place toTime label
        placeLabel("Return Time: ", contentPane, gb, c, 0, 10);
        // place the toTime field
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
        vtBox.requestFocus();
    }

//    public void showExistingCusMenu(MakeReservationDelegate makeReservationDelegate) {
//        this.makeReservationDelegate = makeReservationDelegate;
//        JLabel locationLabel = new JLabel("Location:");
//        JLabel cityLabel = new JLabel("City:");
//        JLabel vtLabel = new JLabel("Vehicle Type:");
//        JLabel fromTimeLabel = new JLabel("Pickup Time:");
//        JLabel toTimeLabel = new JLabel("Return Time:");
//        JLabel licenceLabel = new JLabel("Licence plate:");
//
//        locationField = new JTextField(TEXT_FIELD_WIDTH);
//        vtField = new JTextField(TEXT_FIELD_WIDTH);
//        cityField = new JTextField(TEXT_FIELD_WIDTH);
//        licenceField = new JTextField(TEXT_FIELD_WIDTH);
//
//        okExisting = new JButton("submit");
//        back = new JButton("back");
//
//        JPanel contentPane = new JPanel();
//        this.setContentPane(contentPane);
//
//        // layout components using the GridBag layout manager
//        GridBagLayout gb = new GridBagLayout();
//        GridBagConstraints c = new GridBagConstraints();
//
//        contentPane.setLayout(gb);
//        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//
//
//        // place licence label
//        c.gridwidth = GridBagConstraints.RELATIVE;
//        c.insets = new Insets(0, 10, 10, 0);
//        gb.setConstraints(licenceLabel, c);
//        contentPane.add(licenceLabel);
//
//        // place the licence field
//        c.gridwidth = GridBagConstraints.REMAINDER;
//        c.insets = new Insets(0, 0, 10, 10);
//        gb.setConstraints(licenceField, c);
//        contentPane.add(licenceField);
//
//        // place the location label
//        c.gridwidth = GridBagConstraints.RELATIVE;
//        c.insets = new Insets(10, 10, 5, 0);
//        gb.setConstraints(locationLabel, c);
//        contentPane.add(locationLabel);
//
//        // place the text field for the location
//        c.gridwidth = GridBagConstraints.REMAINDER;
//        c.insets = new Insets(10, 0, 5, 10);
//        gb.setConstraints(locationField, c);
//        contentPane.add(locationField);
//
//        // place city label
//        c.gridwidth = GridBagConstraints.RELATIVE;
//        c.insets = new Insets(0, 10, 10, 0);
//        gb.setConstraints(cityLabel, c);
//        contentPane.add(cityLabel);
//
//        // place the city field
//        c.gridwidth = GridBagConstraints.REMAINDER;
//        c.insets = new Insets(0, 0, 10, 10);
//        gb.setConstraints(cityField, c);
//        contentPane.add(cityField);
//
//        // place vtype label
//        c.gridwidth = GridBagConstraints.RELATIVE;
//        c.insets = new Insets(0, 10, 10, 0);
//        gb.setConstraints(vtLabel, c);
//        contentPane.add(vtLabel);
//
//        // place the vtype field
//        c.gridwidth = GridBagConstraints.REMAINDER;
//        c.insets = new Insets(0, 0, 10, 10);
//        gb.setConstraints(vtField, c);
//        contentPane.add(vtField);
//
//        // place fromTime label
//        c.gridwidth = GridBagConstraints.RELATIVE;
//        c.insets = new Insets(0, 10, 10, 0);
//        gb.setConstraints(fromTimeLabel, c);
//        contentPane.add(fromTimeLabel);
//
//        // place the fromTime field
//        timeSpinnerFrom = new JSpinner( new SpinnerDateModel() );
//        JSpinner.DateEditor fromTimeField = new JSpinner.DateEditor(timeSpinnerFrom, "HH:mm:ss");
//        c.gridwidth = GridBagConstraints.REMAINDER;
//        c.insets = new Insets(0, 0, 10, 10);
//        gb.setConstraints(timeSpinnerFrom, c);
//        contentPane.add(timeSpinnerFrom);
//
//        // place toTime label
//        c.gridwidth = GridBagConstraints.RELATIVE;
//        c.insets = new Insets(0, 10, 10, 0);
//        gb.setConstraints(toTimeLabel, c);
//        contentPane.add(toTimeLabel);
//
//        // place the toTime field
//        // TODO
//        timeSpinnerTo = new JSpinner( new SpinnerDateModel() );
//        JSpinner.DateEditor toTimeField = new JSpinner.DateEditor(timeSpinnerTo, "HH:mm:ss");
//        c.gridwidth = GridBagConstraints.REMAINDER;
//        c.insets = new Insets(0, 0, 10, 10);
//        gb.setConstraints(timeSpinnerTo, c);
//        contentPane.add(timeSpinnerTo);
//
//
//        // place the login button
//        c.gridwidth = GridBagConstraints.REMAINDER;
//        c.insets = new Insets(5, 10, 10, 10);
//        c.anchor = GridBagConstraints.CENTER;
//        gb.setConstraints(okExisting, c);
//        contentPane.add(okExisting);
//
//        c.gridwidth = GridBagConstraints.REMAINDER;
//        c.insets = new Insets(0, 10, 10, 10);
//        c.anchor = GridBagConstraints.CENTER;
//        gb.setConstraints(back, c);
//        contentPane.add(back);
//
//        // register login button with action event handler
//        okExisting.addActionListener(this);
//        back.addActionListener(this);
//
//        // anonymous inner class for closing the window
//        this.addWindowListener(new WindowAdapter() {
//            public void windowClosing(WindowEvent e) {
//                System.exit(0);
//            }
//        });
//
//        // size the window to obtain a best fit for the components
//        this.pack();
//
//        // center the frame
//        Dimension d = this.getToolkit().getScreenSize();
//        Rectangle r = this.getBounds();
//        this.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );
//
//        // make the window visible
//        this.setVisible(true);
//
//        // place the cursor in the text field for the username
//        licenceField.requestFocus();
//
//    }
//
    public void showConfirm(MakeReservationDelegate makeReservationDelegate, Reservation reservation, Branch branch) {
        this.makeReservationDelegate = makeReservationDelegate;
        TimePeriod timePeriod = reservation.getTimePeriod();

        JLabel info = new JLabel("Here is your reservation detail:");
        JLabel resNum = new JLabel("Reservation no.: " + reservation.getConfNo());
        JLabel locationLabel = new JLabel("Location: " + branch.getLocation());
        JLabel cityLabel = new JLabel("City:" + branch.getCity());
        JLabel vtLabel = new JLabel("Vehicle Type:" + reservation.getVtName());
        JLabel fromTimeLabel = new JLabel("Pickup Time:" + timePeriod.getFromDate().toString() + " " + timePeriod.getFromTime());
        JLabel toTimeLabel = new JLabel("Return Time:" + timePeriod.getToDate().toString() + " " + timePeriod.getToTime());
        JLabel licenceLabel = new JLabel("Licence plate:" + license);

        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);

        // layout components using the GridBag layout manager
        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        contentPane.setLayout(gb);
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        // place info label
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(0, 10, 10, 0);
        gb.setConstraints(info, c);
        contentPane.add(info);
        // place info label
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(0, 10, 10, 0);
        gb.setConstraints(resNum, c);
        contentPane.add(resNum);
        // place licence label
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(0, 10, 10, 0);
        gb.setConstraints(licenceLabel, c);
        contentPane.add(licenceLabel);
        // place location label
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(0, 10, 10, 0);
        gb.setConstraints(locationLabel, c);
        contentPane.add(locationLabel);
        // place city label
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(0, 10, 10, 0);
        gb.setConstraints(cityLabel, c);
        contentPane.add(cityLabel);
        // place vtname label
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(0, 10, 10, 0);
        gb.setConstraints(vtLabel, c);
        contentPane.add(vtLabel);
        // place fromTime label
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(0, 10, 10, 0);
        gb.setConstraints(fromTimeLabel, c);
        contentPane.add(fromTimeLabel);
        // place toTime label
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(0, 10, 10, 0);
        gb.setConstraints(toTimeLabel, c);
        contentPane.add(toTimeLabel);

        List<JButton> buttons = new ArrayList<>();
        buttons.add(backToCustomer);
        new Panel(buttons, this, this);

    }
    /**
     * ActionListener Methods
     */
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
            String city = (String) cityBox.getSelectedItem();
            String location = (String) locationBox.getSelectedItem();
            String vehicleType = (String) vtBox.getSelectedItem();
            String license = this.license;
            if (license == null) {
                license = licenseField.getText();
            }
            int reservationNum = (int) (Math.random());
            Reservation reservation = new Reservation(reservationNum, vehicleType, license, fromDate, fromTime, toDate, toTime);
            Branch branch = new Branch(location, city);
            makeReservationDelegate.createReservation(reservation, branch);
        } else if (e.getSource() == backToCustomer) {
            makeReservationDelegate.returnToCustomer();
        }
    }


}
