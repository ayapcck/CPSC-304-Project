package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.ReturnVehicleDelegate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ReturnVehicleWindow extends Window implements ActionListener {
    private ReturnVehicleDelegate returnVehicleDelegate = null;

    private JButton submit;
    private JButton backToClerkMenu;
    private JTextField returnIdField;

    public ReturnVehicleWindow() {
        super("Return Vehicle");

        submit = new JButton("Submit");
        backToClerkMenu = new JButton("Back");

        returnIdField = new JTextField(TEXT_FIELD_WIDTH);
    }

    public void showMenu(ReturnVehicleDelegate returnVehicleDelegate) {
        this.returnVehicleDelegate = returnVehicleDelegate;

        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);

        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        contentPane.setLayout(gb);
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        placeFieldAndLabel("Return ID", returnIdField, contentPane, gb, c);

        List<JButton> buttons = new ArrayList<>();
        buttons.add(submit);
        buttons.add(backToClerkMenu);
        PanelConstraints setConstraints = (JButton button) -> { setButtonConstraints(gb, c, button); };
        new Panel(buttons, this, this, contentPane, gb, setConstraints);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {
            returnVehicleDelegate.returnVehicle(Integer.parseInt(returnIdField.getText()));
        } else if (e.getSource() == backToClerkMenu) {
            returnVehicleDelegate.backToClerk();
        }
    }
}
