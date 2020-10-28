package com.softeng306.io;

import com.softeng306.enums.GroupType;
import com.softeng306.domain.course.Course;
import com.softeng306.domain.course.courseregistration.CourseRegistration;
import com.softeng306.domain.course.group.Group;
import com.softeng306.domain.student.Student;
import com.softeng306.managers.CourseMgr;
import com.softeng306.managers.CourseRegistrationMgr;

import java.util.ArrayList;
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
     * This method prints the students of a given group
     *
     * @param courseRegistrations the list registrations for a course
     * @param groupType           the group of registration that we want to print
     */
    public void printByGroup(List<CourseRegistration> courseRegistrations, GroupType groupType) {
        if (courseRegistrations.isEmpty()) {
            return;
        }

        String groupName = "";
        for (int i = 0; i < courseRegistrations.size(); i++) {
            if (groupType.equals(GroupType.TUTORIAL_GROUP)) {
                if (!groupName.equals(courseRegistrations.get(i).getTutorialGroup().getGroupName())) {
                    groupName = courseRegistrations.get(i).getTutorialGroup().getGroupName();
                    System.out.println("Tutorial group : " + groupName);
                }
            } else if (groupType.equals(GroupType.LAB_GROUP)) {
                if (!groupName.equals(courseRegistrations.get(i).getLabGroup().getGroupName())) {
                    groupName = courseRegistrations.get(i).getLabGroup().getGroupName();
                    System.out.println("Lab group : " + groupName);
                }
            } else if (groupType.equals(GroupType.LECTURE_GROUP)) {
                if (!groupName.equals(courseRegistrations.get(i).getLectureGroup().getGroupName())) {  // if new lecture group print out group name
                    groupName = courseRegistrations.get(i).getLectureGroup().getGroupName();
                    System.out.println("Lecture group : " + groupName);
                }
            }
            System.out.print("Student Name: " + courseRegistrations.get(i).getStudent().getStudentName());
            System.out.println(" Student ID: " + courseRegistrations.get(i).getStudent().getStudentID());
        }
        System.out.println();
    }


    /**
     * When there is no group of the given type, this method will be called
     */
    public void printNoGroup(GroupType type) {
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

    public void printNoAssessmentMessage(Course c) {
        System.out.println("Professor " + c.getProfInCharge().getProfName() + " is preparing the assessment. Please try to register other courses.");
    }

    /**
     * Before registration details are known, this will print an pending message.
     *
     * @param course  is a Course that we are registering a student for.
     * @param student is the student that is being registered.
     */
    public void printPendingRegistrationMethod(Course course, Student student) {
        System.out.println("Student " + student.getStudentName() + " with ID: " + student.getStudentID() +
                " wants to register " + course.getCourseID() + " " + course.getCourseName());
    }

    /**
     * Gets a student and course from the user to create a new registration for
     */
    public void registerCourse() {
        MainMenuIO.printMethodCall("registerCourse");

        CourseMgrIO courseIO = new CourseMgrIO();

        String studentID = StudentMgrIO.readExistingStudentIDFromUser();
        CourseMgr.getInstance().readDepartmentFromUser();
        String courseID = courseIO.readExistingCourseIDFromUser();

        List<String> newRegistrationInfo = courseRegistrationMgr.registerCourse(this, studentID, courseID);

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

        String courseID = courseIO.readExistingCourseIDFromUser();
        printOptions();

        int opt;
        do {
            opt = scanner.nextInt();
            scanner.nextLine();

            System.out.println("------------------------------------------------------");

            courseRegistrationMgr.printStudents(this, courseID, opt);
        } while (opt < 1 || opt > 3);
    }
}
