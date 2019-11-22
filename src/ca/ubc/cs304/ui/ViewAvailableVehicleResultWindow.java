package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.ProcessViewDelegate;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;

import javax.swing.*;

/**
 * The class is only responsible for displaying and handling the login GUI.
 */
public class ViewAvailableVehicleResultWindow extends JFrame implements ActionListener {
    private static final int TEXT_FIELD_WIDTH = 10;
    private ProcessViewDelegate processViewDelegate = null;
    private JPanel contentPane;
    private GridBagConstraints c = new GridBagConstraints();
    private String carType;
    private String location;
    private String city;
    private Date fromDate;
    private Date toDate;
    private JButton mainMenu;
    private JButton moreDetail;
    private GridBagLayout gb = new GridBagLayout();
    public ViewAvailableVehicleResultWindow() {
        super("show number of vehicles available");
    }

    public void showMenu(ProcessViewDelegate processViewDelegate, int count, String carType, String location, String city, Date fromDate, Date toDate) {

        this.carType = carType;
        this.location = location;
        this.city = city;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.processViewDelegate = processViewDelegate;
        JLabel countResult = new JLabel("There are " + count + " vehicles of this type.");

        contentPane = new JPanel();
        this.setContentPane(contentPane);

        contentPane.setLayout(gb);
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // show count of vehicles
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(10, 10, 5, 0);
        gb.setConstraints(countResult, c);
        contentPane.add(countResult);

        // place back button
        mainMenu = new JButton("Back");
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(5, 10, 10, 10);
        c.anchor = GridBagConstraints.CENTER;
        gb.setConstraints(mainMenu, c);
        contentPane.add(mainMenu);

        // place moreDetail button
        moreDetail = new JButton("More detail");
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(5, 10, 10, 10);
        c.anchor = GridBagConstraints.CENTER;
        gb.setConstraints(moreDetail, c);
        contentPane.add(moreDetail);

        // register login button with action event handler
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
    }

    public void showMoreDetail(JTable rs) {
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(5, 10, 10, 10);
        c.anchor = GridBagConstraints.CENTER;
        gb.setConstraints(rs, c);
        contentPane.add(rs);
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
            processViewDelegate.showDetailCountResult(carType, location, city, fromDate, toDate);
        } else if (actionEvent.getSource() == mainMenu) {
            processViewDelegate.backToPrevious();
        }
    }
}
