package com.softeng306.domain.exceptions;

public class CourseNotFoundException extends Exception {

    public CourseNotFoundException(String courseID) {
        super("Course with id: " + courseID + " was not found.")
    }
}
