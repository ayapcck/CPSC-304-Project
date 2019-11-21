package ca.ubc.cs304.model;

import java.sql.Date;

public class Reservations {
    private Integer confNo;
    private String vtName;
    private String dLicense;
    private Date fromDate; // DATE in SQL
    private Date toDate; // DATE in SQL
    private String fromTime;
    private String toTime;

    public Reservations(Integer confNo, String vtName, String dLicense, Date fromDate, String fromTime, Date toDate,
                        String toTime) {
        // TODO: ENFORCE PRIMARY KEY AND REFERENCES
        this.confNo = confNo;
        this.vtName = vtName;
        this.dLicense = dLicense;
        this.fromDate = fromDate;
        this.fromTime = fromTime;
        this.toDate = toDate;
        this.toTime = toTime;

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

    public Date getFromDate() {
        return fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public String getFromTime() {
        return fromTime;
    }

    public String getToTime() {
        return toTime;
    }
}
