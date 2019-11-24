package ca.ubc.cs304.delegates;

import ca.ubc.cs304.model.Branch;
import ca.ubc.cs304.model.Reservation;

public interface RentVehicleDelegate {
    void backToClerk();
    void navToRentalWithReservation();
    void navToRentalNoReservation();
    void rentWithReservation(int confirmationNumber, String cardName, int cardNumber);
    void rentWithoutReservation(Reservation reservation, Branch branch, String cardName, int cardNumber);
}
