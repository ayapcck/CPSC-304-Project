package ca.ubc.cs304.delegates;

import ca.ubc.cs304.ui.TerminalTransactions;

import java.sql.Date;
import java.sql.Time;

/**
 * This interface uses the delegation design pattern where instead of having
 * the LoginWindow class try to do everything, it will only focus on
 * handling the UI. The actual logic/operation will be delegated to the controller
 * class (in this case Bank).
 * 
 * LoginWindow calls the methods that we have listed below but
 * Bank is the actual class that will implement the methods.
 */
public interface LoginWindowDelegate {
<<<<<<< HEAD
    void login(String username, String password);
     void addRequiredTablesAndData();
     void dropRequiredTables();
     void setupDatabase();
     void viewAllTables();
     void backToMain();
     void newCusReserve();
     void newCusRigDone(String name, String phone, String licence, String addr);
     void oldCusReserve();
    void backToNewCus();
    void backToCus();
    void makeActualReserve(String license, String location, String city, String vtname, String fromDate, String fromTime,
                           String toDate, String toTime, int ReservationNum);
=======
     void login(String username, String password);
>>>>>>> b7e0613422a233e53b9a703c1843be3bde297d1b
}
