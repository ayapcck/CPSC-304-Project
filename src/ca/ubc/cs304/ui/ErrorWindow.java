package ca.ubc.cs304.ui;

import javax.swing.JOptionPane;

public class ErrorWindow
{

    public static void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, "Oops: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
}
