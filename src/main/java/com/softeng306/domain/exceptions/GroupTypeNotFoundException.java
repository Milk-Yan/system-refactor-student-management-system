package com.softeng306.domain.exceptions;

/**
 * This exception is thrown when a string given for a CourseType's name does not match any of the CourseTypes defined in the
 * CourseType enum.
 */
public class GroupTypeNotFoundException extends Exception {

    public GroupTypeNotFoundException(String groupTypeName) {
        super("Group type " + groupTypeName + " was not found.");
    }
}
