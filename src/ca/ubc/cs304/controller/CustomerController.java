package ca.ubc.cs304.controller;

import ca.ubc.cs304.delegates.CustomerTransactionDelegate;
import ca.ubc.cs304.ui.MainOperations;
import sun.applet.Main;

import javax.swing.*;

public class CustomerController implements CustomerTransactionDelegate {
    private JFrame currentWindow = null;

    public CustomerController(JFrame currentWindow) {
        this.currentWindow = currentWindow;
    }

    @Override
    public void mainMenu() {
        currentWindow.dispose();
        MainOperations mainOperations = new MainOperations();
        MainController mainController = new MainController(mainOperations);
        mainOperations.showMenu(mainController);
    }
}
