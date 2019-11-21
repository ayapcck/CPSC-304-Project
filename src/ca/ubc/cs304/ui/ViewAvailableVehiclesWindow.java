package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.ProcessViewDelegate;
import ca.ubc.cs304.util.DateLabelFormatter;
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
public class ViewAvailableVehiclesWindow extends JFrame implements ActionListener {
    private static final int TEXT_FIELD_WIDTH = 10;
    private ProcessViewDelegate processViewDelegate = null;
    private String locationData = "";
    private String carTypeData = "";
    private String cityData = "";
    private JDatePickerImpl datePicker;
    private JDatePickerImpl datePickerTo;
    private JButton submit;
    private JButton mainMenu;
    public ViewAvailableVehiclesWindow() {
        super("Please vehicle information:");
    }

    public void showMenu(ProcessViewDelegate processViewDelegate) {
        this.processViewDelegate = processViewDelegate;
        submit = new JButton("Submit");

        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);

        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        contentPane.setLayout(gb);
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // place car type label and field
        JLabel carType = new JLabel("Car Type:");
        JTextField carTypeField = new JTextField(TEXT_FIELD_WIDTH);
        placeLabel(carType, contentPane, gb, c, 10, 5);
        carTypeData = placeTextField(carTypeField, contentPane, gb, c, new Insets(10, 0, 5, 10));

        // place location label and field
        JLabel location = new JLabel("Location:");
        JTextField locationField = new JTextField(TEXT_FIELD_WIDTH);
        placeLabel(location, contentPane, gb, c, 0, 10);
        locationData = placeTextField(locationField, contentPane, gb, c, new Insets(10, 0, 5, 10));

        // place city label and field
        JLabel cityLabel = new JLabel("City:");
        JTextField cityField = new JTextField(TEXT_FIELD_WIDTH);
        placeLabel(cityLabel, contentPane, gb, c, 0, 10);
        cityData = placeTextField(cityField, contentPane, gb, c, new Insets(0, 0, 10, 10));

        // place fromDate label and field
        JLabel fromDate = new JLabel("from Date:");
        placeLabel(fromDate, contentPane, gb, c, 0, 10);
        datePicker = placeDateField(contentPane, gb, c, new Insets(0, 0, 10, 10));

        // place toDate label and field
        JLabel toDate = new JLabel("to Date:");
        placeLabel(toDate, contentPane, gb, c, 0, 10);
        datePickerTo = placeDateField(contentPane, gb, c, new Insets(0, 0, 10, 10));

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

    private JDatePickerImpl placeDateField(JPanel contentPane, GridBagLayout gb, GridBagConstraints c,
                                Insets insets) {
        UtilDateModel model2 = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanelTo = new JDatePanelImpl(model2, p);
        JDatePickerImpl picker = new JDatePickerImpl(datePanelTo, new DateLabelFormatter());
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = insets;
        gb.setConstraints(picker, c);
        contentPane.add(picker);
        return picker;
    }

    private String placeTextField(JTextField field, JPanel contentPane, GridBagLayout gb,
                                  GridBagConstraints c, Insets insets) {
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = insets;
        gb.setConstraints(field, c);
        contentPane.add(field);
        return field.getText();
    }

    private void placeLabel(JLabel label, JPanel contentPane, GridBagLayout gb, GridBagConstraints c, int i, int i2) {
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(i, 10, i2, 0);
        gb.setConstraints(label, c);
        contentPane.add(label);
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
