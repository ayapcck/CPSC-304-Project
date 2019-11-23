package ca.ubc.cs304.delegates;

public interface DatabaseMenuDelegate {
    void setupDatabase();
    void dropRequiredTables();
    void addRequiredTablesAndData();
    void viewAllTables();
    void navToDeleteDataWindow();
    void navToSelectDataWindow();
    void navToInsertDataWindow();
    void mainMenu();
}
