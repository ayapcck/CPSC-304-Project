CREATE TABLE Rental(
    rID INTEGER,
    vID INTEGER,
    cellNum VARCHAR(255),
    fromDate DATE,
    fromTime VARCHAR(255),
    toDate DATE,
    toTime VARCHAR(255),
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