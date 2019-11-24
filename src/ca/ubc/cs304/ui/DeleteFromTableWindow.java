package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.DatabaseActionsDelegate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class DeleteFromTableWindow extends Window implements ActionListener {
    private JButton submit;
    private JButton backToDatabase;
    private JTextField tableNameField;
    private JTextField columnField;
    private JTextField valueField;

    private DatabaseActionsDelegate databaseActionsDelegate = null;

    public DeleteFromTableWindow() {
        super("Delete data from table");

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

        tableNameField = new JTextField(TEXT_FIELD_WIDTH);
        placeFieldAndLabel("Table Name", tableNameField, contentPane, gb, c);
        columnField = new JTextField(TEXT_FIELD_WIDTH);
        placeFieldAndLabel("Column in WHERE", columnField, contentPane, gb, c);
        valueField = new JTextField(TEXT_FIELD_WIDTH);
        placeFieldAndLabel("Value in WHERE", valueField, contentPane, gb, c);

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
            String column = columnField.getText();
            String value = valueField.getText();
            databaseActionsDelegate.deleteFromTable(tableName, column, value);
            tableNameField.setText("");
            columnField.setText("");
            valueField.setText("");
        } else if (e.getSource() == backToDatabase) {
            databaseActionsDelegate.backToDatabaseMenu();
        }
    }
}
