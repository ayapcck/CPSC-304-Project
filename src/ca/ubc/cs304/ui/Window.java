package ca.ubc.cs304.ui;

import ca.ubc.cs304.util.DateLabelFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.util.Properties;

class Window extends JFrame {
    static final int TEXT_FIELD_WIDTH = 10;
    static final Insets TEXT_FIELD_INSET = new Insets(10, 0, 5, 10);

    Window(String label) {
        super(label);
    }

    JDatePickerImpl placeDateField(JPanel contentPane, GridBagLayout gb, GridBagConstraints c,
                                           Insets insets) {
        UtilDateModel model2 = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanelTo = new JDatePanelImpl(model2, p);
        JDatePickerImpl picker = new JDatePickerImpl(datePanelTo, new DateLabelFormatter());
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = insets;
        gb.setConstraints(picker, c);
        contentPane.add(picker);
        return picker;
    }

    void placeFieldAndLabel(String label, JTextField jTextField,
                            JPanel contentPane, GridBagLayout gb, GridBagConstraints c) {
        String labelWithExtra = label.concat(": ");
        JLabel jLabel = new JLabel(labelWithExtra);
        placeLabel(jLabel, contentPane, gb, c, 10, 5);
        placeTextField(jTextField, contentPane, gb, c, TEXT_FIELD_INSET);
    }

    void placeLabel(String label, JPanel contentPane, GridBagLayout gb, GridBagConstraints c, int i, int i2) {
        this.placeLabel(new JLabel(label), contentPane, gb, c, i, i2);
    }

    void placeLabel(JLabel label, JPanel contentPane, GridBagLayout gb, GridBagConstraints c, int i, int i2) {
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(i, 10, i2, 0);
        gb.setConstraints(label, c);
        contentPane.add(label);
    }

    void placeLabelRemainder(JLabel label, JPanel contentPane, GridBagLayout gb, GridBagConstraints c) {
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(0, 10, 10, 0);
        gb.setConstraints(label, c);
        contentPane.add(label);
    }

    void placeTextField(JTextField field, JPanel contentPane, GridBagLayout gb,
                          GridBagConstraints c, Insets insets) {
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = insets;
        gb.setConstraints(field, c);
        contentPane.add(field);
    }


    void clearFields(JTextField[] fields) {
        for (JTextField field : fields) {
            field.setText("");
        }
    }


    void setButtonConstraints(GridBagLayout gb, GridBagConstraints c, JButton button) {
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(5, 10, 10, 10);
        c.anchor = GridBagConstraints.CENTER;
        gb.setConstraints(button, c);
    }
}
