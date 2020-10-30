package com.softeng306.main;

import com.softeng306.io.MainMenuIO;

/**
 * Startup class - Starts the program.
 */
public class Main {

    /**
     * The main function of the system.
     * Prints the welcome message.
     * Starts the program.
     *
     * @param args The command line parameters.
     */
    public static void main(String[] args) {
        MainMenuIO.printWelcome();
        MainMenuIO.startMainMenu();
    }
}
