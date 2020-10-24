package com.softeng306.Enum;

public enum GroupType {
    LabGroup, LectureGroup, TutorialGroup;

    private String typeString;

    static {
        LabGroup.typeString = "lab";
        LectureGroup.typeString = "lecture";
        TutorialGroup.typeString = "tutorial";
    }

    public String toTypeString() {
        return typeString;
    }
}
