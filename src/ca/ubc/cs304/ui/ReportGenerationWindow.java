package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.ClerkTransactionDelegate;
import ca.ubc.cs304.delegates.ReportGenerationDelegate;
import org.jdatepicker.impl.JDatePickerImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ReportGenerationWindow extends Window implements ActionListener {
    ReportGenerationDelegate reportGenerationDelegate = null;

    private JButton submit;
    private JButton backToClerkWindow;
    private JCheckBox companyWideReport;
    private JCheckBox reportForRental;
    private JDatePickerImpl datePicker;
    private JTextField cityField;
    private JTextField locationField;

    public ReportGenerationWindow() {
        super("Which type of report?");

        submit = new JButton("Submit");
        backToClerkWindow = new JButton("Back");
        companyWideReport = new JCheckBox();
        reportForRental = new JCheckBox();
        cityField = new JTextField(TEXT_FIELD_WIDTH);
        locationField = new JTextField(TEXT_FIELD_WIDTH);
    }

    public void showMenu(ReportGenerationDelegate reportGenerationDelegate) {
        this.reportGenerationDelegate = reportGenerationDelegate;

        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);

        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        contentPane.setLayout(gb);
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel companyWideReportLabel = new JLabel("Company wide report: ");
        JLabel rentalReportLabel = new JLabel("Report for Rentals (default is returns): ");
        contentPane.add(companyWideReportLabel);
        c.gridwidth = GridBagConstraints.REMAINDER;
        gb.setConstraints(companyWideReport, c);
        contentPane.add(companyWideReport);
        contentPane.add(rentalReportLabel);
        gb.setConstraints(reportForRental, c);
        contentPane.add(reportForRental);

        JLabel date = new JLabel("Date:");
        placeLabel(date, contentPane, gb, c, 0, 10);
        datePicker = placeDateField(contentPane, gb, c, new Insets(0, 0, 10, 10));

        placeFieldAndLabel("City", cityField, contentPane, gb, c);
        placeFieldAndLabel("Location", locationField, contentPane, gb, c);

        List<JButton> buttons = new ArrayList<>();
        buttons.add(submit);
        buttons.add(backToClerkWindow);
        PanelConstraints setConstraints = (JButton button) -> { setButtonConstraints(gb, c, button); };
        new Panel(buttons, this, this, contentPane, gb, setConstraints);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {
            java.util.Date dateFromWindow = (java.util.Date) datePicker.getModel().getValue();
            Date date = new java.sql.Date(dateFromWindow.getTime());
            if (companyWideReport.isSelected()) {
                if (reportForRental.isSelected()) {
                    reportGenerationDelegate.dailyReportsRentalsWholeCompany(date);
                } else {
                    reportGenerationDelegate.dailyReturnReportsWholeCompany(date);
                }
            } else {
                String city = cityField.getText();
                String location = locationField.getText();
                if (reportForRental.isSelected()) {
                    reportGenerationDelegate.dailyRentalReportsOneBranch(date, location, city);
                } else {
                    reportGenerationDelegate.dailyReturnReportOneBranch(date, location, city);
                }
            }
        } else if (e.getSource() == backToClerkWindow) {
            reportGenerationDelegate.backToClerkMenu();
        }
    }
}
