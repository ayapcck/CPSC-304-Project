package ca.ubc.cs304.model;

public class Rental {
    private Integer rID;
    private Integer vID;
    private String cellNum;
    private String fromDate;
    private String fromTime;
    private String toDate;
    private Integer odometer;
    private String cardName;
    private String cardNo;
    private String expDate;
    private Integer confNo;

    public Rental (Integer rID, Integer vID, String cellNum, String fromDate, String fromTime, String toDate,
                   Integer odometer, String cardName, String cardNo, String expDate, Integer confNo) {
        // TODO: ENFORCE PRIMARY KEY AND FOREIGN KEY
        this.rID = rID;
        this.vID = vID;
        this.cellNum = cellNum;
        this.fromDate = fromDate;
        this.fromTime = fromTime;
        this.toDate = toDate;
        this.odometer = odometer;
        this.cardName = cardName;
        this.cardNo = cardNo;
        this.expDate = expDate;
        this.confNo = confNo;
    }

    public Integer getrID() {
        return rID;
    }

    public Integer getvID() {
        return vID;
    }

    public String getCellNum() {
        return cellNum;
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

    public String getCardNo() {
        return cardNo;
    }

    public String getExpDate() {
        return expDate;
    }

    public Integer getConfNo() {
        return confNo;
    }
}
