package ca.ubc.cs304.model;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Reservation {
    private Integer confNo;
    private String vtName;
    private String dLicense;
    private String fromDate;
    private String toDate;
    private String fromTime;
    private String toTime;
    private TimePeriod timePeriod;

    public Reservation(Integer confNo, String vtName, String dLicense,
                       String fromDate, String fromTime,
                       String toDate, String toTime) {
        if (confNo == null) {
            this.confNo = (int) (Math.random() * 1000);
        } else {
            this.confNo = confNo;
        }
        this.vtName = vtName;
        this.dLicense = dLicense;
        this.fromDate = fromDate;
        this.fromTime = fromTime;
        this.toDate = toDate;
        this.toTime = toTime;
        this.timePeriod = new TimePeriod(fromDate, fromTime, toDate, toTime);
    }

    public Reservation(Integer confNo, String vtName, String dLicense, TimePeriod timePeriod) {
        this(confNo, vtName, dLicense, timePeriod.getFromDate(), timePeriod.getFromTime(),
                timePeriod.getToDate(), timePeriod.getToTime());
    }

    public static Reservation createReservationModel(ResultSet resultSet) {
        try {
            if (resultSet.next()) {
                int confNo = resultSet.getInt(1);
                String vtName = resultSet.getString(2);
                String driversLicense = resultSet.getString(3);
                String fromDate = resultSet.getString(4);
                String fromTime = resultSet.getString(5);
                String toDate = resultSet.getString(6);
                String toTime = resultSet.getString(7);
                return new Reservation(confNo, vtName, driversLicense, fromDate, fromTime, toDate, toTime);
            }
        } catch (SQLException e) {
            System.out.println("Exception when parsing reservation from database\n" + e.getMessage());
        }
        return null;
    }

    public Integer getConfNo() {
        return confNo;
    }

    public String getVtName() {
        return vtName;
    }

    public String getdLicense() {
        return dLicense;
    }

    public String getFromDate() {
        return fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public String getFromTime() {
        return fromTime;
    }

    public String getToTime() {
        return toTime;
    }

    public TimePeriod getTimePeriod() {
        return timePeriod;
    }
}
