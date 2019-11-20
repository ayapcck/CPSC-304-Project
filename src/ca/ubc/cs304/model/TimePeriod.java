package ca.ubc.cs304.model;

import java.util.Calendar;

public class TimePeriod {
    private Calendar fromDate;
    private Calendar fromTime;
    private Calendar toDate;
    private Calendar toTime;

    public TimePeriod(Calendar fromDate, Calendar fromTime, Calendar toDate, Calendar toTime) {
        // TODO: make sure storing DATES is fine as a string (MAY CAUSE BUGS)
        this.fromDate = fromDate;
        this.fromTime =fromTime;
        this.toDate = toDate;
        this.toTime = toTime;
    }

    public Calendar getFromTime() {
        return fromTime;
    }

    public Calendar getFromDate() {
        return fromDate;
    }

    public Calendar getToDate() {
        return toDate;
    }

    public Calendar getToTime() {
        return toTime;
    }
}
