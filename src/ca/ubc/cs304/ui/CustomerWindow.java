package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.ClerkTransactionDelegate;
import ca.ubc.cs304.delegates.CusEnterViewDelegate;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * The class is only responsible for displaying and handling the login GUI.
 */
public class CustomerWindow extends JFrame implements ActionListener {
    private JButton view;
    private JButton reserve;
    private JButton back;
    private CusEnterViewDelegate cusEnterViewDelegate = null;

    public CustomerWindow() {
        super("Choose a transaction:");

        view = new JButton("View Available Vehicles");
        reserve = new JButton("Reserve");
        back = new JButton("Back");
    }

    public void showMenu(CusEnterViewDelegate cusEnterViewDelegate) {
        this.cusEnterViewDelegate = cusEnterViewDelegate;

        List<JButton> buttons = new ArrayList<>();
        buttons.add(view);
        buttons.add(reserve);
        buttons.add(back);
        new Panel(buttons, this, this);
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == view) {
            cusEnterViewDelegate.submitView();
        } else if (actionEvent.getSource() == back) {
            cusEnterViewDelegate.back();
        } else if (actionEvent.getSource() == reserve) {
            // TODO
        }
    }
}
