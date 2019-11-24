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
    private JButton dailyRentalReportsWholeCompany;
    private JButton dailyRentalReportsSingleBranch;
    private JButton dailyReturnReportsWholeCompany;
    private JButton dailyReturReportOneBranch;
    private ClerkTransactionDelegate clerkTransactionDelegate = null;

    public ClerkWindow() {
        super("Choose a transactions");

        mainMenu = new JButton("Main Menu");
        rentVehicle = new JButton("Rent a vehicle");
        returnVehicle = new JButton("Process return");
        dailyRentalReportsWholeCompany = new JButton("Daily Rental Reports Whole Company");
        dailyRentalReportsSingleBranch = new JButton("Daily Rental Reports One Branch");
        dailyReturnReportsWholeCompany = new JButton("Daily Return Reports Whole Company");
        dailyReturReportOneBranch = new JButton("Daily Return Report One Branch");
    }
// pushing
    public void showMenu(ClerkTransactionDelegate clerkTransactionDelegate) {
        this.clerkTransactionDelegate = clerkTransactionDelegate;

        List<JButton> buttons = new ArrayList<>();
        buttons.add(mainMenu);
        buttons.add(rentVehicle);
        buttons.add(returnVehicle);
        buttons.add(dailyRentalReportsWholeCompany);
        buttons.add(dailyRentalReportsSingleBranch);
        buttons.add(dailyReturnReportsWholeCompany);
        buttons.add(dailyRentalReportsWholeCompany);
        new Panel(buttons, this, this);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == mainMenu) {
            clerkTransactionDelegate.mainMenu();
        } else if (actionEvent.getSource() == rentVehicle) {
            clerkTransactionDelegate.navToRentalWindow();
        } else if (actionEvent.getSource() == returnVehicle) {
            clerkTransactionDelegate.returnVehicle();
        } else if (actionEvent.getSource() == dailyRentalReportsWholeCompany) {
            clerkTransactionDelegate.dailyReportsRentalsWholeCompany();
        } else if (actionEvent.getSource() == dailyRentalReportsSingleBranch) {
            clerkTransactionDelegate.dailyRentalReportsSingleBranch();
        } else if (actionEvent.getSource() == dailyReturnReportsWholeCompany) {
            clerkTransactionDelegate.dailyReturnReportsWholeCompany();
        } else if (actionEvent.getSource() == dailyReturReportOneBranch) {
            clerkTransactionDelegate.dailyReturnReportOneCompany();
        }
    }
}
