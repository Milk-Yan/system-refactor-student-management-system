package com.softeng306.io;

import com.softeng306.enums.GroupType;
import com.softeng306.domain.course.Course;
import com.softeng306.domain.course.courseregistration.CourseRegistration;
import com.softeng306.domain.course.group.Group;
import com.softeng306.domain.student.Student;

import java.util.List;

public class CourseRegistrationManagerIO {


    /**
     * This method prints the menu options when printing student lists
     */
    public static void printOptions(){
        System.out.println("Print student by: ");
        System.out.println("(1) Lecture group");
        System.out.println("(2) Tutorial group");
        System.out.println("(3) Lab group");
    }


    /**
     * This method prints the students of a given group
     * @param courseRegistrations the list registrations for a course
     * @param groupType the group of registration that we want to print
     */
    public static void printByGroup(List<CourseRegistration> courseRegistrations, GroupType groupType){
        if(courseRegistrations.size() == 0){
            return;
        }

        String groupName = "";
        for (int i = 0; i < courseRegistrations.size(); i++) {
            if(groupType.equals(GroupType.TUTORIAL_GROUP)){
                if (!groupName.equals(courseRegistrations.get(i).getTutorialGroup().getGroupName())) {
                    groupName = courseRegistrations.get(i).getTutorialGroup().getGroupName();
                    System.out.println("Tutorial group : " + groupName);
                }
            } else if(groupType.equals(GroupType.LAB_GROUP)){
                if (!groupName.equals(courseRegistrations.get(i).getLabGroup().getGroupName())) {
                    groupName = courseRegistrations.get(i).getLabGroup().getGroupName();
                    System.out.println("Lab group : " + groupName);
                }
            } else if(groupType.equals(GroupType.LECTURE_GROUP)){
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
    public static void printNoGroup(GroupType type){
        System.out.format("This course does not contain any %s group.%n", type);
    }

    /**
     * When there is no enrolments for a course, this method will print an error
     * to the user
     */
    public static void printNoEnrolmentsError(){
        System.out.println("No one has registered this course yet.");
    }

    /**
     * If the input is invalid, this method will let the user know
     */
    public static void printInvalidInputError(){
        System.out.println("Invalid input. Please re-enter.");
    }


    public static void printEndOfSection(){
        System.out.println("------------------------------------------------------");
    }

    /**
     * Upon successful registration, this method will print a success message and
     * the groups that the student has been added to
     * @param course that the student has registered in
     * @param student that that has registered
     * @param lectureGroup lecture group that the student is apart of
     * @param tutorialGroup tutorial group that the student is apart of
     * @param labGroup lab group that the student is apart of
     */
    public static void printSuccessfulRegistration(Course course, Student student, Group lectureGroup, Group tutorialGroup, Group labGroup){
        System.out.println("Course registration successful!");
        System.out.print("Student: " + student.getStudentName());
        System.out.print("\tLecture Group: " + lectureGroup.getGroupName());
        if (course.getTutorialGroups().size() != 0) {
            System.out.print("\tTutorial Group: " + tutorialGroup.getGroupName());
        }
        if (course.getLabGroups().size() != 0) {
            System.out.print("\tLab Group: " + labGroup.getGroupName());
        }
        System.out.println();
    }

    public static void printNoVacancies(){
        System.out.println("Sorry, the course has no vacancies any more.");
    }

    public static void printNoAssessmentMessage(Course c){
        System.out.println("Professor " + c.getProfInCharge().getProfName() + " is preparing the assessment. Please try to register other courses.");
    }

    /**
     * Before registration details are known, this will print an pending message.
     * @param course is a Course that we are registering a student for.
     * @param student is the student that is being registered.
     */
    public static void printPendingRegistrationMethod(Course course, Student student){
        System.out.println("Student " + student.getStudentName() + " with ID: " + student.getStudentID() +
                " wants to register " + course.getCourseID() + " " + course.getCourseName());
    }


}
