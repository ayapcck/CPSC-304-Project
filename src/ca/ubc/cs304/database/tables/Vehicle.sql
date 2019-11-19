CREATE TABLE Vehicle(
    vID INTEGER,
    vLicense VARCHAR(255) not null,
    make VARCHAR(255),
    model VARCHAR(255),
    year INTEGER,
    color VARCHAR(255),
    odometer INTEGER,
    status VARCHAR(255),
    VTName VARCHAR(255),
    location VARCHAR(255),
    city VARCHAR(255),
    PRIMARY KEY (vLicense),
    FOREIGN KEY (location, city) REFERENCES Branch,
    FOREIGN KEY (VTName) REFERENCES VehicleType,
    CHECK (status = 'for_rent' OR status = 'for_sale')
);

CREATE VIEW ForRent AS
SELECT * FROM Vehicle
WHERE status = 'for_rent';

CREATE VIEW ForSale AS
SELECT * FROM Vehicle
WHERE status = 'for_sale';