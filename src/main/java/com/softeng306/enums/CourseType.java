package com.softeng306.enums;

import java.util.ArrayList;
import java.util.List;

public enum CourseType {
    CORE, MPE, GREPEBM, GREPELA, GREPESTS, UE;


    public static List<String> getAllCourseTypes(){
        List<String> courseTypes = new ArrayList<>();
        for (CourseType courseType : CourseType.values()) {
            courseTypes.add(courseType.toString());
        }
        return courseTypes;
    }


    /**
     * Finds whether the string exists as a course type.
     */
    public static boolean contains(String possibleCourse) {
        for (CourseType courseType : CourseType.values()) {
            if (courseType.toString().equals(possibleCourse)) {
                return true;
            }
        }

        return false;
    }
}
