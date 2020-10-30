package com.softeng306.io;

import com.softeng306.domain.exceptions.CourseNotFoundException;
import com.softeng306.managers.CourseMgr;
import com.softeng306.managers.MarkMgr;

import java.util.List;
import java.util.Scanner;

public class MarkMgrIO implements IMarkMgrIO {
    private Scanner scanner = new Scanner(System.in);
    private MarkMgr markMgr = MarkMgr.getInstance();
    
    @Override
    public void printStudentNotRegisteredToCourse(String courseID) {
        System.out.println("This student haven't registered " + courseID);
    }

    @Override
    public void printCourseComponentChoices(List<String> availableChoices, List<Integer> weights) {
        System.out.println("Here are the choices you can have: ");

        for (int i = 0; i < availableChoices.size(); i++) {
            System.out.println((i + 1) + ". " + availableChoices.get(i) + " Weight in Total: " + (double) weights.get(i) + "%");
        }
        System.out.println((availableChoices.size() + 1) + ". Quit");
    }

    @Override
    public int readCourseComponentChoice(int numChoices) {
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

    @Override
    public double readCourseComponentMark() {
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

    @Override
    public double readExamMark() {
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

    @Override
    public void initiateEnteringCourseworkMark(boolean isExam) {
        printFunctionCall("enterCourseWorkMark");

        try {
            String studentID = new StudentMgrIO().readExistingStudentIDFromUser();
            String courseID = CourseMgr.getInstance().readCourseFromUser().getCourseID();
            markMgr.setCourseworkMark(isExam, studentID, courseID);
        } catch (CourseNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prints to console that a function has been called.
     */
    private void printFunctionCall(String functionName) {
        System.out.println(functionName + " is called");
    }
}
