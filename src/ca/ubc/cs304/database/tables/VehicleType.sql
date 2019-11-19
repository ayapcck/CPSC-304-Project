CREATE TABLE VehicleType(
    VTName VARCHAR(255) not null,
    features VARCHAR(255),
    weeklyRate INTEGER,
    dailyRate INTEGER,
    hourlyRate INTEGER,
    weeklyInsuranceRate INTEGER,
    dailyInsuranceRate INTEGER,
    hourlyInsuranceRate INTEGER,
    kmRate INTEGER,
    PRIMARY KEY (VTName)
);