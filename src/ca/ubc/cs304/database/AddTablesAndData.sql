CREATE TABLE TimePeriod(
                           fromDate VARCHAR(255),
                           fromTime VARCHAR(255),
                           toDate VARCHAR(255),
                           toTime VARCHAR(255),
                           PRIMARY KEY (fromDate, fromTime, toDate, toTime)
);

CREATE TABLE Customer(
                         cellNum VARCHAR(255),
                         name VARCHAR(255),
                         address VARCHAR(255),
                         driversLicense VARCHAR(255),
                         PRIMARY KEY (driversLicense)
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

CREATE TABLE ForRent(
                        vID INTEGER,
                        vLicense VARCHAR(255),
                        make VARCHAR(255) DEFAULT 'Volkswagen',
                        model VARCHAR(255) DEFAULT 'Beetle',
                        year INTEGER DEFAULT 2000,
                        color VARCHAR(255),
                        odometer INTEGER DEFAULT 0,
                        status VARCHAR(255),
                        VTName VARCHAR(255) NOT NULL,
                        location VARCHAR(255) NOT NULL,
                        city VARCHAR(255) NOT NULL,
                        PRIMARY KEY (vLicense),
                        FOREIGN KEY (location, city) REFERENCES Branch,
                        FOREIGN KEY (VTName) REFERENCES VehicleType,
                        CHECK (status = 'available' OR status = 'notavailable')
);

CREATE TABLE ForSale(
                        vID      INTEGER,
                        vFSLicense VARCHAR(255),
                        make     VARCHAR(255),
                        model    VARCHAR(255),
                        year     INTEGER,
                        color    VARCHAR(255),
                        odometer INTEGER,
                        status   VARCHAR(255),
                        VTName   VARCHAR(255) NOT NULL,
                        location VARCHAR(255) NOT NULL,
                        city     VARCHAR(255) NOT NULL,
                        PRIMARY KEY (vFSLicense),
                        FOREIGN KEY (location, city) REFERENCES Branch,
                        FOREIGN KEY (VTName) REFERENCES VehicleType,
                        CHECK (status = 'available' OR status = 'notavailable')
);

CREATE TABLE Reservations(
                             confNo INTEGER,
                             VTName VARCHAR(255) NOT NULL,
                             driversLicense VARCHAR(255) NOT NULL,
                             fromDate VARCHAR(255),
                             fromTime VARCHAR(255),
                             toDate VARCHAR(255),
                             toTime VARCHAR(255),
                             location VARCHAR(255) NOT NULL,
                             city     VARCHAR(255) NOT NULL,
                             PRIMARY KEY (confNo),
                             FOREIGN KEY (VTName) REFERENCES VehicleType,
                             FOREIGN KEY (location, city) REFERENCES Branch,
                             FOREIGN KEY (driversLicense) REFERENCES Customer,
                             FOREIGN KEY (fromDate, fromTime, toDate, toTime) REFERENCES TimePeriod
);

CREATE TABLE Rental(
                       rID INTEGER,
                       vLicense VARCHAR(255) NOT NULL,
                       driversLicense VARCHAR(255) NOT NULL,
                       fromDate VARCHAR(255) NOT NULL,
                       fromTime VARCHAR(255) NOT NULL,
                       toDate VARCHAR(255) NOT NULL,
                       toTime VARCHAR(255) NOT NULL,
                       odometer INTEGER,
                       cardName VARCHAR(255),
                       cardNo INTEGER,
                       expDate VARCHAR(255),
                       confNo INTEGER,
                       PRIMARY KEY (rID),
                       FOREIGN KEY (vLicense) REFERENCES ForRent,
                       FOREIGN KEY (driversLicense) REFERENCES Customer,
                       FOREIGN KEY (fromDate, fromTime, toDate, toTime) REFERENCES TimePeriod,
                       FOREIGN KEY (confNo) REFERENCES Reservations
);

CREATE TABLE Return(
                       rID INTEGER,
                       returnDate VARCHAR(255),
                       odometer INTEGER,
                       fullTank NUMBER(1),
                       value INTEGER,
                       PRIMARY KEY (rID),
                       FOREIGN KEY (rID) REFERENCES Rental
);

INSERT INTO Branch (location, city) VALUES ('shop_1', 'Vancouver');
INSERT INTO Branch (location, city) VALUES ('shop_2', 'Vancouver');
INSERT INTO Branch (location, city) VALUES ('shop_3', 'Vancouver');
INSERT INTO Branch (location, city) VALUES ('shop_4', 'Vancouver');
INSERT INTO Branch (location, city) VALUES ('shop_1', 'Burnaby');
INSERT INTO Branch (location, city) VALUES ('shop_2', 'Burnaby');
INSERT INTO Branch (location, city) VALUES ('shop_3', 'Burnaby');
INSERT INTO Branch (location, city) VALUES ('shop_4', 'Burnaby');
INSERT INTO Branch (location, city) VALUES ('shop_1', 'Coquitlam');
INSERT INTO Branch (location, city) VALUES ('shop_2', 'Coquitlam');
INSERT INTO Branch (location, city) VALUES ('shop_3', 'Coquitlam');
INSERT INTO Branch (location, city) VALUES ('shop_4', 'Coquitlam');
INSERT INTO Branch (location, city) VALUES ('shop_1', 'Richmond');
INSERT INTO Branch (location, city) VALUES ('shop_2', 'Richmond');
INSERT INTO Branch (location, city) VALUES ('shop_3', 'Richmond');
INSERT INTO Branch (location, city) VALUES ('shop_4', 'Richmond');
INSERT INTO Branch (location, city) VALUES ('shop_5', 'Seattle');


INSERT INTO Customer (cellNum, name, address, driversLicense)
VALUES (6041234567, 'John Smith', '1234 made up lane', '9282019');
INSERT INTO Customer (cellNum, name, address, driversLicense)
VALUES (6041234567, 'Bob Smith', '1234 made up lane', '9272019');
INSERT INTO Customer (cellNum, name, address, driversLicense)
VALUES (6041234567, 'Jo Smith', '1234 made up lane', '9262019');
INSERT INTO Customer (cellNum, name, address, driversLicense)
VALUES (6041234567, 'Jon Smith', '1234 made up lane', '9252019');
INSERT INTO Customer (cellNum, name, address, driversLicense)
VALUES (6041234567, 'Joh Smith', '1234 made up lane', '9242019');
INSERT INTO Customer (cellNum, name, address, driversLicense)
VALUES (6041234567, 'J Smith', '1234 made up lane', '9232019');
INSERT INTO Customer (cellNum, name, address, driversLicense)
VALUES (6041234567, 'Rob Smith', '1234 made up lane', '9222019');
INSERT INTO Customer (cellNum, name, address, driversLicense)
VALUES (6041234567, 'Ro Smith', '1234 made up lane', '9212019');

INSERT INTO Customer (cellNum, name, address, driversLicense)
VALUES (1234567890, 'Cane Able', '4312 does not exist', 'license_num');
INSERT INTO Customer (cellNum, name, address, driversLicense)
VALUES (0109238881, 'Smith Apple', 'Disney Land Resort', 'q1238822');
INSERT INTO Customer (cellNum, name, address, driversLicense)
VALUES (8388772838, 'Jonny Rocket', 'Tim Hortons', '920019293');
INSERT INTO Customer (cellNum, name, address, driversLicense)
VALUES (8289199482, 'Robert Frost', '9829 something road', '9maybe8912');

INSERT INTO Customer (cellNum, name, address, driversLicense)
VALUES (7783648291, 'Julia Frost', '9829 agronomy road', 'jk268g');
INSERT INTO Customer (cellNum, name, address, driversLicense)
VALUES (8876354859, 'Lisa Bin', '9829 agronomy road', 'jk568g');
INSERT INTO Customer (cellNum, name, address, driversLicense)
VALUES (283794058, 'Charlie Targarian', '9829 agronomy road', 'jk468g');
INSERT INTO Customer (cellNum, name, address, driversLicense)
VALUES (7739274854, 'Monica Baby', '9829 city road', 'jk368g');

INSERT INTO VehicleType (VTName, features) VALUES ('SUV', 'car seat');
INSERT INTO VehicleType (VTName, features) VALUES ('truck', 'car seat');
INSERT INTO VehicleType (VTName, features) VALUES ('economy', 'car seat');
INSERT INTO VehicleType (VTName, features) VALUES ('compact', 'car seat');
INSERT INTO VehicleType (VTName, features) VALUES ('mid-size', 'car seat');
INSERT INTO VehicleType (VTName, features) VALUES ('standard', 'car seat');
INSERT INTO VehicleType (VTName, features) VALUES ('full-size', 'car seat');

INSERT INTO ForRent (vID, vLicense, color, status, VTName, location, city)
VALUES (1,'A10111', 'red', 'available', 'SUV', 'shop_1', 'Vancouver');
INSERT INTO ForRent (vID, vLicense, color, status, VTName, location, city)
VALUES (1,'A10112', 'red', 'available', 'full-size', 'shop_1', 'Vancouver');
INSERT INTO ForRent (vID, vLicense, color, status, VTName, location, city)
VALUES (1,'A10113', 'red', 'available', 'full-size', 'shop_1', 'Vancouver');
INSERT INTO ForRent (vID, vLicense, color, status, VTName, location, city)
VALUES (1,'A10114', 'red', 'available', 'truck', 'shop_1', 'Vancouver');
INSERT INTO ForRent (vID, vLicense, color, status, VTName, location, city)
VALUES (1,'A10115', 'red', 'available', 'truck', 'shop_1', 'Vancouver');
INSERT INTO ForRent (vID, vLicense, color, status, VTName, location, city)
VALUES (1,'A10116', 'red', 'available', 'truck', 'shop_1', 'Vancouver');
INSERT INTO ForRent (vID, vLicense, color, status, VTName, location, city)
VALUES (1,'A10117', 'red', 'available', 'economy', 'shop_1', 'Vancouver');
INSERT INTO ForRent (vID, vLicense, color, status, VTName, location, city)
VALUES (1,'A10118', 'red', 'available', 'economy', 'shop_1', 'Vancouver');
INSERT INTO ForRent (vID, vLicense, color, status, VTName, location, city)
VALUES (1,'A10119', 'red', 'available', 'economy', 'shop_1', 'Vancouver');
INSERT INTO ForRent (vID, vLicense, color, status, VTName, location, city)
VALUES (1,'A10121', 'red', 'available', 'economy', 'shop_1', 'Vancouver');
INSERT INTO ForRent (vID, vLicense, color, status, VTName, location, city)
VALUES (1,'A10122', 'red', 'available', 'mid-size', 'shop_1', 'Vancouver');
INSERT INTO ForRent (vID, vLicense, color, status, VTName, location, city)
VALUES (1,'A10123', 'red', 'available', 'SUV', 'shop_1', 'Vancouver');
INSERT INTO ForRent (vID, vLicense, color, status, VTName, location, city)
VALUES (1,'A10124', 'red', 'available', 'SUV', 'shop_1', 'Vancouver');
INSERT INTO ForRent (vID, vLicense, color, status, VTName, location, city)
VALUES (1,'A10125', 'red', 'available', 'SUV', 'shop_1', 'Vancouver');
INSERT INTO ForRent (vID, vLicense, color, status, VTName, location, city)
VALUES (1,'A10126', 'red', 'available', 'SUV', 'shop_1', 'Vancouver');

INSERT INTO ForRent (vID, vLicense, color, status, VTName, location, city)
VALUES (1,'A10127', 'red', 'available', 'compact', 'shop_1', 'Vancouver');
INSERT INTO ForRent (vID, vLicense, color, status, VTName, location, city)
VALUES (1,'A10128', 'red', 'available', 'compact', 'shop_1', 'Vancouver');
INSERT INTO ForRent (vID, vLicense, color, status, VTName, location, city)
VALUES (1,'A10129', 'red', 'available', 'mid-size', 'shop_1', 'Vancouver');
INSERT INTO ForRent (vID, vLicense, color, status, VTName, location, city)
VALUES (1,'A10130', 'red', 'available', 'mid-size', 'shop_1', 'Vancouver');
INSERT INTO ForRent (vID, vLicense, color, status, VTName, location, city)
VALUES (1,'A10131', 'red', 'available', 'mid-size', 'shop_1', 'Vancouver');


INSERT INTO ForRent (vID, vLicense, color, status, VTName, location, city)
VALUES (2, 'B10111', 'blue', 'available', 'truck', 'shop_2', 'Vancouver');
INSERT INTO ForRent (vID, vLicense, color, status, VTName, location, city)
VALUES (2, 'B10112', 'blue', 'available', 'truck', 'shop_3', 'Vancouver');
INSERT INTO ForRent (vID, vLicense, color, status, VTName, location, city)
VALUES (3, 'C10111', 'green', 'available', 'standard', 'shop_1', 'Vancouver');

INSERT INTO ForRent (vID, vLicense, color, status, VTName, location, city)
VALUES (4, 'D10111', 'red', 'available', 'full-size', 'shop_1', 'Burnaby');
INSERT INTO ForRent (vID, vLicense, color, status, VTName, location, city)
VALUES (4, 'D10112', 'red', 'available', 'full-size', 'shop_2', 'Burnaby');
INSERT INTO ForRent (vID, vLicense, color, status, VTName, location, city)
VALUES (4, 'D10113', 'red', 'available', 'full-size', 'shop_3', 'Burnaby');
INSERT INTO ForRent (vID, vLicense, color, status, VTName, location, city)
VALUES (4, 'D10114', 'red', 'available', 'full-size', 'shop_4', 'Burnaby');
INSERT INTO ForRent (vID, vLicense, color, status, VTName, location, city)
VALUES (4, 'D10115', 'red', 'available', 'full-size', 'shop_5', 'Burnaby');
INSERT INTO ForRent (vID, vLicense, color, status, VTName, location, city)
VALUES (4, 'D10116', 'red', 'available', 'full-size', 'shop_6', 'Burnaby');
INSERT INTO ForRent (vID, vLicense, color, status, VTName, location, city)
VALUES (4, 'D10117', 'red', 'available', 'full-size', 'shop_7', 'Burnaby');
INSERT INTO ForRent (vID, vLicense, color, status, VTName, location, city)
VALUES (4, 'D10118', 'red', 'available', 'full-size', 'shop_8', 'Burnaby');
INSERT INTO ForRent (vID, vLicense, color, status, VTName, location, city)
VALUES (4, 'D10119', 'red', 'available', 'full-size', 'shop_9', 'Burnaby');
INSERT INTO ForRent (vID, vLicense, color, status, VTName, location, city)
VALUES (4, 'D10120', 'red', 'available', 'full-size', 'shop_10', 'Burnaby');
INSERT INTO ForRent (vID, vLicense, color, status, VTName, location, city)
VALUES (4, 'D10121', 'red', 'available', 'full-size', 'shop_11', 'Burnaby');
INSERT INTO ForRent (vID, vLicense, color, status, VTName, location, city)
VALUES (4, 'D10122', 'red', 'available', 'full-size', 'shop_12', 'Burnaby');

INSERT INTO ForSale (vID, vFSLicense, color, status, VTName, location, city)
VALUES (5, 'E10111', 'blue', 'available', 'economy', 'shop_1', 'Burnaby');
INSERT INTO ForRent (vID, vLicense, color, status, VTName, location, city)
VALUES (10,'A90909', 'red', 'available', 'SUV', 'shop_1', 'Richmond');
INSERT INTO ForRent (vID, vLicense, color, status, VTName, location, city)
VALUES (11,'A23909', 'white', 'available', 'SUV', 'shop_1', 'Richmond');
INSERT INTO ForRent (vID, vLicense, color, status, VTName, location, city)
VALUES (12,'A80909', 'blue', 'available', 'SUV', 'shop_2', 'Richmond');
INSERT INTO ForRent (vID, vLicense, color, status, VTName, location, city)
VALUES (13,'A70909', 'red', 'available', 'SUV', 'shop_3', 'Richmond');
INSERT INTO ForRent (vID, vLicense, color, status, VTName, location, city)
VALUES (14,'A60908', 'black', 'available', 'mid-size', 'shop_4', 'Coquitlam');
INSERT INTO ForRent (vID, vLicense, color, status, VTName, location, city)
VALUES (15,'A90908', 'red', 'available', 'economy', 'shop_1', 'Coquitlam');
INSERT INTO ForRent (vID, vLicense, color, status, VTName, location, city)
VALUES (16,'A23908', 'green', 'available', 'economy', 'shop_1', 'Coquitlam');
INSERT INTO ForRent (vID, vLicense, color, status, VTName, location, city)
VALUES (17,'A80965', 'white', 'available', 'SUV', 'shop_2', 'Coquitlam');
INSERT INTO ForRent (vID, vLicense, color, status, VTName, location, city)
VALUES (18,'A70900', 'red', 'available', 'economy', 'shop_3', 'Coquitlam');
INSERT INTO ForRent (vID, vLicense, color, status, VTName, location, city)
VALUES (19,'D70900', 'green', 'available', 'standard', 'shop_3', 'Coquitlam');
INSERT INTO ForRent (vID, vLicense, color, status, VTName, location, city)
VALUES (20,'A60349', 'black', 'available', 'mid-size', 'shop_4', 'Coquitlam');




INSERT INTO TIMEPERIOD (fromDate, fromTime, toDate, toTime)
VALUES ('2019-11-24', '12:05', '2020-01-20', '1:00');

INSERT INTO TIMEPERIOD (fromDate, fromTime, toDate, toTime)
VALUES ('2019-11-20', '12:05', '2019-11-25', '1:00');

INSERT INTO TIMEPERIOD (fromDate, fromTime, toDate, toTime)
VALUES ('2019-11-26', '12:05', '2019-01-20', '1:00');

INSERT INTO TIMEPERIOD (fromDate, fromTime, toDate, toTime)
VALUES ('2019-11-27', '12:05', '2019-01-20', '1:00');


INSERT INTO Reservations (confNo, VTName, driversLicense, fromDate, fromTime, toDate, toTime, location, city)
VALUES (123, 'truck', '9282019', '2019-11-24', '12:05', '2020-01-20', '1:00', 'shop_1', 'Vancouver');


INSERT INTO Reservations (confNo, VTName, driversLicense, fromDate, fromTime, toDate, toTime, location, city)
VALUES (124, 'truck', '9282019', '2019-11-20', '12:05', '2019-11-25', '1:00', 'shop_1', 'Vancouver');

INSERT INTO Reservations (confNo, VTName, driversLicense, fromDate, fromTime, toDate, toTime, location, city)
VALUES (125, 'truck', '9272019', '2019-11-24', '12:05', '2020-01-20', '1:00', 'shop_1', 'Vancouver');

INSERT INTO Reservations (confNo, VTName, driversLicense, fromDate, fromTime, toDate, toTime, location, city)
VALUES (126, 'full-size', '9262019', '2019-11-24', '12:05', '2020-01-20', '1:00', 'shop_1', 'Vancouver');

INSERT INTO Reservations (confNo, VTName, driversLicense, fromDate, fromTime, toDate, toTime, location, city)
VALUES (127, 'full-size', '9252019', '2019-11-24', '12:05', '2020-01-20', '1:00', 'shop_1', 'Vancouver');

INSERT INTO Reservations (confNo, VTName, driversLicense, fromDate, fromTime, toDate, toTime, location, city)
VALUES (128, 'economy', '9242019', '2019-11-24', '12:05', '2020-01-20', '1:00', 'shop_1', 'Vancouver');

INSERT INTO Reservations (confNo, VTName, driversLicense, fromDate, fromTime, toDate, toTime, location, city)
VALUES (129, 'economy', '9232019', '2019-11-24', '12:05', '2020-01-20', '1:00', 'shop_1', 'Vancouver');

INSERT INTO Reservations (confNo, VTName, driversLicense, fromDate, fromTime, toDate, toTime, location, city)
VALUES (130, 'standard', '1234567', '2019-12-01', '19:05', '2020-01-20', '1:00', 'shop_1', 'Vancouver');
INSERT INTO Reservations (confNo, VTName, driversLicense, fromDate, fromTime, toDate, toTime, location, city)
VALUES (131, 'standard', '2234567', '2019-12-02', '12:05', '2020-01-20', '1:00', 'shop_1', 'Vancouver');
INSERT INTO Reservations (confNo, VTName, driversLicense, fromDate, fromTime, toDate, toTime, location, city)
VALUES (132, 'standard', '3234567', '2019-11-03', '12:05', '2020-01-20', '1:00', 'shop_1', 'Vancouver');
INSERT INTO Reservations (confNo, VTName, driversLicense, fromDate, fromTime, toDate, toTime, location, city)
VALUES (133, 'standard', '4234567', '2020-01-01', '12:05', '2021-01-20', '1:00', 'shop_1', 'Vancouver');


INSERT INTO Reservations (confNo, VTName, driversLicense, fromDate, fromTime, toDate, toTime, location, city)
VALUES (130, 'economy', '9222019', '2019-11-24', '12:05', '2020-01-20', '1:00', 'shop_1', 'Vancouver');

INSERT INTO Reservations (confNo, VTName, driversLicense, fromDate, fromTime, toDate, toTime, location, city)
VALUES (131, 'SUV', '9222019', '2019-11-24', '12:05', '2020-01-20', '1:00', 'shop_1', 'Vancouver');

INSERT INTO Reservations (confNo, VTName, driversLicense, fromDate, fromTime, toDate, toTime, location, city)
VALUES (132, 'SUV', '9222019', '2019-11-24', '12:05', '2020-01-20', '1:00', 'shop_1', 'Vancouver');

INSERT INTO Reservations (confNo, VTName, driversLicense, fromDate, fromTime, toDate, toTime, location, city)
VALUES (133, 'SUV', '9222019', '2019-11-24', '12:05', '2020-01-20', '1:00', 'shop_1', 'Vancouver');

INSERT INTO Reservations (confNo, VTName, driversLicense, fromDate, fromTime, toDate, toTime, location, city)
VALUES (134, 'SUV', '9222019', '2019-11-24', '12:05', '2020-01-20', '1:00', 'shop_1', 'Vancouver');

INSERT INTO Reservations (confNo, VTName, driversLicense, fromDate, fromTime, toDate, toTime, location, city)
VALUES (135, 'compact', '9222019', '2019-11-24', '12:05', '2020-01-20', '1:00', 'shop_1', 'Vancouver');
INSERT INTO Reservations (confNo, VTName, driversLicense, fromDate, fromTime, toDate, toTime, location, city)
VALUES (136, 'compact', '9222019', '2019-11-24', '12:05', '2020-01-20', '1:00', 'shop_1', 'Vancouver');
INSERT INTO Reservations (confNo, VTName, driversLicense, fromDate, fromTime, toDate, toTime, location, city)
VALUES (137, 'mid-size', '9222019', '2019-11-24', '12:05', '2020-01-20', '1:00', 'shop_1', 'Vancouver');
INSERT INTO Reservations (confNo, VTName, driversLicense, fromDate, fromTime, toDate, toTime, location, city)
VALUES (138, 'mid-size', '9222019', '2019-11-24', '12:05', '2020-01-20', '1:00', 'shop_1', 'Vancouver');
INSERT INTO Reservations (confNo, VTName, driversLicense, fromDate, fromTime, toDate, toTime, location, city)
VALUES (139, 'mid-size', '9222019', '2019-11-24', '12:05', '2020-01-20', '1:00', 'shop_1', 'Vancouver');

-- different city reservations
INSERT INTO Reservations (confNo, VTName, driversLicense, fromDate, fromTime, toDate, toTime, location, city)
VALUES (200, 'truck', '9222019', '2019-11-24', '12:05', '2020-01-20', '1:00', 'shop_2', 'Vancouver');
INSERT INTO Reservations (confNo, VTName, driversLicense, fromDate, fromTime, toDate, toTime, location, city)
VALUES (201, 'truck', '9222019', '2019-11-24', '12:05', '2020-01-20', '1:00', 'shop_3', 'Vancouver');

INSERT INTO Reservations (confNo, VTName, driversLicense, fromDate, fromTime, toDate, toTime, location, city)
VALUES (202, 'full-size', '9222019', '2019-11-24', '12:05', '2020-01-20', '1:00', 'shop_1', 'Burnaby');
INSERT INTO Reservations (confNo, VTName, driversLicense, fromDate, fromTime, toDate, toTime, location, city)
VALUES (203, 'full-size', '9222019', '2019-11-24', '12:05', '2020-01-20', '1:00', 'shop_2', 'Burnaby');
INSERT INTO Reservations (confNo, VTName, driversLicense, fromDate, fromTime, toDate, toTime, location, city)
VALUES (204, 'full-size', '9222019', '2019-11-24', '12:05', '2020-01-20', '1:00', 'shop_3', 'Burnaby');
INSERT INTO Reservations (confNo, VTName, driversLicense, fromDate, fromTime, toDate, toTime, location, city)
VALUES (205, 'full-size', '9222019', '2019-11-24', '12:05', '2020-01-20', '1:00', 'shop_4', 'Burnaby');
INSERT INTO Reservations (confNo, VTName, driversLicense, fromDate, fromTime, toDate, toTime, location, city)
VALUES (206, 'full-size', '9222019', '2019-11-24', '12:05', '2020-01-20', '1:00', 'shop_5', 'Burnaby');
INSERT INTO Reservations (confNo, VTName, driversLicense, fromDate, fromTime, toDate, toTime, location, city)
VALUES (207, 'full-size', '9222019', '2019-11-24', '12:05', '2020-01-20', '1:00', 'shop_6', 'Burnaby');
INSERT INTO Reservations (confNo, VTName, driversLicense, fromDate, fromTime, toDate, toTime, location, city)
VALUES (208, 'full-size', '9222019', '2019-11-24', '12:05', '2020-01-20', '1:00', 'shop_7', 'Burnaby');
INSERT INTO Reservations (confNo, VTName, driversLicense, fromDate, fromTime, toDate, toTime, location, city)
VALUES (209, 'full-size', '9222019', '2019-11-24', '12:05', '2020-01-20', '1:00', 'shop_8', 'Burnaby');
INSERT INTO Reservations (confNo, VTName, driversLicense, fromDate, fromTime, toDate, toTime, location, city)
VALUES (210, 'full-size', '9222019', '2019-11-24', '12:05', '2020-01-20', '1:00', 'shop_9', 'Burnaby');
INSERT INTO Reservations (confNo, VTName, driversLicense, fromDate, fromTime, toDate, toTime, location, city)
VALUES (211, 'full-size', '9222019', '2019-11-24', '12:05', '2020-01-20', '1:00', 'shop_10', 'Burnaby');
INSERT INTO Reservations (confNo, VTName, driversLicense, fromDate, fromTime, toDate, toTime, location, city)
VALUES (212, 'full-size', '9222019', '2019-11-24', '12:05', '2020-01-20', '1:00', 'shop_11', 'Burnaby');
INSERT INTO Reservations (confNo, VTName, driversLicense, fromDate, fromTime, toDate, toTime, location, city)
VALUES (213, 'full-size', '9222019', '2019-11-24', '12:05', '2020-01-20', '1:00', 'shop_12', 'Burnaby');

INSERT INTO Rental(rID ,vLicense, driversLicense , fromDate , fromTime, toDate, toTime, odometer,cardName,cardNo ,expDate, confNo)
VALUES (125, 'A10115', '9282019', '2019-11-20', '12:05', '2019-11-25', '1:00', 0, 'VISA', 123456, '02/21', 124);

INSERT INTO Rental(rID ,vLicense, driversLicense , fromDate , fromTime, toDate, toTime, odometer,cardName,cardNo ,expDate, confNo)
VALUES (126, 'A10116', '9272019', '2019-11-24', '12:05', '2020-01-20', '1:00', 0, 'VISA', 123456, '02/21', 125);

INSERT INTO Rental(rID ,vLicense, driversLicense , fromDate , fromTime, toDate, toTime, odometer,cardName,cardNo ,expDate, confNo)
VALUES (127, 'A10112', '9262019', '2019-11-24', '12:05', '2020-01-20', '1:00', 0, 'VISA', 123456, '02/21', 126);


INSERT INTO Rental(rID ,vLicense, driversLicense , fromDate , fromTime, toDate, toTime, odometer,cardName,cardNo ,expDate, confNo)
VALUES (128, 'A10113', '9252019', '2019-11-24', '12:05', '2020-01-20', '1:00', 0, 'VISA', 123456, '02/21', 127);


INSERT INTO Rental(rID ,vLicense, driversLicense , fromDate , fromTime, toDate, toTime, odometer,cardName,cardNo ,expDate, confNo)
VALUES (129, 'A10117', '9242019', '2019-11-24', '12:05', '2020-01-20', '1:00', 0, 'VISA', 123456, '02/21', 128);


INSERT INTO Rental(rID ,vLicense, driversLicense , fromDate , fromTime, toDate, toTime, odometer,cardName,cardNo ,expDate, confNo)
VALUES (130, 'A10118', '9232019', '2019-11-24', '12:05', '2020-01-20', '1:00', 0, 'VISA', 123456, '02/21', 129);

INSERT INTO Rental(rID ,vLicense, driversLicense , fromDate , fromTime, toDate, toTime, odometer,cardName,cardNo ,expDate, confNo)
VALUES (131, 'A10119', '9222019', '2019-11-24', '12:05', '2020-01-20', '1:00', 0, 'VISA', 123456, '02/21', 130);

INSERT INTO Rental(rID ,vLicense, driversLicense , fromDate , fromTime, toDate, toTime, odometer,cardName,cardNo ,expDate, confNo)
VALUES (132, 'A10123', '9222019', '2019-11-24', '12:05', '2020-01-20', '1:00', 0, 'VISA', 123456, '02/21', 131);

INSERT INTO Rental(rID ,vLicense, driversLicense , fromDate , fromTime, toDate, toTime, odometer,cardName,cardNo ,expDate, confNo)
VALUES (133, 'A10124', '9222019', '2019-11-24', '12:05', '2020-01-20', '1:00', 0, 'VISA', 123456, '02/21', 132);

INSERT INTO Rental(rID ,vLicense, driversLicense , fromDate , fromTime, toDate, toTime, odometer,cardName,cardNo ,expDate, confNo)
VALUES (134, 'A10125', '9222019', '2019-11-24', '12:05', '2020-01-20', '1:00', 0, 'VISA', 123456, '02/21', 133);

INSERT INTO Rental(rID ,vLicense, driversLicense , fromDate , fromTime, toDate, toTime, odometer,cardName,cardNo ,expDate, confNo)
VALUES (135, 'A10126', '9222019', '2019-11-24', '12:05', '2020-01-20', '1:00', 0, 'VISA', 123456, '02/21', 134);

INSERT INTO Rental(rID ,vLicense, driversLicense , fromDate , fromTime, toDate, toTime, odometer,cardName,cardNo ,expDate, confNo)
VALUES (136, 'A10127', '9222019', '2019-11-24', '12:05', '2020-01-20', '1:00', 0, 'VISA', 123456, '02/21', 135);

INSERT INTO Rental(rID ,vLicense, driversLicense , fromDate , fromTime, toDate, toTime, odometer,cardName,cardNo ,expDate, confNo)
VALUES (137, 'A10128', '9222019', '2019-11-24', '12:05', '2020-01-20', '1:00', 0, 'VISA', 123456, '02/21', 136);

INSERT INTO Rental(rID ,vLicense, driversLicense , fromDate , fromTime, toDate, toTime, odometer,cardName,cardNo ,expDate, confNo)
VALUES (138, 'A10129', '9222019', '2019-11-24', '12:05', '2020-01-20', '1:00', 0, 'VISA', 123456, '02/21', 137);

INSERT INTO Rental(rID ,vLicense, driversLicense , fromDate , fromTime, toDate, toTime, odometer,cardName,cardNo ,expDate, confNo)
VALUES (139, 'A10130', '9222019', '2019-11-24', '12:05', '2020-01-20', '1:00', 0, 'VISA', 123456, '02/21', 138);

INSERT INTO Rental(rID ,vLicense, driversLicense , fromDate , fromTime, toDate, toTime, odometer,cardName,cardNo ,expDate, confNo)
VALUES (140, 'A10131', '9222019', '2019-11-24', '12:05', '2020-01-20', '1:00', 0, 'VISA', 123456, '02/21', 139);

-- different city rentals
INSERT INTO Rental(rID ,vLicense, driversLicense , fromDate , fromTime, toDate, toTime, odometer,cardName,cardNo ,expDate, confNo)
VALUES (201, 'B10111', '9222019', '2019-11-24', '12:05', '2020-01-20', '1:00', 0, 'VISA', 123456, '02/21', 200);

INSERT INTO Rental(rID ,vLicense, driversLicense , fromDate , fromTime, toDate, toTime, odometer,cardName,cardNo ,expDate, confNo)
VALUES (202, 'B10112', '9222019', '2019-11-24', '12:05', '2020-01-20', '1:00', 0, 'VISA', 123456, '02/21', 201);

INSERT INTO Rental(rID ,vLicense, driversLicense , fromDate , fromTime, toDate, toTime, odometer,cardName,cardNo ,expDate, confNo)
VALUES (203, 'D10111', '9222019', '2019-11-24', '12:05', '2020-01-20', '1:00', 0, 'VISA', 123456, '02/21', 202);

INSERT INTO Rental(rID ,vLicense, driversLicense , fromDate , fromTime, toDate, toTime, odometer,cardName,cardNo ,expDate, confNo)
VALUES (204, 'D10112', '9222019', '2019-11-24', '12:05', '2020-01-20', '1:00', 0, 'VISA', 123456, '02/21', 203);

INSERT INTO Rental(rID ,vLicense, driversLicense , fromDate , fromTime, toDate, toTime, odometer,cardName,cardNo ,expDate, confNo)
VALUES (205, 'D10113', '9222019', '2019-11-24', '12:05', '2020-01-20', '1:00', 0, 'VISA', 123456, '02/21', 204);

INSERT INTO Rental(rID ,vLicense, driversLicense , fromDate , fromTime, toDate, toTime, odometer,cardName,cardNo ,expDate, confNo)
VALUES (206, 'D10114', '9222019', '2019-11-24', '12:05', '2020-01-20', '1:00', 0, 'VISA', 123456, '02/21', 205);

INSERT INTO Rental(rID ,vLicense, driversLicense , fromDate , fromTime, toDate, toTime, odometer,cardName,cardNo ,expDate, confNo)
VALUES (207, 'D10115', '9222019', '2019-11-24', '12:05', '2020-01-20', '1:00', 0, 'VISA', 123456, '02/21', 206);

INSERT INTO Rental(rID ,vLicense, driversLicense , fromDate , fromTime, toDate, toTime, odometer,cardName,cardNo ,expDate, confNo)
VALUES (208, 'D10116', '9222019', '2019-11-24', '12:05', '2020-01-20', '1:00', 0, 'VISA', 123456, '02/21', 207);

INSERT INTO Rental(rID ,vLicense, driversLicense , fromDate , fromTime, toDate, toTime, odometer,cardName,cardNo ,expDate, confNo)
VALUES (209, 'D10117', '9222019', '2019-11-24', '12:05', '2020-01-20', '1:00', 0, 'VISA', 123456, '02/21', 208);

INSERT INTO Rental(rID ,vLicense, driversLicense , fromDate , fromTime, toDate, toTime, odometer,cardName,cardNo ,expDate, confNo)
VALUES (210, 'D10118', '9222019', '2019-11-24', '12:05', '2020-01-20', '1:00', 0, 'VISA', 123456, '02/21', 209);

INSERT INTO Rental(rID ,vLicense, driversLicense , fromDate , fromTime, toDate, toTime, odometer,cardName,cardNo ,expDate, confNo)
VALUES (211, 'D10119', '9222019', '2019-11-24', '12:05', '2020-01-20', '1:00', 0, 'VISA', 123456, '02/21', 210);

INSERT INTO Rental(rID ,vLicense, driversLicense , fromDate , fromTime, toDate, toTime, odometer,cardName,cardNo ,expDate, confNo)
VALUES (212, 'D10120', '9222019', '2019-11-24', '12:05', '2020-01-20', '1:00', 0, 'VISA', 123456, '02/21', 211);

INSERT INTO Rental(rID ,vLicense, driversLicense , fromDate , fromTime, toDate, toTime, odometer,cardName,cardNo ,expDate, confNo)
VALUES (213, 'D10121', '9222019', '2019-11-24', '12:05', '2020-01-20', '1:00', 0, 'VISA', 123456, '02/21', 212);

INSERT INTO Rental(rID ,vLicense, driversLicense , fromDate , fromTime, toDate, toTime, odometer,cardName,cardNo ,expDate, confNo)
VALUES (214, 'D10122', '9222019', '2019-11-24', '12:05', '2020-01-20', '1:00', 0, 'VISA', 123456, '02/21', 213);

INSERT INTO Return(rID, returnDate, odometer, fullTank, value)
VALUES(125, '2019-11-25', 5, 1, 510);

INSERT INTO Return(rID, returnDate, odometer, fullTank, value)
VALUES(126, '2020-01-20', 5, 1, 410);

INSERT INTO Return(rID, returnDate, odometer, fullTank, value)
VALUES(127, '2020-01-20', 5, 1, 410);

INSERT INTO Return(rID, returnDate, odometer, fullTank, value)
VALUES(128, '2020-01-20', 5, 1, 410);

INSERT INTO Return(rID, returnDate, odometer, fullTank, value)
VALUES(129, '2020-01-20', 5, 1, 410);

INSERT INTO Return(rID, returnDate, odometer, fullTank, value)
VALUES(130, '2020-01-20', 5, 1, 410);

INSERT INTO Return(rID, returnDate, odometer, fullTank, value)
VALUES(131, '2020-01-20', 5, 1, 410);

INSERT INTO Return(rID, returnDate, odometer, fullTank, value)
VALUES(132, '2020-01-20', 5, 1, 410);

INSERT INTO Return(rID, returnDate, odometer, fullTank, value)
VALUES(133, '2020-01-20', 5, 1, 410);

INSERT INTO Return(rID, returnDate, odometer, fullTank, value)
VALUES(134, '2020-01-20', 5, 1, 410);

INSERT INTO Return(rID, returnDate, odometer, fullTank, value)
VALUES(135, '2020-01-20', 5, 1, 410);

INSERT INTO Return(rID, returnDate, odometer, fullTank, value)
VALUES(136, '2020-01-20', 5, 1, 410);

INSERT INTO Return(rID, returnDate, odometer, fullTank, value)
VALUES(137, '2020-01-20', 5, 1, 410);

INSERT INTO Return(rID, returnDate, odometer, fullTank, value)
VALUES(138, '2020-01-20', 5, 1, 410);

INSERT INTO Return(rID, returnDate, odometer, fullTank, value)
VALUES(139, '2020-01-20', 5, 1, 410);

INSERT INTO Return(rID, returnDate, odometer, fullTank, value)
VALUES(140, '2020-01-20', 5, 1, 410);

INSERT INTO Return(rID, returnDate, odometer, fullTank, value)
VALUES(201, '2020-01-20', 5, 1, 410);

INSERT INTO Return(rID, returnDate, odometer, fullTank, value)
VALUES(202, '2020-01-20', 5, 1, 410);

INSERT INTO Return(rID, returnDate, odometer, fullTank, value)
VALUES(203, '2020-01-20', 5, 1, 410);

INSERT INTO Return(rID, returnDate, odometer, fullTank, value)
VALUES(204, '2020-01-20', 5, 1, 410);

INSERT INTO Return(rID, returnDate, odometer, fullTank, value)
VALUES(205, '2020-01-20', 5, 1, 410);

INSERT INTO Return(rID, returnDate, odometer, fullTank, value)
VALUES(206, '2020-01-20', 5, 1, 410);

INSERT INTO Return(rID, returnDate, odometer, fullTank, value)
VALUES(207, '2020-01-20', 5, 1, 410);

INSERT INTO Return(rID, returnDate, odometer, fullTank, value)
VALUES(208, '2020-01-20', 5, 1, 410);

INSERT INTO Return(rID, returnDate, odometer, fullTank, value)
VALUES(209, '2020-01-20', 5, 1, 410);

INSERT INTO Return(rID, returnDate, odometer, fullTank, value)
VALUES(210, '2020-01-20', 5, 1, 410);

INSERT INTO Return(rID, returnDate, odometer, fullTank, value)
VALUES(211, '2020-01-20', 5, 1, 410);

INSERT INTO Return(rID, returnDate, odometer, fullTank, value)
VALUES(212, '2020-01-20', 5, 1, 410);

INSERT INTO Return(rID, returnDate, odometer, fullTank, value)
VALUES(213, '2020-01-20', 5, 1, 410);

INSERT INTO Return(rID, returnDate, odometer, fullTank, value)
VALUES(214, '2020-01-20', 5, 1, 410);