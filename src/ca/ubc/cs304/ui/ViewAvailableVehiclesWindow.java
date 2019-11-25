package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.ViewVehiclesDelegate;
import ca.ubc.cs304.util.DateLabelFormatter;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.*;

/**
 * The class is only responsible for displaying and handling the login GUI.
 */
public class ViewAvailableVehiclesWindow extends Window implements ActionListener {
    private ViewVehiclesDelegate viewVehiclesDelegate = null;
    private JButton submit;
    private JButton mainMenu;
    private JDatePickerImpl datePicker;
    private JDatePickerImpl datePickerTo;
    private JTextField carTypeField;
    private JTextField cityField;
    private JTextField locationField;

    public ViewAvailableVehiclesWindow() {
        super("Please vehicle information:");

        mainMenu = new JButton("Back");
        submit = new JButton("Submit");
    }

    public void showMenu(ViewVehiclesDelegate viewVehiclesDelegate) {
        this.viewVehiclesDelegate = viewVehiclesDelegate;

        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);

        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        contentPane.setLayout(gb);
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // place car type label and field
        JLabel carType = new JLabel("Car Type:");
        carTypeField = new JTextField(TEXT_FIELD_WIDTH);
        placeLabel(carType, contentPane, gb, c, 10, 5);
        placeTextField(carTypeField, contentPane, gb, c, TEXT_FIELD_INSET);

        // place location label and field
        JLabel location = new JLabel("Location:");
        locationField = new JTextField(TEXT_FIELD_WIDTH);
        placeLabel(location, contentPane, gb, c, 0, 10);
        placeTextField(locationField, contentPane, gb, c, TEXT_FIELD_INSET);

        // place city label and field
        JLabel cityLabel = new JLabel("City:");
        cityField = new JTextField(TEXT_FIELD_WIDTH);
        placeLabel(cityLabel, contentPane, gb, c, 0, 10);
        placeTextField(cityField, contentPane, gb, c, new Insets(0, 0, 10, 10));

        // place fromDate label and field
        JLabel fromDate = new JLabel("from Date:");
        placeLabel(fromDate, contentPane, gb, c, 0, 10);
        datePicker = placeDateField(contentPane, gb, c, new Insets(0, 0, 10, 10));
//
        // place toDate label and field
        JLabel toDate = new JLabel("to Date:");
        placeLabel(toDate, contentPane, gb, c, 0, 10);
        datePickerTo = placeDateField(contentPane, gb, c, new Insets(0, 0, 10, 10));

        List<JButton> buttons = new ArrayList<>();
        buttons.add(submit);
        buttons.add(mainMenu);
        PanelConstraints setConstraints = (JButton button) -> { setButtonConstraints(gb, c, button); };
        new Panel(buttons, this, this, contentPane, gb, setConstraints);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == submit) {
            java.util.Date fromDateUtil = (java.util.Date) datePicker.getModel().getValue();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date toDateUtil = (java.util.Date) datePickerTo.getModel().getValue();
            String toDate = df.format(toDateUtil);
            String fromDate = df.format(fromDateUtil);
            String carTypeData = carTypeField.getText();
            String cityData = cityField.getText();
            String locationData = locationField.getText();
            viewVehiclesDelegate.submit(carTypeData, locationData, cityData, fromDate, toDate);
        } else if (actionEvent.getSource() == mainMenu) {
            viewVehiclesDelegate.returnToCustomer();
        }
    }
}
