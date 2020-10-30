package com.softeng306.io;

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
    String readExistingStudentIDFromUser();
}
