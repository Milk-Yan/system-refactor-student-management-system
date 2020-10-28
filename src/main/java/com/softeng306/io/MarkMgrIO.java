package com.softeng306.io;

import java.util.List;
import java.util.Scanner;

public class MarkMgrIO {
    private static Scanner scanner = new Scanner(System.in);

    /**
     * Prints to console that a function has been called.
     */
    public static void printFunctionCall(String functionName) {
        System.out.println(functionName + " is called");
    }

    /**
     * Prints to console that a student is not registered for a course.
     */
    public static void printStudentNotRegisteredToCourse(String courseID) {
        System.out.println("This student haven't registered " + courseID);
    }

    /**
     * Prints to console a list of available course component choices.
     */
    public static void printCourseComponentChoices(List<String> availableChoices, List<Double> weights) {
        System.out.println("Here are the choices you can have: ");

        for (int i = 0; i < availableChoices.size(); i++) {
            System.out.println((i + 1) + ". " + availableChoices.get(i) + " Weight in Total: " + weights.get(i) + "%");
        }
        System.out.println((availableChoices.size() + 1) + ". Quit");
    }

    /**
     * Reads from console the name of a course components.
     */
    public static int readCourseComponentChoice(int numChoices) {
        int choice;
        System.out.println("Enter your choice");
        choice = scanner.nextInt();
        scanner.nextLine();

        while (choice > (numChoices + 1) || choice < 0) {
            System.out.println("Please enter choice between " + 0 + "~" + (numChoices + 1));
            System.out.println("Enter your choice");
            choice = scanner.nextInt();
            scanner.nextLine();
        }

        return choice;
    }

    /**
     * Reads from console the mark of a course component.
     */
    public static double readCourseComponentMark() {
        double assessmentMark;
        System.out.println("Enter the mark for this assessment:");
        assessmentMark = scanner.nextDouble();
        scanner.nextLine();
        while (assessmentMark > 100 || assessmentMark < 0) {
            System.out.println("Please enter mark in range 0 ~ 100.");
            assessmentMark = scanner.nextDouble();
            scanner.nextLine();
        }

        return assessmentMark;
    }

    /**
     * Reads from console the mark of an exam.
     */
    public static double readExamMark() {
        double examMark;
        System.out.println("Enter exam mark:");
        examMark = scanner.nextDouble();
        scanner.nextLine();
        while (examMark > 100 || examMark < 0) {
            System.out.println("Please enter mark in range 0 ~ 100.");
            examMark = scanner.nextDouble();
            scanner.nextLine();
        }

        return examMark;
    }

    public void initiateEnteringCourseworkMark(boolean isExam) {
        printFunctionCall("enterCourseWorkMark");

        String studentID = StudentMgr.getInstance().readStudentFromUser().getStudentID();
        String courseID = CourseMgr.getInstance().readCourseFromUser().getCourseID();
        MarkMgr.getInstance().setCourseworkMark(isExam, studentID, courseID);
    }

}
