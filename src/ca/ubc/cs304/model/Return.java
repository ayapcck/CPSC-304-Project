package ca.ubc.cs304.model;

public class Return {
    private Integer rID;
    private String returnDate; // DATE in SQL
    private Integer odometer;
    private Integer fullTank; // NUMBER(1) in SQL
    private Integer value;

    public Return(Integer rID, String returnDate, Integer odometer, Integer fullTank, Integer value) {
        // TODO: FIND A WAY TO ENFORE FOREIGN KEY IF WE CAN
        this.rID = rID;
        this.returnDate = returnDate;
        this.odometer = odometer;
        this.fullTank = fullTank;
        this.value = value;
    }

    public Integer getrID() {
        return rID;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public Integer getOdometer() {
        return odometer;
    }

    public Integer getFullTank() {
        return fullTank;
    }

    public Integer getValue() {
        return value;
    }
}
