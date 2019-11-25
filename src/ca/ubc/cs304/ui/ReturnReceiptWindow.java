package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.ReturnVehicleDelegate;
import ca.ubc.cs304.model.RentalCost;
import ca.ubc.cs304.model.Return;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ReturnReceiptWindow extends Window implements ActionListener {
    private ReturnVehicleDelegate returnVehicleDelegate = null;

    private JButton backToClerkMenu;

    public ReturnReceiptWindow() {
        super("Return Receipt Information");

        backToClerkMenu = new JButton("Back");
    }

    public void showMenu(ReturnVehicleDelegate returnVehicleDelegate, Pair<Return, RentalCost> returnPair) {
        this.returnVehicleDelegate = returnVehicleDelegate;

        Return returnObj = returnPair.getKey();
        RentalCost rentalCost = returnPair.getValue();

        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);

        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        contentPane.setLayout(gb);
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        long totalWeeklyRate = rentalCost.getWeekInsuranceValue() + rentalCost.getWeekValue();
        long totalDailyRate = rentalCost.getDayInsuranceValue() + rentalCost.getDayValue();
        long totalHourlyRate = rentalCost.getHourInsuranceValue() + rentalCost.getHourValue();
        JLabel weeksRented = new JLabel("Weeks rented: " + rentalCost.getWeeks() + " at $" + totalWeeklyRate + "/week");
        JLabel daysRented = new JLabel("Days rented: " + rentalCost.getDaysLeft() + " at $" + totalDailyRate + "/day");
        JLabel hoursRented = new JLabel("Hours rented: " + rentalCost.getHoursLeft() + " at $" + totalHourlyRate + "/hour");
        JLabel costLabel = new JLabel("Total Cost: $" + returnObj.getValue());
        JLabel returnDateLabel = new JLabel("Date: " + returnObj.getReturnDate());
        placeLabelRemainder(weeksRented, contentPane, gb, c);
        placeLabelRemainder(daysRented, contentPane, gb, c);
        placeLabelRemainder(hoursRented, contentPane, gb, c);
        placeLabelRemainder(costLabel, contentPane, gb, c);
        placeLabelRemainder(returnDateLabel, contentPane, gb, c);

        List<JButton> buttons = new ArrayList<>();
        buttons.add(backToClerkMenu);
        PanelConstraints setConstraints = (JButton button) -> { setButtonConstraints(gb, c, button); };
        new Panel(buttons, this, this, contentPane, gb, setConstraints);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backToClerkMenu) {
            returnVehicleDelegate.backToClerk();
        }
    }
}
