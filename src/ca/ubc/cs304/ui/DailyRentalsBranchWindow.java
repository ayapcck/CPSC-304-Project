package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.ReportGenerationDelegate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DailyRentalsBranchWindow extends Window implements ActionListener {
    private ReportGenerationDelegate reportGenerationDelegate = null;

    private JButton backToClerkMenu;

    public DailyRentalsBranchWindow() {
        super("Daily Rentals for Branch");

        backToClerkMenu = new JButton("Back");
    }

    public void showMenu(ReportGenerationDelegate reportGenerationDelegate,
                         int totalRentals, JTable rentalsPerVehicleType) {
        this.reportGenerationDelegate = reportGenerationDelegate;

        JPanel contentPane = new JPanel();
        GridBagConstraints c = new GridBagConstraints();
        GridBagLayout gb = new GridBagLayout();
        this.setContentPane(contentPane);

        JLabel totalRentalsLabel = new JLabel("Total Rentals: " + totalRentals);
        contentPane.add(totalRentalsLabel);

        JScrollPane jsp = new JScrollPane(rentalsPerVehicleType);

        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(5, 10, 10, 10);
        c.anchor = GridBagConstraints.CENTER;
        gb.setConstraints(jsp, c);
        contentPane.add(jsp);
        contentPane.add(backToClerkMenu);
        backToClerkMenu.addActionListener(this);
        // anonymous inner class for closing the window
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.pack();
        Dimension d = this.getToolkit().getScreenSize();
        Rectangle r = this.getBounds();
        this.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );
        // make the window visible
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backToClerkMenu) {
            reportGenerationDelegate.backToClerkMenu();
        }
    }
}
