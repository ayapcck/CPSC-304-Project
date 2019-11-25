package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.ReturnVehicleDelegate;
import ca.ubc.cs304.model.Return;

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

    public void showMenu(ReturnVehicleDelegate returnVehicleDelegate, Return returnObj) {
        this.returnVehicleDelegate = returnVehicleDelegate;

        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);

        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        contentPane.setLayout(gb);
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel costLabel = new JLabel("Cost: $" + returnObj.getValue());
        JLabel returnDateLabel = new JLabel("Date: " + returnObj.getReturnDate());
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
