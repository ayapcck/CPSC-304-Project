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
    private int count;
    private ViewVehiclesResultDelegate viewVehiclesResultDelegate = null;
    private JPanel contentPane;
    private GridBagLayout gb = new GridBagLayout();
    private GridBagConstraints c = new GridBagConstraints();

    private String carType;
    private String location;
    private String city;
    private Date fromDate;
    private Date toDate;
    private JButton backToCustomerMenu;
    private JButton moreDetail;
    private JButton back;
    public ViewAvailableVehicleResultWindow() {
        super("Number of vehicles available");

        backToCustomerMenu = new JButton("Back");
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
        placeLabel(countResult, contentPane, gb, c, 10, 5);
        // TODO: need to fix positioning on this label
        //  should set to REMAINDER instead of RELATIVE

        List<JButton> buttons = new ArrayList<>();
        buttons.add(moreDetail);
        buttons.add(backToCustomerMenu);
        PanelConstraints setConstraints = (JButton button) -> { setButtonConstraints(gb, c, button); };
        new Panel(buttons, this, this, contentPane, gb, setConstraints);
    }

    public void showMoreDetail(ViewVehiclesResultDelegate viewVehiclesResultDelegate, JTable rs) {
        this.viewVehiclesResultDelegate = viewVehiclesResultDelegate;
        contentPane = new JPanel();
        c = new GridBagConstraints();
        this.setContentPane(contentPane);
        JScrollPane jsp = new JScrollPane(rs);
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(5, 10, 10, 10);
        c.anchor = GridBagConstraints.CENTER;
        gb.setConstraints(jsp, c);
        contentPane.add(jsp);
        contentPane.add(backToCustomerMenu);
        backToCustomerMenu.addActionListener(this);
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
        } else if (actionEvent.getSource() == backToCustomerMenu) {
            viewVehiclesResultDelegate.backToCustomer();
        } else if (actionEvent.getSource() == back) {
            viewVehiclesResultDelegate.backToView();
        }
    }
}
