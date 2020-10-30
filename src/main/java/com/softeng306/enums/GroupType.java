package com.softeng306.enums;

/**
 * Enumerated type to represent the types of groups a student enrols in for a course registration.
 */
public enum GroupType {
    LAB_GROUP("lab"), LECTURE_GROUP("lecture"), TUTORIAL_GROUP("tutorial");

    /**
     * String representation of the group type.
     */
    private String nameLowerCase;

    /**
     * Creates a group type with a given name.
     *
     * @param nameLowerCase The name for the group.
     */
    GroupType(String nameLowerCase) {
        this.nameLowerCase = nameLowerCase;
    }

    /**
     * Returns the string value of the enum type with all lower cases.
     */
    @Override
    public String toString() {
        return nameLowerCase;
    }

    /**
     * Returns the name of the enum with a capital letter at the start.
     */
    public String getNameWithCapital() {
        return nameLowerCase.substring(0, 1).toUpperCase() + nameLowerCase.substring(1);
    }

}
