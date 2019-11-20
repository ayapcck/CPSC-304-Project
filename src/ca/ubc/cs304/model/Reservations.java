package ca.ubc.cs304.model;

import java.util.Calendar;

public class Reservations {
    private Integer confNo;
    private String vtName;
    private String cellNum;
    private Calendar fromDate; // DATE in SQL
    private Calendar toDate; // DATE in SQL
    private Calendar fromTime;
    private Calendar toTime;
    private String location;
    private String city;

    public Reservations(Integer confNo, String vtName, String cellNum, Calendar fromDate, Calendar fromTime, Calendar toDate,
                        Calendar toTime, String  location, String city) {
        // TODO: ENFORCE PRIMARY KEY AND REFERENCES
        this.confNo = confNo;
        this.vtName = vtName;
        this.cellNum = cellNum;
        this.fromDate = fromDate;
        this.fromTime = fromTime;
        this.toDate = toDate;
        this.toTime = toTime;
        this.location = location;
        this.city = city;
    }

    public Integer getConfNo() {
        return confNo;
    }

    public String getVtName() {
        return vtName;
    }

    public String getCellNum() {
        return cellNum;
    }

    public Calendar getFromDate() {
        return fromDate;
    }

    public Calendar getToDate() {
        return toDate;
    }

    public Calendar getFromTime() {
        return fromTime;
    }

    public Calendar getToTime() {
        return toTime;
    }

    public String getLocation() { return location; }

    public String getCity() { return city; }
}
