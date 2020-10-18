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
        Main.students.add(currentStudent);

        StudentMgrIO.printStudentID(currentStudent.getStudentName(), currentStudent.getStudentID());
    }
}
