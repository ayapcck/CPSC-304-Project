CREATE TABLE Branch(
    location VARCHAR(255),
    city VARCHAR(255),
    PRIMARY KEY (location, city)
);

INSERT INTO Branch (location, city) VALUES ('shop_1', 'Vancouver');
INSERT INTO Branch (location, city) VALUES ('shop_2', 'Vancouver');
INSERT INTO Branch (location, city) VALUES ('shop_3', 'Vancouver');
INSERT INTO Branch (location, city) VALUES ('shop_1', 'Burnaby');
INSERT INTO Branch (location, city) VALUES ('shop_2', 'Burnaby');
