package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.DatabaseActionsDelegate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class InsertIntoTableWindow extends Window implements ActionListener {
    private JButton submit;
    private JButton backToDatabase;
    private JTextField tableNameField;
    private JTextField columnsField;
    private JTextField valuesField;

    private DatabaseActionsDelegate databaseActionsDelegate = null;

    public InsertIntoTableWindow() {
        super("Insert data into table");

        tableNameField = new JTextField(TEXT_FIELD_WIDTH);
        columnsField = new JTextField(TEXT_FIELD_WIDTH);
        valuesField = new JTextField(TEXT_FIELD_WIDTH);

        submit = new JButton("Submit");
        backToDatabase = new JButton("Back");
    }

    public void showMenu(DatabaseActionsDelegate databaseActionsDelegate) {
        this.databaseActionsDelegate = databaseActionsDelegate;

        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);

        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        contentPane.setLayout(gb);
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        placeFieldAndLabel("Table Name", tableNameField, contentPane, gb, c);
        placeFieldAndLabel("Columns", columnsField, contentPane, gb, c);
        placeFieldAndLabel("Values", valuesField, contentPane, gb, c);

        List<JButton> buttons = new ArrayList<>();
        buttons.add(submit);
        buttons.add(backToDatabase);
        PanelConstraints setConstraints = (JButton button) -> { setButtonConstraints(gb, c, button); };
        new Panel(buttons, this, this, contentPane, gb, setConstraints);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {
            String tableName = tableNameField.getText();
            String columns = columnsField.getText();
            String values = valuesField.getText();
            databaseActionsDelegate.insertIntoTable(tableName, columns, values);
            tableNameField.setText("");
            columnsField.setText("");
            valuesField.setText("");
        } else if (e.getSource() == backToDatabase) {
            databaseActionsDelegate.backToDatabaseMenu();
        }
    }
}
