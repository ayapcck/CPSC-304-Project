package ca.ubc.cs304.delegates;

public interface DatabaseActionsDelegate {
    void insertIntoTable();
    void deleteFromTable(String tableName, String column, String value);
    void selectFromTable(String tableName, String columns);
    void updateTable();
    void backToDatabaseMenu();
}
