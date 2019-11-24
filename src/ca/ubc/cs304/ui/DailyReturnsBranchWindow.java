package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.ReportGenerationDelegate;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DailyReturnsBranchWindow extends Window implements ActionListener {
    private ReportGenerationDelegate reportGenerationDelegate = null;

    private JButton backToClerkMenu;

    public DailyReturnsBranchWindow() {
        super("Daily Returns for Branch");

        backToClerkMenu = new JButton("Back");
    }

    public void showMenu(ReportGenerationDelegate reportGenerationDelegate,
                         JTable returnForOneBranch, JTable returnTotalRevAndReturn) {
        this.reportGenerationDelegate = reportGenerationDelegate;

        JFrame frame = new JFrame();
        JPanel contentPane = new JPanel();
        JPanel table1Panel = new JPanel();
        table1Panel.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),
                "Total Returns",
                TitledBorder.CENTER,
                TitledBorder.TOP));
        JPanel table2Panel = new JPanel();
        table2Panel.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),
                "Total Returns and Revenue",
                TitledBorder.CENTER,
                TitledBorder.TOP));

        GridBagConstraints c = new GridBagConstraints();
        GridBagLayout gb = new GridBagLayout();
        this.setContentPane(contentPane);

        JScrollPane returnForOneBranchTable = new JScrollPane(returnForOneBranch);
        JScrollPane returnTotalRevAndReturnTable = new JScrollPane(returnTotalRevAndReturn);

        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(5, 10, 10, 10);
        c.anchor = GridBagConstraints.CENTER;
        gb.setConstraints(returnForOneBranchTable, c);
        table1Panel.add(returnForOneBranchTable);
        gb.setConstraints(returnTotalRevAndReturnTable, c);
        table2Panel.add(returnTotalRevAndReturnTable);

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
        if (e.getSource() == backToClerkMenu) {
            reportGenerationDelegate.backToClerkMenu();
        }
    }
}
