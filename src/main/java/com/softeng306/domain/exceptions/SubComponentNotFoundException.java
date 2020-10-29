package com.softeng306.domain.exceptions;

public class SubComponentNotFoundException extends Exception {

    public SubComponentNotFoundException(String subComponentName) {
        super("SubComponent " + subComponentName + " was not found.");
    }
}
