CREATE TABLE TimePeriod(
    fromDate DATE,
    fromTime VARCHAR(255),
    toDate DATE,
    toTime VARCHAR(255),
    PRIMARY KEY (fromDate, fromTime, toDate, toTime)
);