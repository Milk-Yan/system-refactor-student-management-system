package com.softeng306.io;

import com.softeng306.managers.CourseMgr;
import com.softeng306.managers.CourseRegistrationMgr;

import java.util.List;
import java.util.Scanner;

public class CourseRegistrationMgrIO {
    private Scanner scanner = new Scanner(System.in);
    private CourseRegistrationMgr courseRegistrationMgr = CourseRegistrationMgr.getInstance();

    /**
     * This method prints the menu options when printing student lists
     */
    public void printOptions() {
        System.out.println("Print student by: ");
        System.out.println("(1) Lecture group");
        System.out.println("(2) Tutorial group");
        System.out.println("(3) Lab group");
    }


    /**
     * When there is no group of the given type, this method will be called
     */
    public void printNoGroup(String type) {
        System.out.format("This course does not contain any %s group.%n", type);
    }

    /**
     * When there is no enrolments for a course, this method will print an error
     * to the user
     */
    public void printNoEnrolmentsError() {
        System.out.println("No one has registered this course yet.");
    }

    /**
     * If the input is invalid, this method will let the user know
     */
    public void printInvalidInputError() {
        System.out.println("Invalid input. Please re-enter.");
    }


    public void printEndOfSection() {
        System.out.println("------------------------------------------------------");
    }

    /**
     * Upon successful registration, this method will print a success message and
     * the groups that the student has been added to
     *
     * @param newRegistrationInfo The new registration information to display
     */
    public void printSuccessfulRegistration(List<String> newRegistrationInfo) {
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

    public void printNoVacancies() {
        System.out.println("Sorry, the course has no vacancies any more.");
    }

    public void printNoAssessmentMessage(String profName) {
        System.out.println("Professor " + profName + " is preparing the assessment. Please try to register other courses.");
    }

    /**
     * Before registration details are known, this will print an pending message.
     *
     * @param studentName is the name of the student
     * @param studentId   is the id of the student
     * @param courseId    is a course id that we are registering a student for.
     * @param courseName  is a course name that we are registering a student for.
     */
    public void printPendingRegistrationMethod(String studentName, String studentId, String courseId, String courseName) {
        System.out.println("Student " + studentName + " with ID: " + studentId +
                " wants to register " + courseId + " " + courseName);
    }

    /**
     * Gets a student and course from the user to create a new registration for
     */
    public void registerCourse() {
        MainMenuIO.printMethodCall("registerCourse");

        CourseMgrIO courseIO = new CourseMgrIO();

        String studentID = new StudentMgrIO().readExistingStudentIDFromUser();
        CourseMgr.getInstance().readDepartmentFromUser();
        String courseID = courseIO.readValidCourseIdFromUser();

        List<String> newRegistrationInfo = courseRegistrationMgr.registerCourse(studentID, courseID);

        if (newRegistrationInfo != null) {
            printSuccessfulRegistration(newRegistrationInfo);
        }
    }

    /**
     * Gets a course id from the user to print student registrations for
     */
    public void printStudents() {
        MainMenuIO.printMethodCall("printStudent");
        CourseMgrIO courseIO = new CourseMgrIO();

        String courseID = courseIO.readValidCourseIdFromUser();
        printOptions();

        int opt;
        do {
            opt = scanner.nextInt();
            scanner.nextLine();

            System.out.println("------------------------------------------------------");

            courseRegistrationMgr.printStudents(courseID, opt);
        } while (opt < 1 || opt > 3);
    }

    public void printGroupString(List<String> groupString) {
        groupString.forEach(System.out::println);
    }
}
