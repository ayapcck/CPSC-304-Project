CREATE TABLE TimePeriod(
    fromDate date,
    fromTime VARCHAR(255),
    toDate date,
    toTime VARCHAR(255),
    PRIMARY KEY (fromDate, fromTime, toDate, toTime)
);