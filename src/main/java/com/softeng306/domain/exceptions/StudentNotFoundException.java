package com.softeng306.domain.exceptions;

/**
 * This exception is thrown when the system is asked to manipulate a specific student, identified by it's studentId,
 * and no student with the given studentId is found in the system.
 */
public class StudentNotFoundException extends Exception {

    public StudentNotFoundException(String studentID) {
        super("Student with id: " + studentID + " not found.");
    }
}
