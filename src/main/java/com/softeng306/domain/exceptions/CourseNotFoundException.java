package com.softeng306.domain.exceptions;

/**
 * This exception is thrown when the system is asked to manipulate a specific course, identified by it's courseId,
 * and no course with the given courseId is found in the system.
 */
public class CourseNotFoundException extends Exception {

    public CourseNotFoundException(String courseID) {
        super("Course with id: " + courseID + " was not found.");
    }
}
