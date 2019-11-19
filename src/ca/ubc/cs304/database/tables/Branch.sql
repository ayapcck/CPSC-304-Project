CREATE TABLE Branch(
    location VARCHAR(255) not null,
    city VARCHAR(255) not null,
    PRIMARY KEY (location, city)
);