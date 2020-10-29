package com.softeng306.domain.exceptions;

public class GroupTypeNotFoundException extends Exception {

    public GroupTypeNotFoundException(String groupTypeName) {
        super("Group type " + groupTypeName + " was not found.");
    }
}
