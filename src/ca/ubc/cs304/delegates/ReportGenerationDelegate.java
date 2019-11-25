package ca.ubc.cs304.delegates;

import java.sql.Date;

public interface ReportGenerationDelegate {
    void dailyReportsRentalsWholeCompany(String date);
    void dailyRentalReportsOneBranch(String date, String location, String city);
    void dailyReturnReportsWholeCompany(String date);
    void dailyReturnReportOneBranch(String date, String location, String city);
    void backToClerkMenu();
}
