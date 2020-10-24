package com.softeng306.main;

import com.softeng306.io.MainMenuIO;
import com.softeng306.managers.*;

public class Main {

    /**
     * The main function of the system.
     * Command line interface.
     *
     * @param args The command line parameters.
     */
    public static void main(String[] args) {
        MainMenuIO.printWelcome();
        MainMenuIO.startMainMenu();
    }
}
