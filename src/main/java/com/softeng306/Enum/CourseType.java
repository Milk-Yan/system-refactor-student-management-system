package com.softeng306.Enum;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public enum CourseType {
    CORE, MPE, GREPEBM, GREPELA, GREPESTS, UE;

    /**
     * Displays a list of all the course types.
     */
    public static void printAllCourseType() {
        int index = 1;
        for (CourseType courseType : CourseType.values()) {
            System.out.println(index + ": " + courseType);
            index++;
        }
    }


    /**
     * Gets all the course types as a list.
     *
     * @return a list of all the course types.
     */
    public static List<String> getAllCourseType() {
        Set<CourseType> courseTypeEnumSet = EnumSet.allOf(CourseType.class);
        List<String> courseTypeStringSet = new ArrayList<>(0);
        for (CourseType courseType : courseTypeEnumSet) {
            courseTypeStringSet.add(courseType.toString());
        }
        return courseTypeStringSet;
    }
}
