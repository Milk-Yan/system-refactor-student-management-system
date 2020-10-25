package com.softeng306.enums;

public enum GroupType {
    LabGroup, LectureGroup, TutorialGroup;

    private String typeString;

    static {
        LabGroup.typeString = "Lab";
        LectureGroup.typeString = "Lecture";
        TutorialGroup.typeString = "Tutorial";
    }

    public String toTypeString() {
        return typeString;
    }
}
