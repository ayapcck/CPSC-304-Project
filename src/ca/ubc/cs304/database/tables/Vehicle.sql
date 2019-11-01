CREATE TABLE Vehicle(
    vID INTEGER,
    vLicense VARCHAR(255),
    make VARCHAR(255),
    model VARCHAR(255),
    year INTEGER,
    color VARCHAR(255),
    odometer INTEGER,
    status VARCHAR(255),
    VTName VARCHAR(255),
    location VARCHAR(255),
    city VARCHAR(255),
    PRIMARY KEY (vID),
    FOREIGN KEY (location, city) REFERENCES Branch,
    FOREIGN KEY (VTName) REFERENCES VehicleType
);