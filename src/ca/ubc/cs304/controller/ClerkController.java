package ca.ubc.cs304.controller;

import ca.ubc.cs304.delegates.ClerkTransactionDelegate;
import ca.ubc.cs304.ui.MainOperations;

import javax.swing.*;

public class ClerkController implements ClerkTransactionDelegate {
    private JFrame currentWindow = null;

    public ClerkController(JFrame currentWindow) {
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
