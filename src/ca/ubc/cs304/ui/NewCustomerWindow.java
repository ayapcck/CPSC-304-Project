package ca.ubc.cs304.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import ca.ubc.cs304.delegates.NewCustomerDelegate;

public class NewCustomerWindow extends Window implements ActionListener {
    // delegate
    private NewCustomerDelegate newCustomerDelegate;

    private JButton submit;
    private JButton backToCustomer;
    private JTextField nameField;
    private JTextField phoneField;
    private JTextField addrField;
    private JTextField licenseField;

    public NewCustomerWindow() {
        super("New Customer Registration");

        nameField = new JTextField(TEXT_FIELD_WIDTH);
        phoneField = new JTextField(TEXT_FIELD_WIDTH);
        addrField = new JTextField(TEXT_FIELD_WIDTH);
        licenseField = new JTextField(TEXT_FIELD_WIDTH);

        submit = new JButton("Submit");
        backToCustomer = new JButton("Back");
    }

    public void showMenu(NewCustomerDelegate newCustomerDelegate) {
        this.newCustomerDelegate = newCustomerDelegate;

        JLabel nameLabel = new JLabel("Name: ");
        JLabel phoneLabel = new JLabel("Phone number: ");
        JLabel addrLabel = new JLabel("Address: ");
        JLabel dLicenseLabel = new JLabel("Driver's license: ");

        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);

        // layout components using the GridBag layout manager
        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        contentPane.setLayout(gb);
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // place the name label and field
        placeLabel(nameLabel, contentPane, gb, c, 10, 5);
        placeTextField(nameField, contentPane, gb, c, TEXT_FIELD_INSET);

        // place phone label and field
        placeLabel(phoneLabel, contentPane, gb, c, 0, 10);
        placeTextField(phoneField, contentPane, gb, c, new Insets(0, 0, 10, 10));

        // place licence label and field
        placeLabel(dLicenseLabel, contentPane, gb, c, 0, 10);
        placeTextField(licenseField, contentPane, gb, c, new Insets(0, 0, 10, 10));

        // place addr label and field
        placeLabel(addrLabel, contentPane, gb, c, 0, 10);
        placeTextField(addrField, contentPane, gb, c, new Insets(0, 0, 10, 10));

        List<JButton> buttons = new ArrayList<>();
        buttons.add(submit);
        buttons.add(backToCustomer);
        PanelConstraints setConstraints = (JButton button) -> { setButtonConstraints(gb, c, button); };
        new Panel(buttons, this, this, contentPane, gb, setConstraints);

        // place the cursor in the text field for the username
        nameField.requestFocus();
    }

    /**
     * ActionListener Methods
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {
            String name = nameField.getText();
            String phone = phoneField.getText();
            String licence = licenseField.getText();
            String addr = addrField.getText();
            newCustomerDelegate.finishRegistration(name, phone, licence, addr);
        } else if (e.getSource() == backToCustomer) {
            newCustomerDelegate.returnToCustomer();
        }
    }
}
