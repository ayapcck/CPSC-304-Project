package ca.ubc.cs304.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.invoke.SerializedLambda;
import java.util.ArrayList;
import java.util.List;

class Panel {
    private List<JButton> buttons = new ArrayList<>();
    private JPanel contentPane;

    Panel(List<JButton> buttons, JFrame frame, ActionListener al) {
        contentPane = new JPanel();
        FlowLayout flowLayout = new FlowLayout();
        new Panel(buttons, frame, al, contentPane, flowLayout, null);
    }

    Panel(List<JButton> buttons, JFrame frame, ActionListener al,
          JPanel contentPane, LayoutManager layout, PanelConstraints c) {
        frame.setContentPane(contentPane);
        contentPane.setLayout(layout);

        for (JButton button : buttons) {
            if (c != null) {
                c.setConstraints(button);
            }
            contentPane.add(button);
            button.addActionListener(al);
        }

        finishInitializing(frame);
    }

    private void finishInitializing(JFrame frame) {
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
