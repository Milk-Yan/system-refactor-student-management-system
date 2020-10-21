package com.softeng306.managers;


import com.softeng306.domain.student.Student;
import com.softeng306.io.FILEMgr;
import com.softeng306.io.StudentMgrIO;

import java.util.ArrayList;

/**
 * Manages the student related operations.
 * Contains addStudent.
 */

public class StudentMgr {
    /**
     * An array list of all the students in this school.
     */
    public static ArrayList<Student> students = new ArrayList<Student>(0);


    /**
     * Adds a student and put the student into file
     */
    public static void addStudent() {
        String studentID = null;
        StudentMgrIO.printMenu();

        boolean systemGeneratedID = StudentMgrIO.systemGenerateID();
        if (!systemGeneratedID) {
            studentID = StudentMgrIO.getStudentID();
        }

        String studentName = StudentMgrIO.getStudentName();
        Student currentStudent = new Student(studentName);

        if (!systemGeneratedID) {
            currentStudent.setStudentID(studentID);
        }

        currentStudent.setStudentSchool(StudentMgrIO.getSchoolName());  //Set school
        currentStudent.setGender(StudentMgrIO.getStudentGender());      //gender
        currentStudent.setStudentYear(StudentMgrIO.getStudentYear());   //student year

        FILEMgr.writeStudentsIntoFile(currentStudent);
        StudentMgr.students.add(currentStudent);

        StudentMgrIO.printStudentID(currentStudent.getStudentName(), currentStudent.getStudentID());
    }


    /**
     * Displays a list of IDs of all the students.
     */
    public static void printAllStudents() {
        StudentMgr.students.stream().map(s -> s.getStudentID()).forEach(System.out::println);
    }

}
