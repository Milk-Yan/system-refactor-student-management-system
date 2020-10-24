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
        StudentMgr.getInstance();
        CourseMgr.getInstance();
        CourseRegistrationMgr.getInstance();
        MarkMgr.getInstance();
        ProfessorMgr.getInstance();
        MainMenuIO.printWelcome();
        MainMenuIO.startMainMenu();
    }
}
