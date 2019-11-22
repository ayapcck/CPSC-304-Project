package ca.ubc.cs304.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import ca.ubc.cs304.delegates.LoginWindowDelegate;
import ca.ubc.cs304.delegates.NewCustomerDelegate;

public class NewCustomerWindow extends JFrame implements ActionListener {
    private static final int TEXT_FIELD_WIDTH = 10;
    private JButton ok;
    private JButton back;
    private JTextField nameField;
    private JTextField phoneField;
    private JTextField addrField;
    private JTextField licenseField;
    // delegate
    private NewCustomerDelegate newCustomerDelegate;

    public NewCustomerWindow() {
        super("New Customer Registration");
    }

    public void showMenu(NewCustomerDelegate newCustomerDelegate) {
        this.newCustomerDelegate = newCustomerDelegate;

        JLabel nameLabel = new JLabel("Name: ");
        JLabel phoneLabel = new JLabel("Phone number: ");
        JLabel addrLabel = new JLabel("Address: ");
        JLabel dLicenseLabel = new JLabel("Driver's license: ");

        nameField = new JTextField(TEXT_FIELD_WIDTH);
        phoneField = new JTextField(TEXT_FIELD_WIDTH);
        addrField = new JTextField(TEXT_FIELD_WIDTH);
        licenseField = new JTextField(TEXT_FIELD_WIDTH);

        ok = new JButton("Submit");
        back = new JButton("Back");

        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);

        // layout components using the GridBag layout manager
        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        contentPane.setLayout(gb);
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        // place the name label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(10, 10, 5, 0);
        gb.setConstraints(nameLabel, c);
        contentPane.add(nameLabel);
        // place the text field for the name
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(10, 0, 5, 10);
        gb.setConstraints(nameField, c);
        contentPane.add(nameField);

        // place phone label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(0, 10, 10, 0);
        gb.setConstraints(phoneLabel, c);
        contentPane.add(phoneLabel);

        // place the phone field
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(0, 0, 10, 10);
        gb.setConstraints(phoneField, c);
        contentPane.add(phoneField);

        // place licence label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(0, 10, 10, 0);
        gb.setConstraints(dLicenseLabel, c);
        contentPane.add(dLicenseLabel);

        // place the licence field
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(0, 0, 10, 10);
        gb.setConstraints(licenseField, c);
        contentPane.add(licenseField);

        // place addr label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(0, 10, 10, 0);
        gb.setConstraints(addrLabel, c);
        contentPane.add(addrLabel);

        // place the addr field
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(0, 0, 10, 10);
        gb.setConstraints(addrField, c);
        contentPane.add(addrField);

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
        nameField.requestFocus();
    }

    /**
     * ActionListener Methods
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ok) {
            String name = nameField.getText();
            String phone = phoneField.getText();
            String licence = licenseField.getText();
            String addr = addrField.getText();
            newCustomerDelegate.finishRegistration(name, phone, licence, addr);
        } else if (e.getSource() == back) {
            newCustomerDelegate.returnToCustomer();
        }
    }
}
