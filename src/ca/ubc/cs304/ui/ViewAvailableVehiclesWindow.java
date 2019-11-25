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
    private JComboBox vtBox;
    private JComboBox locationBox;
    private JComboBox cityBox;

    public ViewAvailableVehiclesWindow() {
        super("Please enter vehicle information:");

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
        placeLabel(carType, contentPane, gb, c, 10, 5);
//        placeTextField(carTypeField, contentPane, gb, c, TEXT_FIELD_INSET);
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
            String toDate = "";
            String fromDate = "";
            if ((toDateUtil != null) && (fromDateUtil != null)) {
                toDate = df.format(toDateUtil);
                fromDate = df.format(fromDateUtil);
            }
            String carTypeData = (String) vtBox.getSelectedItem();
            String cityData = (String) cityBox.getSelectedItem();
            String locationData = (String) locationBox.getSelectedItem();
            viewVehiclesDelegate.submit(carTypeData, locationData, cityData, fromDate, toDate);
        } else if (actionEvent.getSource() == mainMenu) {
            viewVehiclesDelegate.returnToCustomer();
        }
    }
}
