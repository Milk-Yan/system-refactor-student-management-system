package com.softeng306.validation;

import com.softeng306.enums.CourseType;
import com.softeng306.domain.course.Course;
import com.softeng306.managers.CourseMgr;

import java.util.List;
import java.util.stream.Collectors;

public class CourseValidator {

    private static final String COURSE_ID_REGEX = "^[A-Z]{2}[0-9]{3,4}$";

    /**
     * Checks whether the inputted course ID is in the correct format.
     *
     * @param courseID The inputted course ID.
     * @return boolean indicates whether the inputted course ID is valid.
     */
    public static boolean checkValidCourseIDInput(String courseID) {
        boolean valid = RegexValidator.checkStringRegexFormat(courseID, COURSE_ID_REGEX);
        if (!valid) {
            System.out.println("Wrong format of course ID.");
        }
        return valid;
    }

    /**
     * Checks whether the inputted course type is valid.
     *
     * @param courseType The inputted course type.
     * @return boolean indicates whether the inputted course type is valid.
     */
    public static boolean checkCourseTypeValidation(String courseType) {
        if (CourseType.contains(courseType)) {
            return true;
        }
        System.out.println("The course type is invalid. Please re-enter.");
        return false;
    }

    public static boolean checkCourseIDExists(String courseID) {
        List<Course> anyCourse = CourseMgr.getInstance().getCourses().stream().filter(c -> courseID.equals(c.getCourseID())).collect(Collectors.toList());
        if (anyCourse.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * Checks whether this course ID is used by other courses.
     *
     * @param courseID The inputted course ID.
     * @return the existing course or else null.
     */
    public static Course getCourseFromId(String courseID) {
        List<Course> anyCourse = CourseMgr.getInstance().getCourses().stream().filter(c -> courseID.equals(c.getCourseID())).collect(Collectors.toList());
        if (anyCourse.isEmpty()) {
            return null;
        }
        return anyCourse.get(0);
    }

}
