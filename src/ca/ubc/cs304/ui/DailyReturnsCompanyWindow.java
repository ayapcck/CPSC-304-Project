package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.ReportGenerationDelegate;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DailyReturnsCompanyWindow extends Window implements ActionListener {
    private ReportGenerationDelegate reportGenerationDelegate = null;

    private JButton backToClerkMenu;

    public DailyReturnsCompanyWindow() {
        super("Daily Returns for Company");

        backToClerkMenu = new JButton("Back");
    }

    public void showMenu(ReportGenerationDelegate reportGenerationDelegate,
                         int totalRevenue, JTable totalReturnsByVt, JTable totalReturnsPerBranch) {
        this.reportGenerationDelegate = reportGenerationDelegate;

        JPanel contentPane = new JPanel();
        JPanel table1Panel = new JPanel();
        table1Panel.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),
                "Total Returns per Branch",
                TitledBorder.CENTER,
                TitledBorder.TOP));
        JPanel table2Panel = new JPanel();
        table2Panel.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),
                "Total Returns per Vehicle Type",
                TitledBorder.CENTER,
                TitledBorder.TOP));

        GridBagConstraints c = new GridBagConstraints();
        GridBagLayout gb = new GridBagLayout();
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(5, 10, 10, 10);
        c.anchor = GridBagConstraints.CENTER;

        this.setContentPane(contentPane);

        JLabel totalRentalsLabel = new JLabel("Total Revenue: " + totalRevenue);
        contentPane.add(totalRentalsLabel);

        JScrollPane totalRentalsPerBranchTable = new JScrollPane(totalReturnsPerBranch);
        gb.setConstraints(totalRentalsPerBranchTable, c);
        table1Panel.add(totalRentalsPerBranchTable);

        JScrollPane rentalsPerVehicleTypeTable = new JScrollPane(totalReturnsByVt);
        gb.setConstraints(rentalsPerVehicleTypeTable, c);
        table2Panel.add(rentalsPerVehicleTypeTable);

        contentPane.add(table1Panel);
        contentPane.add(table2Panel);

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

    }
}
