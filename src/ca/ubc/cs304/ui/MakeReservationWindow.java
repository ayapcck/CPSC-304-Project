package ca.ubc.cs304.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;

import ca.ubc.cs304.delegates.MakeReservationDelegate;

/**
 * The class is only responsible for displaying and handling the login GUI.
 */
public class MakeReservationWindow extends JFrame implements ActionListener {
    private static final int TEXT_FIELD_WIDTH = 10;
    private JButton ok;
    private JButton back;
    private JTextField locationField;
    private JTextField vtField;
    private JTextField cityField;
    private String license;
    private JSpinner timeSpinnerFrom;
    private JSpinner timeSpinnerTo;

    // delegate
    private MakeReservationDelegate makeReservationDelegate;

    public MakeReservationWindow() {
        super("reserve your dream car");
    }

    public void showMenu(MakeReservationDelegate makeReservationDelegate, String licence) {
        this.makeReservationDelegate = makeReservationDelegate;
        this.license = licence;
        JLabel locationLabel = new JLabel("Location:");
        JLabel cityLabel = new JLabel("City:");
        JLabel vtLabel = new JLabel("Vehicle Type:");
        JLabel fromTimeLabel = new JLabel("Pickup Time:");
        JLabel toTimeLabel = new JLabel("Return Time:");

        locationField = new JTextField(TEXT_FIELD_WIDTH);
        vtField = new JTextField(TEXT_FIELD_WIDTH);
        cityField = new JTextField(TEXT_FIELD_WIDTH);

        ok = new JButton("submit");
        back = new JButton("back");

        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);

        // layout components using the GridBag layout manager
        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        contentPane.setLayout(gb);
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        // place the location label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(10, 10, 5, 0);
        gb.setConstraints(locationLabel, c);
        contentPane.add(locationLabel);
        // place the text field for the location
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(10, 0, 5, 10);
        gb.setConstraints(locationField, c);
        contentPane.add(locationField);

        // place city label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(0, 10, 10, 0);
        gb.setConstraints(cityLabel, c);
        contentPane.add(cityLabel);

        // place the city field
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(0, 0, 10, 10);
        gb.setConstraints(cityField, c);
        contentPane.add(cityField);

        // place vtype label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(0, 10, 10, 0);
        gb.setConstraints(vtLabel, c);
        contentPane.add(vtLabel);

        // place the vtype field
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(0, 0, 10, 10);
        gb.setConstraints(vtField, c);
        contentPane.add(vtField);

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


        // place the login button
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(5, 10, 10, 10);
        c.anchor = GridBagConstraints.CENTER;
        gb.setConstraints(ok, c);
        contentPane.add(ok);

        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(0, 10, 10, 10);
        c.anchor = GridBagConstraints.CENTER;
        gb.setConstraints(back, c);
        contentPane.add(back);

        // register login button with action event handler
        ok.addActionListener(this);
        back.addActionListener(this);

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
        locationField.requestFocus();
    }

    /**
     * ActionListener Methods
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ok) {
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            DateFormat tf = new SimpleDateFormat("HH:mm");
            Date fromDate = (Date) timeSpinnerFrom.getValue();
            Date toDate = (Date) timeSpinnerTo.getValue();
            String fromDateString = df.format(fromDate);
            String fromTimeString = tf.format(fromDate);
            String toDateString = df.format(toDate);
            String toTimeString = tf.format(toDate);
            System.out.println(fromDateString+"\n");
            System.out.println(fromTimeString+"\n");
            System.out.println(toDateString+"\n");
            System.out.println(toTimeString+"\n");
            int reservationNum = (int) (Math.random());
            //get time
            makeReservationDelegate.createReservation(license, locationField.getText(), cityField.getText(), vtField.getText(), fromDateString, fromTimeString, toDateString, toTimeString, reservationNum);
        } else if (e.getSource() == back) {
            makeReservationDelegate.returnToCustomer();
        }
    }
}
