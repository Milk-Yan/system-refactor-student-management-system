package com.softeng306.io;

import com.softeng306.Enum.GroupType;
import com.softeng306.domain.course.Course;
import com.softeng306.domain.course.courseregistration.CourseRegistration;
import com.softeng306.domain.student.Student;

import java.util.List;

public class CourseRegistrationManagerIO {

    public void printOptions(){
        System.out.println("Print student by: ");
        System.out.println("(1) Lecture group");
        System.out.println("(2) Tutorial group");
        System.out.println("(3) Lab group");
    }

    public void printByGroup(List<CourseRegistration> courseRegistrations, GroupType groupType){
        if(courseRegistrations.size() == 0){
            return;
        }

        String groupName = "";
        for (int i = 0; i < courseRegistrations.size(); i++) {
            if(groupType.equals(GroupType.TutorialGroup)){
                if (!groupName.equals(courseRegistrations.get(i).getTutorialGroup())) {
                    groupName = courseRegistrations.get(i).getTutorialGroup();
                    System.out.println("Tutorial group : " + groupName);
                }
            } else if(groupType.equals(GroupType.LabGroup)){
                if (!groupName.equals(courseRegistrations.get(i).getLabGroup())) {
                    groupName = courseRegistrations.get(i).getLabGroup();
                    System.out.println("Lab group : " + groupName);
                }
            } else if(groupType.equals(GroupType.LectureGroup)){
                if (!groupName.equals(courseRegistrations.get(i).getLectureGroup())) {  // if new lecture group print out group name
                    groupName = courseRegistrations.get(i).getLectureGroup();
                    System.out.println("Lecture group : " + groupName);
                }
            }
            System.out.print("Student Name: " + courseRegistrations.get(i).getStudent().getStudentName());
            System.out.println(" Student ID: " + courseRegistrations.get(i).getStudent().getStudentID());
        }
        System.out.println();
    }

    public void printNoGroup(GroupType type){
        System.out.format("This course does not contain any %s group.%n", type.toTypeString());
    }

    public void printNoEnrolmentsError(){
        System.out.println("No one has registered this course yet.");
    }

    public void printInvalidInputError(){
        System.out.println("Invalid input. Please re-enter.");
    }

    public void printEndOfSection(){
        System.out.println("------------------------------------------------------");
    }

    public void printSuccessfulRegistration(Course c, Student s, String lectureGroup, String tutorialGroup, String labGroup){
        System.out.println("Course registration successful!");
        System.out.print("Student: " + s.getStudentName());
        System.out.print("\tLecture Group: " + lectureGroup);
        if (c.getTutorialGroups().size() != 0) {
            System.out.print("\tTutorial Group: " + tutorialGroup);
        }
        if (c.getLabGroups().size() != 0) {
            System.out.print("\tLab Group: " + labGroup);
        }
        System.out.println();
    }

    public void noVacancies(){
        System.out.println("Sorry, the course has no vacancies any more.");
    }

    public void printNoAssessmentMessage(Course c){
        System.out.println("Professor " + c.getProfInCharge().getProfName() + " is preparing the assessment. Please try to register other courses.");
    }

    public void printPendingRegistrationMethod(Course c, Student s){
        System.out.println("Student " + s.getStudentName() + " with ID: " + s.getStudentID() +
                " wants to register " + c.getCourseID() + " " + c.getCourseName());
    }


}
