package com.softeng306.managers;

import com.softeng306.domain.exceptions.StudentNotFoundException;
import com.softeng306.domain.student.IStudent;

import java.util.List;

/**
 * Interface for student manager operations.
 * Defines the responsibilities of what functions must be performed on enrolled students.
 */
public interface IStudentMgr {
    /**
     * Creates a new student.
     *
     * @param id     The new student's ID.
     * @param name   The new student's name.
     * @param school The school (department) that the new student is in.
     * @param gender The gender of the new student.
     * @param year   The year number of the new student (1-4).
     */
    void createNewStudent(String id, String name, String school, String gender, int year);

    /**
     * Displays the IDs of all the students.
     */
    void printAllStudentIds();

    /**
     * Generates an ID for a new student.
     *
     * @return the generated student ID.
     */
    String generateStudentID();

    /**
     * Checks whether a student is enrolled in any courses.
     *
     * @param studentId The student to check registrations for.
     * @return Whether or not the student has registrations.
     */
    boolean studentHasCourses(String studentId);

    /**
     * Get a student with a given ID.
     *
     * @param studentId The student ID to search for.
     * @return The student with the given ID.
     * @throws StudentNotFoundException If there is no student with the given ID.
     */
    IStudent getStudentFromId(String studentId) throws StudentNotFoundException;

    /**
     * Get the name of the student with a given ID.
     *
     * @param studentId The ID to get the student name of.
     * @return The name of the student with the given ID.
     * @throws StudentNotFoundException If there is no student with the given ID.
     */
    String getStudentName(String studentId) throws StudentNotFoundException;

    /**
     * Generate a list of strings representing each student.
     *
     * @return The list of student strings.
     */
    List<String> generateStudentInformationStrings();

    /**
     * Checks whether this student ID is used by any students.
     *
     * @param studentID The student ID to check for.
     * @return Whether or not the given ID has been used.
     */
    boolean studentExists(String studentID);
    
}
