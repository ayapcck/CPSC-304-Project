package ca.ubc.cs304.delegates;

import ca.ubc.cs304.model.Branch;
import ca.ubc.cs304.model.Reservation;

public interface MakeReservationDelegate {
    void createReservation(Reservation reservation, Branch branch);
    void returnToCustomer();
}
