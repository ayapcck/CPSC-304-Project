CREATE TABLE Return(
    rID INTEGER not null,
    returnDate DATE,
    odometer INTEGER,
    fullTank NUMBER(1),
    value INTEGER,
    PRIMARY KEY (rID),
    FOREIGN KEY (rID) REFERENCES Rental
);