package com.softeng306.managers;

import com.softeng306.domain.exceptions.StudentNotFoundException;
import com.softeng306.domain.student.IStudent;
import com.softeng306.domain.student.Student;

import java.util.List;

public interface IStudentMgr {
    void createNewStudent(String id, String name, String school, String gender, int year);

    /**
     * Displays a list of IDs of all the students.
     */
    void printAllStudentIds();

    /**
     * Generates the ID of a new student.
     *
     * @return the generated student ID.
     */
    String generateStudentID();

    boolean studentHasCourses(String studentId);

    IStudent getStudentFromId(String studentId) throws StudentNotFoundException;

    String getStudentName(String studentId) throws StudentNotFoundException;

    List<String> generateStudentInformationStrings();

    /**
     * Checks whether this student ID is used by other students.
     *
     * @param studentID This student's ID.
     * @return the existing student or else null.
     */
    boolean studentExists(String studentID);
}
