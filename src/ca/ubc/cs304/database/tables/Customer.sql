CREATE TABLE Customer(
    cellNum VARCHAR(255),
    name VARCHAR(255),
    address VARCHAR(255),
    driversLicense VARCHAR(255) not null,
    PRIMARY KEY (driversLicense)
);