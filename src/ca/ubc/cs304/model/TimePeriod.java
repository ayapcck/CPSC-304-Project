package ca.ubc.cs304.model;

public class TimePeriod {
    private String fromDate;
    private String fromTime;
    private String toDate;
    private String toTime;

    public TimePeriod(String fromDate, String fromTime, String toDate, String toTime) {
        // TODO: make sure storing DATES is fine as a string (MAY CAUSE BUGS)
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
