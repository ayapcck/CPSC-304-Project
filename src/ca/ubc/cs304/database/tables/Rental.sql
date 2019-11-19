CREATE TABLE Rental(
    rID INTEGER not null,
    vID INTEGER null,
    cellNum VARCHAR(255) null ,
    fromDate DATE null ,
    fromTime VARCHAR(255) null,
    toDate DATE null,
    toTime VARCHAR(255) null,
    odometer INTEGER null,
    cardName VARCHAR(255) null,
    cardNo VARCHAR(255) null,
    expDate VARCHAR(255) null,
    confNo INTEGER null,
    PRIMARY KEY (rID),
    FOREIGN KEY (vID) REFERENCES Vehicle,
    FOREIGN KEY (cellNum) REFERENCES Customer,
    FOREIGN KEY (fromDate, fromTime, toDate, toTime) REFERENCES TimePeriod,
    FOREIGN KEY (confNo) REFERENCES Reservations
);