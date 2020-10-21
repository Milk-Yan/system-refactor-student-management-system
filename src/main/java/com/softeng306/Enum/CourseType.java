package com.softeng306.Enum;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Set;

public enum CourseType {
    CORE,MPE,GREPEBM,GREPELA,GREPESTS,UE
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

}
