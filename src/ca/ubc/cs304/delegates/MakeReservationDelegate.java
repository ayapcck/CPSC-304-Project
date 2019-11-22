package ca.ubc.cs304.delegates;

public interface MakeReservationDelegate {
    void createReservation(String license, String location, String city,
                           String vtName, String fromDate, String fromTime,
                           String toDate, String toTime, int ReservationNum);
    void returnToCustomer();
}
