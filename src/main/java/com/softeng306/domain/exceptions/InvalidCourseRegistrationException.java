package com.softeng306.domain.exceptions;

/**
 * This exception is thrown when the user tries to register a student for a particular course,
 * but the student cannot be registered as the details provided for the student, course, or both are invalid.
 */
public class InvalidCourseRegistrationException extends Exception {

    public InvalidCourseRegistrationException() {
        super("Invalid Course Registration.");
    }
}
