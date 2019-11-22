package ca.ubc.cs304.controller;

import java.sql.Connection;

public class ClerkTransaction {
    private Connection connection = null;
    public ClerkTransaction(Connection connection) {
        this.connection = connection;
    }
}
