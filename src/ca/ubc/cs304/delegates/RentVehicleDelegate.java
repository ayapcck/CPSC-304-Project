package ca.ubc.cs304.delegates;

public interface RentVehicleDelegate {
    void backToClerk();
    void navToRentalWithReservation();
    void navToRentalNoReservation();
    void rentWithReservation(int confirmationNumber, String cardName, int cardNumber);
    void rentWithoutReservation();
}
