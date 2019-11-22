package ca.ubc.cs304.model;

public class VehicleType {
    private String vtName;
    private String features;
    private Integer weeklyRate;
    private Integer dailyRate;
    private Integer hourlyRate;
    private Integer weeklyInsuranceRate;
    private Integer dailyInsuranceRate;
    private Integer hourlyInsuranceRate;
    private Integer kmRate;

    public VehicleType(String vtName, String features, Integer weeklyRate, Integer dailyRate, Integer hourlyRate,
                       Integer weeklyInsuranceRate, Integer dailyInsuranceRate, Integer hourlyInsuranceRate, Integer kmRate) {
        this.vtName = vtName;
        this.features = features;
        this.weeklyRate = weeklyRate;
        this.dailyRate = dailyRate;
        this.hourlyRate = hourlyRate;
        this.weeklyInsuranceRate = weeklyInsuranceRate;
        this.dailyInsuranceRate = dailyInsuranceRate;
        this.hourlyInsuranceRate = hourlyInsuranceRate;
        this.kmRate = kmRate;
    }

    // getters

    public String getVtName() {
        return this.vtName;
    }

    public String getFeatures() {
        return this.features;
    }

    public Integer getWeeklyInsuranceRate() {
        return weeklyInsuranceRate;
    }

    public Integer getWeeklyRate() {
        return weeklyRate;
    }

    public Integer getDailyRate() {
        return dailyRate;
    }

    public Integer getHourlyRate() {
        return hourlyRate;
    }

    public Integer getDailyInsuranceRate() {
        return dailyInsuranceRate;
    }

    public Integer getHourlyInsuranceRate() {
        return hourlyInsuranceRate;
    }

    public Integer getKmRate() {
        return kmRate;
    }
}
