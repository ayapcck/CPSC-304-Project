package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.DatabaseActionsDelegate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class UpdateTableWindow extends Window implements ActionListener {
    private DatabaseActionsDelegate databaseActionsDelegate = null;

    private JButton submit;
    private JButton backToDatabaseMenu;
    private JTextField tableNameField;
    private JTextField columnsField;
    private JTextField valuesField;
    private JTextField whereColumnField;
    private JTextField whereValueField;
    private JTextField[] fields;

    public UpdateTableWindow() {
        super("Update data in table");

        tableNameField = new JTextField(TEXT_FIELD_WIDTH);
        columnsField = new JTextField(TEXT_FIELD_WIDTH);
        valuesField = new JTextField(TEXT_FIELD_WIDTH);
        whereColumnField = new JTextField(TEXT_FIELD_WIDTH);
        whereValueField = new JTextField(TEXT_FIELD_WIDTH);
        fields = new JTextField[]{tableNameField, columnsField, valuesField, whereColumnField, whereValueField};

        submit = new JButton("Submit");
        backToDatabaseMenu = new JButton("Back");
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
        placeFieldAndLabel("Where Column", whereColumnField, contentPane, gb, c);
        placeFieldAndLabel("Where Value", whereValueField, contentPane, gb, c);

        List<JButton> buttons = new ArrayList<>();
        buttons.add(submit);
        buttons.add(backToDatabaseMenu);
        PanelConstraints setConstraints = (JButton button) -> { setButtonConstraints(gb, c, button); };
        new Panel(buttons, this, this, contentPane, gb, setConstraints);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {
            databaseActionsDelegate.updateTable(tableNameField.getText(), columnsField.getText(),
                    valuesField.getText(), whereColumnField.getText(), whereValueField.getText());
            clearFields(fields);
        } else if (e.getSource() == backToDatabaseMenu) {
            databaseActionsDelegate.backToDatabaseMenu();
        }
    }
}
