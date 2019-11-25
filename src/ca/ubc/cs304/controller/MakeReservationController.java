package ca.ubc.cs304.controller;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.delegates.MakeReservationDelegate;
import ca.ubc.cs304.model.Branch;
import ca.ubc.cs304.model.Reservation;
import ca.ubc.cs304.ui.CustomerWindow;
import ca.ubc.cs304.ui.ErrorWindow;
import ca.ubc.cs304.ui.MakeReservationWindow;

import javax.swing.*;
import java.util.ArrayList;

public class MakeReservationController implements MakeReservationDelegate {
    private DatabaseConnectionHandler dbHandler = null;
    private JFrame currentWindow = null;

    public MakeReservationController(JFrame currentWindow) {
        this.currentWindow = currentWindow;
        this.dbHandler = DatabaseConnectionHandler.getDBHandlerInstance();
    }

    @Override
    public void createReservation(Reservation reservation, Branch branch) {
        System.out.println("fromDate:"+reservation.getToTime());

        if (!checkDateIsValid(reservation.getFromDate(), reservation.getToDate(), reservation.getFromTime(), reservation.getToTime())) {
            currentWindow.dispose();
            ErrorWindow errorWindow = new ErrorWindow();
            errorWindow.infoBox("Sorry, time information is invalid", "Invalid Input!");
            MakeReservationWindow makeReservationWindow = new MakeReservationWindow();
            MakeReservationController makeReservationController = new MakeReservationController(makeReservationWindow);
            ArrayList<String> vtlist = dbHandler.findAllVT();
            ArrayList<String> locationList = dbHandler.findAllBranch();
            ArrayList<String> citylist = dbHandler.findAllCity();

            makeReservationWindow.showMenu(vtlist, locationList, citylist, makeReservationController, null);
        } else if (reservation.getdLicense().equals("")) {
            currentWindow.dispose();
            ErrorWindow errorWindow = new ErrorWindow();
            errorWindow.infoBox("Licence cannot be empty", "Missing licence plate!");
            MakeReservationWindow makeReservationWindow = new MakeReservationWindow();
            MakeReservationController makeReservationController = new MakeReservationController(makeReservationWindow);
            ArrayList<String> vtlist = dbHandler.findAllVT();
            ArrayList<String> locationList = dbHandler.findAllBranch();
            ArrayList<String> citylist = dbHandler.findAllCity();

            makeReservationWindow.showMenu(vtlist, locationList, citylist, makeReservationController, null);

        } else if (dbHandler.insertReservation(reservation, branch)) {
            currentWindow.dispose();
            MakeReservationWindow reservationConfirmWindow = new MakeReservationWindow();
            MakeReservationController customerController = new MakeReservationController(reservationConfirmWindow);
            reservationConfirmWindow.showConfirm(customerController, reservation, branch);
        } else {
            currentWindow.dispose();
            ErrorWindow errorWindow = new ErrorWindow();
            errorWindow.infoBox("Sorry, no vehicle matches your search", "No Available Vehicle");
            MakeReservationWindow makeReservationWindow = new MakeReservationWindow();
            MakeReservationController makeReservationController = new MakeReservationController(makeReservationWindow);
            ArrayList<String> vtlist = dbHandler.findAllVT();
            ArrayList<String> locationList = dbHandler.findAllBranch();
            ArrayList<String> citylist = dbHandler.findAllCity();

            makeReservationWindow.showMenu(vtlist, locationList, citylist, makeReservationController, null);

        }
    }

    @Override
    public void returnToCustomer() {
        currentWindow.dispose();
        CustomerWindow customerWindow = new CustomerWindow();
        CustomerController customerController = new CustomerController(customerWindow);
        customerWindow.showMenu(customerController);
    }

    public boolean checkDateIsValid(String fromDate, String toDate, String fromTime, String toTime) {
        int fromyear = Integer.parseInt(fromDate.split("-")[0]);
        int fromMonth = Integer.parseInt(fromDate.split("-")[1]);
        int fromDay = Integer.parseInt(fromDate.split("-")[2]);
        int toyear = Integer.parseInt(toDate.split("-")[0]);
        int toMonth = Integer.parseInt(toDate.split("-")[1]);
        int toDay = Integer.parseInt(toDate.split("-")[2]);
        int fromHour = Integer.parseInt(fromTime.split(":")[0]);
        int fromMin = Integer.parseInt(fromTime.split(":")[1]);
        int toHour = Integer.parseInt(toTime.split(":")[0]);
        int toMin = Integer.parseInt(toTime.split(":")[1]);

        if ((fromyear > toyear)) return false;
        if ((fromyear == toyear) && (fromMonth > toMonth)) return false;
        if ((fromyear == toyear) && (fromMonth == toMonth) && (fromDay > toDay)) return false;
        if (fromyear < 2019) return false;
        if ((fromyear == toyear) && (fromMonth == toMonth) && (fromDay == toDay) &&
                (fromHour > toHour)) return false;
        if ((fromyear == toyear) && (fromMonth == toMonth) && (fromDay == toDay) &&
                (fromHour == toHour) && (fromMin >= toMin)) return false;
        return true;
    }

}
