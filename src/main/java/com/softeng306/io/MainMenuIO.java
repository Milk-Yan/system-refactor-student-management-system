package com.softeng306.io;

import com.softeng306.managers.CourseMgr;
import com.softeng306.managers.MarkMgr;

import java.util.Scanner;

public class MainMenuIO {
    public static Scanner scanner = new Scanner(System.in);

    private static CourseRegistrationMgrIO courseRegistrationMgrIO = new CourseRegistrationMgrIO();
    private static StudentMgrIO studentMgrIO = new StudentMgrIO();
    private static MarkMgrIO markMgrIO = new MarkMgrIO();
    private static CourseMgrIO courseMgrIO = new CourseMgrIO();


    /**
     * Startup the main menu for the application
     */
    public static void startMainMenu() {
        int choice = 0;
        int lowestChoiceInt = 0;
        int highestChoiceInt = 11;
        while (choice != 11) {
            printOptions();
            choice = getIntInputFromUser(lowestChoiceInt, highestChoiceInt);
            doActionFromUserChoice(choice);
        }
    }

    /**
     * Prompts the user to enter an integer between the specified limits
     *
     * @param lowerLimit The lowest valid number for a user to enter
     * @param upperLimit The highest valid number for a user to enter
     * @return
     */
    private static int getIntInputFromUser(int lowerLimit, int upperLimit) {
        int choice;

        while (true) {
            System.out.println("Enter your choice, let me help you:");
            while (!scanner.hasNextInt()) {
                System.out.println("Sorry. " + scanner.nextLine() + " is not an integer.");
                System.out.println("Enter your choice, let me help you:");
            }
            choice = scanner.nextInt();
            scanner.nextLine();
            if (choice < lowerLimit || choice > upperLimit) {
                System.out.println("Please enter " + lowerLimit + " ~ " + upperLimit + " for your choice:");
            } else {
                return choice;
            }
        }
    }

    private static void doActionFromUserChoice(int choice) {
        // Choose command based on user input choice
        switch (choice) {
            case 0:
                break;
            case 1:
                studentMgrIO.addStudent();
                break;
            case 2:
                courseMgrIO.addCourse();
                break;
            case 3:
                courseRegistrationMgrIO.registerCourse();
                break;
            case 4:
                courseMgrIO.checkAvailableSlots();
                break;
            case 5:
                courseRegistrationMgrIO.printStudents();
                break;
            case 6:
                courseMgrIO.enterCourseWorkComponentWeightage();
                break;
            case 7:
                markMgrIO.initiateEnteringCourseworkMark(false);
                break;
            case 8:
                markMgrIO.initiateEnteringCourseworkMark(true);
                break;
            case 9:
                courseMgrIO.printCourseStatistics();
                break;
            case 10:
                studentMgrIO.printStudentTranscript();
                break;
            case 11:
                exitApplication();
                break;
        }
    }

    /**
     * Displays the welcome message.
     */
    public static void printWelcome() {
        System.out.println();
        System.out.println("****************** Hello! Welcome to SOFTENG 306 Project 2! ******************");
        System.out.println("Please note this application is not developed in The University of Auckland. All rights reserved for the original developers.");
        System.out.println("Permission has been granted by the original developers to anonymize the code and use for education purposes.");
        System.out.println("******************************************************************************************************************************");
        System.out.println();
    }

    /**
     * Displays the exiting message.
     */
    public static void exitApplication() {

        System.out.println("Backing up data before exiting...");
        // we don't need to back up the data anymore
        System.out.println("********* Bye! Thank you for using Main! *********");
        System.out.println();
        System.out.println("                 ######    #      #   #######                   ");
        System.out.println("                 #    ##    #    #    #                         ");
        System.out.println("                 #    ##     #  #     #                         ");
        System.out.println("                 ######       ##      #######                   ");
        System.out.println("                 #    ##      ##      #                         ");
        System.out.println("                 #    ##      ##      #                         ");
        System.out.println("                 ######       ##      #######                   ");
        System.out.println();

    }

    /**
     * Displays all the options of the system.
     */
    public static void printOptions() {
        System.out.println("************ I can help you with these functions: *************");
        System.out.println(" 0. Print Options");
        System.out.println(" 1. Add a student");
        System.out.println(" 2. Add a course");
        System.out.println(" 3. Register student for a course including tutorial/lab classes");
        System.out.println(" 4. Check available slots in a class (vacancy in a class)");
        System.out.println(" 5. Print student list by lecture, tutorial or laboratory session for a course");
        System.out.println(" 6. Enter course assessment components weightage");
        System.out.println(" 7. Enter coursework mark – inclusive of its components");
        System.out.println(" 8. Enter exam mark");
        System.out.println(" 9. Print course statistics");
        System.out.println("10. Print student transcript");
        System.out.println("11. Quit Main System");
        System.out.println();
    }

    /**
     * Prints the method call
     */
    public static void printMethodCall(String methodName) {
        System.out.println(methodName + " is called");
    }
}
