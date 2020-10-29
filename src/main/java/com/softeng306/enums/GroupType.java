package com.softeng306.enums;

public enum GroupType {
    LAB_GROUP("lab"), LECTURE_GROUP("lecture"), TUTORIAL_GROUP("tutorial");

    private String nameLowerCase;

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
