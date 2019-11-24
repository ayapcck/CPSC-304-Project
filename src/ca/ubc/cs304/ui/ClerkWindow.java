package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.ClerkTransactionDelegate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ClerkWindow extends Window implements ActionListener {
    private JButton mainMenu;
    private JButton rentVehicle;
    private JButton returnVehicle;
    private JButton navToReportGenerationWindow;
    private ClerkTransactionDelegate clerkTransactionDelegate = null;

    public ClerkWindow() {
        super("Choose a transactions");

        mainMenu = new JButton("Main Menu");
        rentVehicle = new JButton("Rent a vehicle");
        returnVehicle = new JButton("Process return");
        navToReportGenerationWindow = new JButton("Generate a report");
    }
// pushing
    public void showMenu(ClerkTransactionDelegate clerkTransactionDelegate) {
        this.clerkTransactionDelegate = clerkTransactionDelegate;
        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);

        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        List<JButton> buttons = new ArrayList<>();
        buttons.add(rentVehicle);
        buttons.add(returnVehicle);
        buttons.add(navToReportGenerationWindow);
        buttons.add(mainMenu);
        PanelConstraints setConstraints = (JButton button) -> { setButtonConstraints(gb, c, button); };
        new Panel(buttons, this, this, contentPane, gb, setConstraints);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mainMenu) {
            clerkTransactionDelegate.mainMenu();
        } else if (e.getSource() == rentVehicle) {
            clerkTransactionDelegate.navToRentalWindow();
        } else if (e.getSource() == returnVehicle) {
            clerkTransactionDelegate.returnVehicle();
        } else if (e.getSource() == navToReportGenerationWindow) {
            clerkTransactionDelegate.navToGenerateReportWindow();
        }
    }
}
