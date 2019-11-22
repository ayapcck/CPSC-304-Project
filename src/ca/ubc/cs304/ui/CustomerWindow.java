package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.CustomerTransactionDelegate;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * The class is only responsible for displaying and handling the login GUI.
 */
public class CustomerWindow extends JFrame implements ActionListener {
    private JButton view;
    private JButton reserve;
    private JButton back;
    private CustomerTransactionDelegate customerTransactionDelegate = null;

    public CustomerWindow() {
        super("Choose a transaction:");

        view = new JButton("View Available Vehicles");
        reserve = new JButton("Reserve Vehicle");
        back = new JButton("Back");
    }

    public void showMenu(CustomerTransactionDelegate customerTransactionDelegate) {
        this.customerTransactionDelegate = customerTransactionDelegate;

        List<JButton> buttons = new ArrayList<>();
        buttons.add(view);
        buttons.add(reserve);
        buttons.add(back);
        new Panel(buttons, this, this);
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == view) {
            // TODO: finish
        } else if (actionEvent.getSource() == back) {
            customerTransactionDelegate.mainMenu();
        } else if (actionEvent.getSource() == reserve) {
            customerTransactionDelegate.makeReservation();
        }
    }
}
