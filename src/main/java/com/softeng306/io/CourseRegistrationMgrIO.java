package com.softeng306.io;

import com.softeng306.domain.exceptions.CourseNotFoundException;
import com.softeng306.domain.exceptions.GroupTypeNotFoundException;
import com.softeng306.domain.exceptions.InvalidCourseRegistrationException;
import com.softeng306.domain.exceptions.StudentNotFoundException;
import com.softeng306.managers.CourseMgr;
import com.softeng306.managers.CourseRegistrationMgr;

import java.util.List;
import java.util.Scanner;

public class CourseRegistrationMgrIO implements ICourseRegistrationMgrIO {
    private Scanner scanner = new Scanner(System.in);
    private CourseRegistrationMgr courseRegistrationMgr = CourseRegistrationMgr.getInstance();

    @Override
    public void printContainsNoGroupMessage(String type) {
        System.out.format("This course does not contain any %s group.%n", type);
    }

    @Override
    public void printNoRegistrationsForCourseMessage() {
        System.out.println("No one has registered this course yet.");
    }

    @Override
    public void printAlreadyRegisteredError() {
        System.out.println("Sorry. This student already registers this course.");
    }

    @Override
    public void printInvalidUserInputMessage() {
        System.out.println("Invalid input. Please re-enter.");
    }


    @Override
    public void printEndOfSection() {
        System.out.println("------------------------------------------------------");
    }

    @Override
    public void printNoVacancies() {
        System.out.println("Sorry, the course has no vacancies any more.");
    }

    @Override
    public void printNoAssessmentMessage(String profName) {
        System.out.println("Professor " + profName + " is preparing the assessment. Please try to register other courses.");
    }

    @Override
    public void printRegistrationRequestDetails(String studentName, String studentId, String courseId, String courseName) {
        System.out.println("Student " + studentName + " with ID: " + studentId +
                " wants to register " + courseId + " " + courseName);
    }

    @Override
    public void registerStudentForCourse() {
        MainMenuIO.printMethodCall("registerCourse");

        ICourseMgrIO courseIO = new CourseMgrIO();

        String studentID = new StudentMgrIO().readExistingStudentIDFromUser();
        CourseMgr.getInstance().readDepartmentFromUser();
        String courseID = courseIO.readValidCourseIdFromUser();

        try {
            List<String> newRegistrationInfo = courseRegistrationMgr.registerCourse(studentID, courseID);
            printSuccessfulRegistration(newRegistrationInfo);
        } catch (CourseNotFoundException | StudentNotFoundException | InvalidCourseRegistrationException ignored) {

        }
    }

    @Override
    public void printStudents() {
        MainMenuIO.printMethodCall("printStudent");
        ICourseMgrIO courseIO = new CourseMgrIO();

        String courseID = courseIO.readValidCourseIdFromUser();
        printOptions();

        int opt;
        do {
            opt = scanner.nextInt();
            scanner.nextLine();

            System.out.println("------------------------------------------------------");

            try {
                courseRegistrationMgr.printStudents(courseID, opt);
            } catch (CourseNotFoundException | GroupTypeNotFoundException e) {
                e.printStackTrace();
            }
        } while (opt < 1 || opt > 3);
    }

    @Override
    public void printGroupString(List<String> groupString) {
        groupString.forEach(System.out::println);
    }

    /**
     * This method prints the menu options when printing student lists
     */
    private void printOptions() {
        System.out.println("Print student by: ");
        System.out.println("(1) Lecture group");
        System.out.println("(2) Tutorial group");
        System.out.println("(3) Lab group");
    }

    /**
     * Upon successful registration, this method will print a success message and
     * the groups that the student has been added to
     *
     * @param newRegistrationInfo The new registration information to display
     */
    private void printSuccessfulRegistration(List<String> newRegistrationInfo) {
        String studentName = newRegistrationInfo.get(0);
        String lectureGroup = newRegistrationInfo.get(1);
        String tutorialGroup = newRegistrationInfo.get(2);
        String labGroup = newRegistrationInfo.get(3);

        System.out.println("Course registration successful!");
        System.out.print("Student: " + studentName);
        System.out.print("\tLecture Group: " + lectureGroup);
        if (!(tutorialGroup.isEmpty())) {
            System.out.print("\tTutorial Group: " + tutorialGroup);
        }
        if (!(labGroup.isEmpty())) {
            System.out.print("\tLab Group: " + labGroup);
        }
        System.out.println();
    }

}
