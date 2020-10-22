package com.softeng306.managers;


import com.softeng306.domain.student.Student;
import com.softeng306.io.FILEMgr;
import com.softeng306.io.HelpInfoMgr;
import com.softeng306.validation.DepartmentValidator;
import com.softeng306.validation.GenderValidator;
import com.softeng306.validation.StudentValidator;
import com.softeng306.io.StudentMgrIO;

import java.util.ArrayList;

import com.softeng306.io.FILEMgr;

/**
 * Manages the student related operations.
 * Contains addStudent.
 */

public class StudentMgr {
    /**
     * An array list of all the students in this school.
     */
    public static ArrayList<Student> students = new ArrayList<Student>(0);

    private static StudentMgr singleInstance = null;

    /**
     * Override default constructor to implement singleton pattern
     */
    private StudentMgr() {
    }

    /**
     * Return the StudentMgr singleton, if not initialised already, create an instance.
     *
     * @return StudentMgr the singleton instance
     */
    public static StudentMgr getInstance() {
        if (singleInstance == null) {
            singleInstance = new StudentMgr();
        }

        return singleInstance;
    }


    /**
     * Adds a student and put the student into file
     */
    public void addStudent() {
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
}
