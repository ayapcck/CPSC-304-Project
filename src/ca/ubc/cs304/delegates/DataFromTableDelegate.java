package ca.ubc.cs304.delegates;

import java.util.List;

public interface DataFromTableDelegate {
    void submit(String tableName, String columns);
    void backToDatabase();
}
