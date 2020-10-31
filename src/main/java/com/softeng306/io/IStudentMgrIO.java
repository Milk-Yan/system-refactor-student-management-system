package com.softeng306.io;

/**
 * Interface for StudentMgrIO.
 * StudentMgrIO acts as a central module to manipulate students.
 * Provides methods to manipulate students.
 */
public interface IStudentMgrIO {
    /**
     * Adds a student and put the student into file
     */
    void addStudent();

    /**
     * Prints transcript (Results of course taken) for a particular student
     */
    void printStudentTranscript();

    /**
     * Prompts the user to input an ID for an existing student
     */
    String readExistingStudentID();
}
