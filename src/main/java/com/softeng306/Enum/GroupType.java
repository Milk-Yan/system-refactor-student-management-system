package com.softeng306.Enum;

public enum GroupType {
    LAB_GROUP("LabGroup"), LECTURE_GROUP("LectureGroup"), TUTORIAL_GROUP("TutorialGroup");

    private String stringValue;

    GroupType(String stringValue) { this.stringValue = stringValue; }

    /**
     * Returns the string value of the enum type.
     */
    @Override
    public String toString() {
        return stringValue;
    }
}
