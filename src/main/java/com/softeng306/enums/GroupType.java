package com.softeng306.enums;

public enum GroupType {
    LAB_GROUP("lab"), LECTURE_GROUP("lecture"), TUTORIAL_GROUP("tutorial");

    private String nameLowerCase;
    private String nameWithCapital;

    static {
        LAB_GROUP.nameWithCapital = "Lab";
        LECTURE_GROUP.nameWithCapital = "Lecture";
        TUTORIAL_GROUP.nameWithCapital = "Tutorial";
    }

    GroupType(String nameLowerCase) { this.nameLowerCase = nameLowerCase; }

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
        return nameWithCapital;
    }
}
