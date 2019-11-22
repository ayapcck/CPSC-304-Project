package ca.ubc.cs304.delegates;

import java.sql.Date;

public interface ViewVehiclesResultDelegate {
    void showDetailCountResult(int count, String carType, String location, String city,
                               Date fromDate, Date toDate);
    void backToCustomer();
}
