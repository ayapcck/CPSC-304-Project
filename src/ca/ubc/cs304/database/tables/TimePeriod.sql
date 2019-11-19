CREATE TABLE TimePeriod(
    fromDate DATE not null,
    fromTime VARCHAR(255) not null,
    toDate DATE not null,
    toTime VARCHAR(255) not null,
    PRIMARY KEY (fromDate, fromTime, toDate, toTime)
);