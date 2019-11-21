package ca.ubc.cs304.controller;

import ca.ubc.cs304.delegates.CustomerTransactionDelegate;
import ca.ubc.cs304.ui.MainOperations;
import sun.applet.Main;

import javax.swing.*;

public class CustomerController implements CustomerTransactionDelegate {
    private JFrame currentWindow = null;
    private MainController mainController = null;
    private MainOperations mainOperations = null;

    public CustomerController(JFrame currentWindow) {
        this.currentWindow = currentWindow;
    }

    @Override
    public void mainMenu() {
        currentWindow.dispose();
        mainOperations = new MainOperations();
        mainController = new MainController(mainOperations);
        mainOperations.showMenu(mainController);
    }
}
