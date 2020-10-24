package com.softeng306.managers;


import com.softeng306.domain.student.Student;
import com.softeng306.io.FILEMgr;
import com.softeng306.io.StudentMgrIO;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the student related operations.
 * Contains addStudent.
 */
public class StudentMgr {
    /**
     * A list of all the students in this school.
     */
    private List<Student> students;

    private static StudentMgr singleInstance = null;

    /**
     * Override default constructor to implement singleton pattern
     */
    private StudentMgr(List<Student> students) {

        this.students = students;
        students = new ArrayList<>(0);
    }

    /**
     * Return the StudentMgr singleton, if not initialised already, create an instance.
     *
     * @return StudentMgr the singleton instance
     */
    public static StudentMgr getInstance() {
        if (singleInstance == null) {
            singleInstance = new StudentMgr(FILEMgr.loadStudents());
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
        students.add(currentStudent);

        StudentMgrIO.printStudentID(currentStudent.getStudentName(), currentStudent.getStudentID());
    }

    /**
     * Return the list of all students in the system.
     * @return An list of all students.
     */
    public List<Student> getStudents() {
        return students;
    }

    /**
     * Displays a list of IDs of all the students.
     */
    public void printAllStudentIds() {
        for (Student s : students) {
            System.out.println(s.getStudentID());
        }
    }

}
