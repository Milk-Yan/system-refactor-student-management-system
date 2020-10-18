package com.softeng306.managers;


import com.softeng306.domain.student.Student;
import com.softeng306.io.StudentMgrIO;
import com.softeng306.main.Main;
import com.softeng306.io.FILEMgr;

/**
 * Manages the student related operations.
 * Contains addStudent.

 */

public class StudentMgr {

    /**
     * Adds a student and put the student into file
     */
    public static void addStudent() {
        StudentMgrIO io = new StudentMgrIO();
        String studentID = null;
        io.printMenu();
        boolean systemGeneratedID = io.systemGenerateID();

        if (!systemGeneratedID) {
            System.out.println("Bitch");
            studentID = io.getStudentID();
        }

        String studentName = io.getStudentName();

        Student currentStudent = new Student(studentName);

        if (!systemGeneratedID) {
            currentStudent.setStudentID(studentID);
        }

        //Set school
        currentStudent.setStudentSchool(io.getSchoolName());


        //gender
        currentStudent.setGender(io.getStudentGender());


        //student year
        currentStudent.setStudentYear(io.getStudentYear());


        FILEMgr.writeStudentsIntoFile(currentStudent);
        Main.students.add(currentStudent);

        io.printStudentID(currentStudent.getStudentName(), currentStudent.getStudentID());
    }
}
