package ca.ubc.cs304.model;

public class RentalCost {
    private VehicleType vehicleType;
    private long weeks;
    private long daysLeft;
    private long hoursLeft;

    private long weekValue;
    private long dayValue;
    private long hourValue;
    private long weekInsuranceValue;
    private long dayInsuranceValue;
    private long hourInsuranceValue;

    public RentalCost(VehicleType vehicleType, long weeks, long daysLeft, long hoursLeft) {
        this.vehicleType = vehicleType;
        this.weeks = weeks;
        this.daysLeft = daysLeft;
        this.hoursLeft = hoursLeft;

        weekValue = vehicleType.getWeeklyRate() * weeks;
        dayValue = vehicleType.getDailyRate() * daysLeft;
        hourValue = vehicleType.getHourlyRate() * hoursLeft;
        weekInsuranceValue = vehicleType.getWeeklyInsuranceRate() * weeks;
        dayInsuranceValue = vehicleType.getDailyInsuranceRate() * daysLeft;
        hourInsuranceValue = vehicleType.getHourlyInsuranceRate() * hoursLeft;
    }

    public long getDayInsuranceValue() {
        return dayInsuranceValue;
    }

    public long getDaysLeft() {
        return daysLeft;
    }

    public long getDayValue() {
        return dayValue;
    }

    public long getHourInsuranceValue() {
        return hourInsuranceValue;
    }

    public long getHoursLeft() {
        return hoursLeft;
    }

    public long getHourValue() {
        return hourValue;
    }

    public long getWeekInsuranceValue() {
        return weekInsuranceValue;
    }

    public long getWeeks() {
        return weeks;
    }

    public long getWeekValue() {
        return weekValue;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public int getCalculatedCost() {
        return (int) (weekValue + dayValue + hourValue + weekInsuranceValue + dayInsuranceValue + hourInsuranceValue);
    }
}
