package ca.ubc.cs304.model;

import java.sql.Date;

public class TimePeriod {
    private String fromDate;
    private String fromTime;
    private String toDate;
    private String toTime;

    public TimePeriod(String fromDate, String fromTime, String toDate, String toTime) {
        this.fromDate = fromDate;
        this.fromTime =fromTime;
        this.toDate = toDate;
        this.toTime = toTime;
    }

    public String getFromTime() {
        return fromTime;
    }

    public String getFromDate() {
        return fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public String getToTime() {
        return toTime;
    }
}
