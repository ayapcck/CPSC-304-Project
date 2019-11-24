package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.ViewVehiclesResultDelegate;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

/**
 * The class is only responsible for displaying and handling the login GUI.
 */
public class ViewAvailableVehicleResultWindow extends Window implements ActionListener {
    private static final int TEXT_FIELD_WIDTH = 10;
    private int count;
    private ViewVehiclesResultDelegate viewVehiclesResultDelegate = null;
    private JPanel contentPane;
    private GridBagLayout gb = new GridBagLayout();
    private GridBagConstraints c = new GridBagConstraints();

    private String carType;
    private String location;
    private String city;
    private JButton mainMenu;
    private JButton moreDetail;
    private JButton back;
    public ViewAvailableVehicleResultWindow() {
        super("Number of vehicles available");

        mainMenu = new JButton("Back");
        moreDetail = new JButton("More detail");
    }

    public void showMenu(ViewVehiclesResultDelegate viewVehiclesResultDelegate, int count, String carType, String location, String city) {
        this.viewVehiclesResultDelegate = viewVehiclesResultDelegate;
        this.count = count;
        this.carType = carType;
        this.location = location;
        this.city = city;

        contentPane = new JPanel();
        this.setContentPane(contentPane);

        contentPane.setLayout(gb);
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // show count of vehicles
        JLabel countResult = new JLabel("There are " + count + " vehicles of this type.");

        this.setContentPane(contentPane);
        c =  new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(10, 10, 5, 0);
        gb.setConstraints(countResult, c);
        contentPane.add(countResult);

        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(5, 10, 10, 10);
        c.anchor = GridBagConstraints.CENTER;
        gb.setConstraints(moreDetail, c);
        contentPane.add(moreDetail);

        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(0, 10, 10, 10);
        c.anchor = GridBagConstraints.CENTER;
        gb.setConstraints(mainMenu, c);
        contentPane.add(mainMenu);


        moreDetail.addActionListener(this);
        mainMenu.addActionListener(this);

        // anonymous inner class for closing the window
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        // size the window to obtain a best fit for the components
        this.pack();

        // center the frame
        Dimension d = this.getToolkit().getScreenSize();
        Rectangle r = this.getBounds();
        this.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );

        // make the window visible
        this.setVisible(true);

        // place the cursor in the text field for the username
        moreDetail.requestFocus();
    }

    public void showMoreDetail(JTable rs) {
        contentPane = new JPanel();
        c = new GridBagConstraints();
        this.setContentPane(contentPane);
        JScrollPane jsp = new JScrollPane(rs);
        jsp.setPreferredSize(new Dimension (900, 250));
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(5, 10, 10, 10);
        c.anchor = GridBagConstraints.CENTER;
        gb.setConstraints(jsp, c);
        contentPane.add(jsp);
        back = new JButton("back");
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(5, 10, 5, 10);
        c.anchor = GridBagConstraints.CENTER;
        gb.setConstraints(back, c);
        contentPane.add(back);
        back.addActionListener(this);

        // anonymous inner class for closing the window
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.pack();
        Dimension d = this.getToolkit().getScreenSize();
        Rectangle r = this.getBounds();
        this.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );
        // make the window visible
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == moreDetail) {
            viewVehiclesResultDelegate.showDetailCountResult(count, carType, location, city);
        } else if (actionEvent.getSource() == mainMenu) {
            viewVehiclesResultDelegate.backToCustomer();
        } else if (actionEvent.getSource() == back) {
            viewVehiclesResultDelegate.backToView();
        }
    }
}
