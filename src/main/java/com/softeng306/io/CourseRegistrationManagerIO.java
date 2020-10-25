package com.softeng306.io;

import com.softeng306.Enum.GroupType;
import com.softeng306.domain.course.courseregistration.CourseRegistration;
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
}
