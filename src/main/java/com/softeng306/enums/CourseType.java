package com.softeng306.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * Enumerated type to represent different course types.
 */
public enum CourseType {
    CORE, MPE, GREPEBM, GREPELA, GREPESTS, UE;

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

    /**
     * @return A list of the names of all the course types.
     */
    public static List<String> getListOfAllCourseTypeNames() {
        List<String> courseTypes = new ArrayList<>();

        for (CourseType courseType : CourseType.values()) {
            courseTypes.add(courseType.toString());
        }

        return courseTypes;
    }

}
