package ca.ubc.cs304.delegates;

public interface DatabaseManipulationsDelegate {
    void setupDatabase();
    void dropRequiredTables();
    void addRequiredTablesAndData();
    void viewAllTables();
    void backToMain();
}
