package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.ProcessViewDelegate;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Properties;

import javax.swing.*;

/**
 * The class is only responsible for displaying and handling the login GUI.
 */
public class CustomerViewWindow extends JFrame implements ActionListener {
    private static final int TEXT_FIELD_WIDTH = 10;
    private ProcessViewDelegate processViewDelegate = null;
    private String locationData = "";
    private String carTypeData = "";
    private String cityData = "";
    private JDatePickerImpl datePicker;
    private JDatePickerImpl datePickerTo;
    private JButton submit;
    private JButton mainMenu;
    public CustomerViewWindow() {
        super("what do you want to see?");
    }

    public void showMenu(ProcessViewDelegate processViewDelegate) {
        this.processViewDelegate = processViewDelegate;
        JLabel carType = new JLabel("Car Type:");
        JLabel location = new JLabel("Location:");
        submit = new JButton("Submit");
        JTextField locationField = new JTextField(TEXT_FIELD_WIDTH);
        JTextField carTypeField = new JTextField(TEXT_FIELD_WIDTH);

        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);

        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        contentPane.setLayout(gb);
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // place car type label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(10, 10, 5, 0);
        gb.setConstraints(carType, c);
        contentPane.add(carType);

        // place the car type field
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(10, 0, 5, 10);
        gb.setConstraints(carTypeField, c);
        contentPane.add(carTypeField);
        carTypeData = carTypeField.getText();

        // place location label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(0, 10, 10, 0);
        gb.setConstraints(location, c);
        contentPane.add(location);

        // place the location field
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(0, 0, 10, 10);
        gb.setConstraints(locationField, c);
        contentPane.add(locationField);
        locationData = locationField.getText();

        // place city label
        JLabel cityLabel = new JLabel("City:");
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(0, 10, 10, 0);
        gb.setConstraints(cityLabel, c);
        contentPane.add(cityLabel);

        // place the city field
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(0, 0, 10, 10);
        JTextField cityField = new JTextField(TEXT_FIELD_WIDTH);
        gb.setConstraints(cityField, c);
        contentPane.add(cityField);
        cityData = cityField.getText();

        // place fromDate label
        JLabel fromDate = new JLabel("from Date:");
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(0, 10, 10, 0);
        gb.setConstraints(fromDate, c);
        contentPane.add(fromDate);

        // place fromDate field
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
         datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(0, 0, 10, 10);
        gb.setConstraints(datePicker, c);
        contentPane.add(datePicker);
        // place toDate label
        JLabel toDate = new JLabel("to Date:");
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(0, 10, 10, 0);
        gb.setConstraints(toDate, c);
        contentPane.add(toDate);
        // place toDate field
        UtilDateModel model2 = new UtilDateModel();
        Properties p2 = new Properties();
        p2.put("text.today", "Today");
        p2.put("text.month", "Month");
        p2.put("text.year", "Year");
        JDatePanelImpl datePanelTo = new JDatePanelImpl(model2, p2);
        datePickerTo = new JDatePickerImpl(datePanelTo, new DateLabelFormatter());
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(0, 0, 10, 10);
        gb.setConstraints(datePickerTo, c);
        contentPane.add(datePickerTo);

        // place submit button
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(5, 10, 10, 10);
        c.anchor = GridBagConstraints.CENTER;
        gb.setConstraints(submit, c);
        contentPane.add(submit);
        // place main menu button
        mainMenu = new JButton("Back");
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(5, 10, 10, 10);
        c.anchor = GridBagConstraints.CENTER;
        gb.setConstraints(mainMenu, c);
        contentPane.add(mainMenu);

        // register login button with action event handler
        submit.addActionListener(this);
        mainMenu.addActionListener(this);
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
        if (actionEvent.getSource() == submit) {
            java.util.Date fromDateUtil = (java.util.Date) datePicker.getModel().getValue();
            java.sql.Date fromDateField = new java.sql.Date(fromDateUtil.getTime());
            java.util.Date toDateUtil = (java.util.Date) datePickerTo.getModel().getValue();
            java.sql.Date toDateField = new java.sql.Date(toDateUtil.getTime());
            processViewDelegate.processView(carTypeData, locationData, cityData, fromDateField, toDateField);
        } else if (actionEvent.getSource() == mainMenu) {
            processViewDelegate.backToPrevious();
        }
    }
}
