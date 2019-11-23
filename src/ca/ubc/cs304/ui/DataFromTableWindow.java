package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.DataFromTableDelegate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class DataFromTableWindow extends Window implements ActionListener {
    private JButton submit;
    private JButton backToDatabase;
    private JTextField tableNameField;
    private JTextField columnsField;

    private DataFromTableDelegate dataFromTableDelegate = null;

    public DataFromTableWindow() {
        super("Retrieve data from table");

        submit = new JButton("Submit");
        backToDatabase = new JButton("Back");
    }

    public void showMenu(DataFromTableDelegate dataFromTableDelegate) {
        this.dataFromTableDelegate = dataFromTableDelegate;


        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);

        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        contentPane.setLayout(gb);
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel tableLabel = new JLabel("Table Name: ");
        tableNameField = new JTextField(TEXT_FIELD_WIDTH);
        placeLabel(tableLabel, contentPane, gb, c, 10 ,5);
        placeTextField(tableNameField, contentPane, gb, c, TEXT_FIELD_INSET);

        JLabel columnsLabel = new JLabel("Columns: ");
        columnsField = new JTextField(TEXT_FIELD_WIDTH);
        placeLabel(columnsLabel, contentPane, gb, c, 10, 5);
        placeTextField(columnsField, contentPane, gb, c, TEXT_FIELD_INSET);

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
            dataFromTableDelegate.submit(tableName, columns);
        } else if (e.getSource() == backToDatabase) {
            dataFromTableDelegate.backToDatabase();
        }
    }
}
