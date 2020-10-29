package com.softeng306.domain.exceptions;

public class StudentNotFoundException extends Exception {

    public StudentNotFoundException(String studentID) {
        super("Student with id: " + studentID + " not found.");
    }
}
