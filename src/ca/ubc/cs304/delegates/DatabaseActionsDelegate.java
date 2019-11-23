package ca.ubc.cs304.delegates;

public interface DatabaseActionsDelegate {
    void insertIntoTable(String tableName, String columns, String values);
    void deleteFromTable(String tableName, String column, String value);
    void selectFromTable(String tableName, String columns);
    void updateTable(String tableName, String columns, String values, String whereColumn, String whereValue);
    void backToDatabaseMenu();
}
