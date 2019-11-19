CREATE TABLE Customer(
    cellNum VARCHAR(255),
    name VARCHAR(255),
    address VARCHAR(255),
    driversLicense VARCHAR(255),
    PRIMARY KEY (cellNum)
);

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
