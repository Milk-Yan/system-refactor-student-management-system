package com.softeng306.domain.exceptions;

public class ProfessorNotFoundException extends Exception {

    public ProfessorNotFoundException(String professorID) {
        super("Professor with id: " + professorID+ " was not found.");
    }
}
