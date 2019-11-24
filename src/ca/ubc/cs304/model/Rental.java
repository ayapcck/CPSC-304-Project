package ca.ubc.cs304.model;

public class Rental {
    private Integer rID;
    private String vLicense;
    private String driversLicense;
    private String fromDate;
    private String fromTime;
    private String toDate;
    private String toTime;
    private Integer odometer;
    private String cardName;
    private Integer cardNo;
    private String expDate;
    private Integer confNo;

    public Rental (Integer rID, String vLicense, String driversLicense, String fromDate, String fromTime, String toDate,
                   String toTime, Integer odometer, String cardName, Integer cardNo, String expDate, Integer confNo) {
        this.rID = rID;
        this.vLicense = vLicense;
        this.driversLicense = driversLicense;
        this.fromDate = fromDate;
        this.fromTime = fromTime;
        this.toDate = toDate;
        this.odometer = odometer;
        this.cardName = cardName;
        this.cardNo = cardNo;
        this.expDate = expDate;
        this.confNo = confNo;
        this.toTime = toTime;
    }

    public Integer getrID() {
        return rID;
    }

    public String getFromDate() {
        return fromDate;
    }

    public String getFromTime() {
        return fromTime;
    }

    public String getToDate() {
        return toDate;
    }

    public Integer getOdometer() {
        return odometer;
    }

    public String getCardName() {
        return cardName;
    }

    public Integer getCardNo() {
        return cardNo;
    }

    public String getExpDate() {
        return expDate;
    }

    public Integer getConfNo() {
        return confNo;
    }

    public String getvLicense() {
        return vLicense;
    }

    public String getDriversLicense() {
        return driversLicense;
    }

    public String getToTime() {
        return toTime;
    }
}
