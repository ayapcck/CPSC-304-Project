package ca.ubc.cs304.delegates;

import java.sql.Date;

public interface ReportGenerationDelegate {
    void dailyReportsRentalsWholeCompany(Date date);
    void dailyRentalReportsOneBranch(Date date, String location, String city);
    void dailyReturnReportsWholeCompany(Date date);
    void dailyReturnReportOneBranch(Date date, String location, String city);
    void backToClerkMenu();
}
