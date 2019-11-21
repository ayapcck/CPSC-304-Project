package ca.ubc.cs304.model;

import java.sql.Date;

public class TimePeriod {
    private Date fromDate;
    private String fromTime;
    private Date toDate;
    private String toTime;

    public TimePeriod(Date fromDate, String fromTime, Date toDate, String toTime) {
        // TODO: make sure storing DATES is fine as a string (MAY CAUSE BUGS)
        this.fromDate = fromDate;
        this.fromTime =fromTime;
        this.toDate = toDate;
        this.toTime = toTime;
    }

    public String getFromTime() {
        return fromTime;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public String getToTime() {
        return toTime;
    }
}
