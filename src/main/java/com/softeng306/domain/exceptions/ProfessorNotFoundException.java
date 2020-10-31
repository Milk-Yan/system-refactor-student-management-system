package com.softeng306.domain.exceptions;

/**
 * This exception is thrown when the system is asked to manipulate a specific professor, identified by it's professorId,
 * and no professor with the given professorId is found in the system.
 */
public class ProfessorNotFoundException extends Exception {

    public ProfessorNotFoundException(String professorID) {
        super("Professor with id: " + professorID + " was not found.");
    }
}
