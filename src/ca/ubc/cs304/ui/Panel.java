package ca.ubc.cs304.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class Panel {
    private List<JButton> buttons = new ArrayList<>();
    private JPanel contentPane;

    public Panel(List<JButton> buttonLabels, JFrame frame, ActionListener al) {
        contentPane = new JPanel();
        frame.setContentPane(contentPane);

        // layout components using the GridBag layout manager
        FlowLayout flowLayout = new FlowLayout();

        contentPane.setLayout(flowLayout);
        for (JButton button : buttonLabels) {
            contentPane.add(button);
            button.addActionListener(al);
        }
        // anonymous inner class for closing the window
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        // size the window to obtain a best fit for the components
        frame.pack();

        // center the frame
        Dimension d = frame.getToolkit().getScreenSize();
        Rectangle r = frame.getBounds();
        frame.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );

        // make the window visible
        frame.setVisible(true);
    }
}
