package ca.ubc.cs304.delegates;


import java.sql.Date;

public interface ProcessViewDelegate {
    void processView(String carType, String location, String city, Date fromDate, Date toDate);
    void showDetailCountResult(String carType, String location, String city, Date fromDate, Date toDate);
}
