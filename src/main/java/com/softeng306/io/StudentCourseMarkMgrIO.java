package com.softeng306.io;

import com.softeng306.domain.exceptions.CourseNotFoundException;
import com.softeng306.managers.CourseMgr;
import com.softeng306.managers.IStudentCourseMarkMgr;
import com.softeng306.managers.StudentCourseMarkMgr;

import java.util.List;
import java.util.Scanner;

public class StudentCourseMarkMgrIO implements IStudentCourseMarkMgrIO {
    private Scanner reader = new Scanner(System.in);
    private IStudentCourseMarkMgr studentCourseMarkMgr = StudentCourseMarkMgr.getInstance();

    @Override
    public void printStudentNotRegisteredToCourse(String courseID) {
        System.out.println("This student haven't registered " + courseID);
    }

    @Override
    public void printCourseComponentChoices(List<String> availableChoices, List<Integer> weights) {
        System.out.println("Here are the choices you can have: ");

        //Iterate through the available course component choices and print each out
        for (int i = 0; i < availableChoices.size(); i++) {
            System.out.println((i + 1) + ". " + availableChoices.get(i) + " Weight in Total: " + (double) weights.get(i) + "%");
        }
        System.out.println((availableChoices.size() + 1) + ". Quit");
    }

    @Override
    public int readCourseComponentChoice(int numChoices) {
        int choice;
        System.out.println("Enter your choice");
        choice = reader.nextInt();
        reader.nextLine();

        //While the choice is invalid, prompt the user to re-enter another choice
        while (choice > (numChoices + 1) || choice < 0) {
            System.out.println("Please enter choice between " + 0 + "~" + (numChoices + 1));
            System.out.println("Enter your choice");
            choice = reader.nextInt();
            reader.nextLine();
        }

        return choice;
    }

    @Override
    public double readCourseComponentMark() {
        double assessmentMark;
        System.out.println("Enter the mark for this assessment:");
        assessmentMark = reader.nextDouble();
        reader.nextLine();

        //While the course component mark is invalid, prompt the user to re-enter the mark
        while (assessmentMark > 100 || assessmentMark < 0) {
            System.out.println("Please enter mark in range 0 ~ 100.");
            assessmentMark = reader.nextDouble();
            reader.nextLine();
        }

        return assessmentMark;
    }

    @Override
    public double readExamMark() {
        double examMark;
        System.out.println("Enter exam mark:");
        examMark = reader.nextDouble();
        reader.nextLine();

        //While the exam mark is invalid, prompt the user to re-enter the mark
        while (examMark > 100 || examMark < 0) {
            System.out.println("Please enter mark in range 0 ~ 100.");
            examMark = reader.nextDouble();
            reader.nextLine();
        }

        return examMark;
    }

    @Override
    public void initiateEnteringCourseworkMark(boolean isExam) {
        printFunctionCall("enterCourseWorkMark");

        try {
            //Try set the courseWorkMark for the given studentId and courseId
            String studentID = new StudentMgrIO().readExistingStudentID();
            String courseID = CourseMgr.getInstance().readExistingCourse().getCourseId();
            studentCourseMarkMgr.setCourseworkMark(isExam, studentID, courseID);

        } catch (CourseNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void printSubComponentMarkSetMessage(List<Double> resultList) {
        System.out.println("The sub course work component is successfully set to: " + resultList.get(0));
        System.out.println("The main course work component increase by: " + resultList.get(1));
        System.out.println("The course total mark is updated to: " + resultList.get(2));
    }

    @Override
    public void printMainComponentMarkSetMessage(List<Double> resultList) {
        if (resultList.isEmpty()) {
            System.out.println("This main assessment is not stand alone");
        } else {
            System.out.println("The course work component is successfully set to: " + resultList.get(0));
            System.out.println("The course total mark is updated to: " + resultList.get(1));
        }
    }

    @Override
    public void printMainComponentDoesNotExistMessage(String message) {
        System.out.println(message);
    }

    /**
     * Prints to console that a function has been called.
     */
    private void printFunctionCall(String functionName) {
        System.out.println(functionName + " is called");
    }

}
