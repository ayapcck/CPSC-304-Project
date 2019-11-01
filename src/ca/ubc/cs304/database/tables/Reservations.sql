CREATE TABLE Reservations(
    confNo INTEGER,
    VTName VARCHAR(255),
    cellNum VARCHAR(255),
    fromDate date,
    fromTime VARCHAR(255),
    toDate date,
    toTime VARCHAR(255),
    PRIMARY KEY (confNo),
    FOREIGN KEY (VTName) REFERENCES VehicleType,
    FOREIGN KEY (cellNum) REFERENCES Customer,
    FOREIGN KEY (fromDate, fromTime, toDate, toTime) REFERENCES TIMEPERIOD
);