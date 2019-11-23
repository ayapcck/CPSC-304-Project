package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.ClerkTransactionDelegate;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ClerkWindow extends JFrame implements ActionListener {
    private JButton mainMenu;
    private JButton rentVehicle;
    private JButton returnVehicle;
    private ClerkTransactionDelegate clerkTransactionDelegate = null;

    public ClerkWindow() {
        super("Choose a transactions");

        mainMenu = new JButton("Main Menu");
        rentVehicle = new JButton("Rent out a vehicle");
        returnVehicle = new JButton("Process return");
    }

    public void showMenu(ClerkTransactionDelegate clerkTransactionDelegate) {
        this.clerkTransactionDelegate = clerkTransactionDelegate;

        List<JButton> buttons = new ArrayList<>();
        buttons.add(mainMenu);
        buttons.add(rentVehicle);
        buttons.add(returnVehicle);
        new Panel(buttons, this, this);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == mainMenu) {
            clerkTransactionDelegate.mainMenu();
        } else if (actionEvent.getSource() == rentVehicle) {
            clerkTransactionDelegate.rentVehicle();
        } else if (actionEvent.getSource() == returnVehicle) {
            clerkTransactionDelegate.returnVehicle();
        }
    }
}
