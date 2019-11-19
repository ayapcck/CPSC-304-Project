CREATE TABLE TimePeriod(
    fromDate DATE,
    fromTime VARCHAR(255),
    toDate DATE,
    toTime VARCHAR(255),
    PRIMARY KEY (fromDate, fromTime, toDate, toTime)
);

CREATE TABLE Customer(
    cellNum VARCHAR(255),
    name VARCHAR(255),
    address VARCHAR(255),
    driversLicense VARCHAR(255),
    PRIMARY KEY (cellNum)
);

CREATE TABLE Branch(
    location VARCHAR(255),
    city VARCHAR(255),
    PRIMARY KEY (location, city)
);

CREATE TABLE VehicleType(
    VTName VARCHAR(255),
    features VARCHAR(255),
    weeklyRate INTEGER DEFAULT 1000,
    dailyRate INTEGER DEFAULT 100,
    hourlyRate INTEGER DEFAULT 10,
    weeklyInsuranceRate INTEGER DEFAULT 1000,
    dailyInsuranceRate INTEGER DEFAULT 100,
    hourlyInsuranceRate INTEGER DEFAULT 10,
    kmRate INTEGER DEFAULT 2,
    PRIMARY KEY (VTName)
);

CREATE TABLE Vehicle(
    vID INTEGER,
    vLicense VARCHAR(255) DEFAULT 'XXX-XXX',
    make VARCHAR(255),
    model VARCHAR(255),
    year INTEGER,
    color VARCHAR(255),
    odometer INTEGER,
    status VARCHAR(255),
    VTName VARCHAR(255) NOT NULL,
    location VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    PRIMARY KEY (vID),
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

CREATE TABLE Reservations(
    confNo INTEGER,
    VTName VARCHAR(255) NOT NULL,
    cellNum VARCHAR(255) NOT NULL,
    fromDate DATE,
    fromTime VARCHAR(255),
    toDate DATE,
    toTime VARCHAR(255),
    PRIMARY KEY (confNo),
    FOREIGN KEY (VTName) REFERENCES VehicleType,
    FOREIGN KEY (cellNum) REFERENCES Customer,
    FOREIGN KEY (fromDate, fromTime, toDate, toTime) REFERENCES TimePeriod
);

CREATE TABLE Rental(
    rID INTEGER,
    vID INTEGER NOT NULL,
    cellNum VARCHAR(255) NOT NULL,
    fromDate DATE NOT NULL,
    fromTime VARCHAR(255) NOT NULL,
    toDate DATE NOT NULL,
    toTime VARCHAR(255) NOT NULL,
    odometer VARCHAR(255),
    cardName VARCHAR(255),
    cardNo VARCHAR(255),
    expDate VARCHAR(255),
    confNo INTEGER,
    PRIMARY KEY (rID),
    FOREIGN KEY (vID) REFERENCES Vehicle,
    FOREIGN KEY (cellNum) REFERENCES Customer,
    FOREIGN KEY (fromDate, fromTime, toDate, toTime) REFERENCES TimePeriod,
    FOREIGN KEY (confNo) REFERENCES Reservations
);

CREATE TABLE Return(
    rID INTEGER,
    returnDate DATE,
    odometer INTEGER,
    fullTank NUMBER(1),
    value INTEGER,
    PRIMARY KEY (rID),
    FOREIGN KEY (rID) REFERENCES Rental
);

INSERT INTO Branch (location, city) VALUES ('shop_1', 'Vancouver');
INSERT INTO Branch (location, city) VALUES ('shop_2', 'Vancouver');
INSERT INTO Branch (location, city) VALUES ('shop_3', 'Vancouver');
INSERT INTO Branch (location, city) VALUES ('shop_1', 'Burnaby');
INSERT INTO Branch (location, city) VALUES ('shop_2', 'Burnaby');

INSERT INTO Customer (cellNum, name, address, driversLicense)
    VALUES (6041234567, 'John Smith', '1234 made up lane', '9282019');
INSERT INTO Customer (cellNum, name, address, driversLicense)
    VALUES (1234567890, 'Cane Able', '4312 does not exist', 'license_num');
INSERT INTO Customer (cellNum, name, address, driversLicense)
    VALUES (0109238881, 'Smith Apple', 'Disney Land Resort', 'q1238822');
INSERT INTO Customer (cellNum, name, address, driversLicense)
    VALUES (8388772838, 'Jonny Rocket', 'Tim Hortons', '920019293');
INSERT INTO Customer (cellNum, name, address, driversLicense)
    VALUES (8289199482, 'Robert Frost', '9829 something road', '9maybe8912');

INSERT INTO VehicleType (VTName, features) VALUES ('SUV', 'car seat');
INSERT INTO VehicleType (VTName, features) VALUES ('truck', 'car seat');
INSERT INTO VehicleType (VTName, features) VALUES ('economy', 'car seat');
INSERT INTO VehicleType (VTName, features) VALUES ('compact', 'car seat');
INSERT INTO VehicleType (VTName, features) VALUES ('mid-size', 'car seat');
INSERT INTO VehicleType (VTName, features) VALUES ('standard', 'car seat');
INSERT INTO VehicleType (VTName, features) VALUES ('full-size', 'car seat');

INSERT INTO Vehicle (vID, color, status, VTName, location, city)
    VALUES (1, 'red', 'for_rent', 'SUV', 'shop_1', 'Vancouver');
INSERT INTO Vehicle (vID, color, status, VTName, location, city)
    VALUES (2, 'blue', 'for_rent', 'truck', 'shop_2', 'Vancouver');
INSERT INTO Vehicle (vID, color, status, VTName, location, city)
    VALUES (3, 'green', 'for_rent', 'standard', 'shop_1', 'Vancouver');
INSERT INTO Vehicle (vID, color, status, VTName, location, city)
    VALUES (4, 'red', 'for_rent', 'full-size', 'shop_1', 'Burnaby');
INSERT INTO Vehicle (vID, color, status, VTName, location, city)
    VALUES (5, 'blue', 'for_sale', 'economy', 'shop_1', 'Burnaby');