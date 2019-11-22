package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.ClerkTransactionDelegate;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ClerkWindow extends JFrame implements ActionListener {
    private JButton mainMenu;
    private ClerkTransactionDelegate clerkTransactionDelegate = null;

    public ClerkWindow() {
        super("Choose a transactions");

        // TODO: add rest of buttons
        mainMenu = new JButton("Main Menu");
    }

    public void showMenu(ClerkTransactionDelegate clerkTransactionDelegate) {
        this.clerkTransactionDelegate = clerkTransactionDelegate;

        List<JButton> buttons = new ArrayList<>();
        buttons.add(mainMenu);
        new Panel(buttons, this, this);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == mainMenu) {
            clerkTransactionDelegate.mainMenu();
        }
    }
}
