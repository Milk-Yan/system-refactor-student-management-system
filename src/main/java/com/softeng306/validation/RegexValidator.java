package com.softeng306.validation;

import java.util.regex.Pattern;

public class RegexValidator {

    private static final String COURSE_ID_REGEX = "^[A-Z]{2}[0-9]{3,4}$";
    private static final String GROUP_NAME_REGEX = "^[a-zA-Z0-9]+$";
    private static final String STUDENT_ID_REGEX = "^U[0-9]{7}[A-Z]$";
    private static final String STUDENT_NAME_REGEX = "^[ a-zA-Z]+$";
    
    /**
     * Checks whether the inputted string is in the correct format.
     *
     * @param stringValue The string to check the format of.
     * @param regexFormat The regex format to use
     * @return boolean indicates whether the inputted stringValue is valid.
     */
    public static boolean checkStringRegexFormat(String stringValue, String regexFormat) {
        return Pattern.compile(regexFormat).matcher(stringValue).matches();
    }

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
     * Checks whether the inputted group name is in the correct format.
     *
     * @param groupName The inputted group name.
     * @return boolean indicates whether the inputted group name is valid.
     */
    public static boolean checkValidGroupNameInput(String groupName) {
        boolean valid = RegexValidator.checkStringRegexFormat(groupName, GROUP_NAME_REGEX);
        if (!valid) {
            System.out.println("Wrong format of group name.");
        }
        return valid;
    }

    /**
     * Checks whether the inputted student ID is in the correct format.
     *
     * @param studentID The inputted student ID.
     * @return boolean indicates whether the inputted student ID is valid.
     */
    public static boolean checkValidStudentIDInput(String studentID) {
        boolean valid = RegexValidator.checkStringRegexFormat(studentID, STUDENT_ID_REGEX);
        if (!valid) {
            System.out.println("Wrong format of student ID.");
        }
        return valid;
    }

    /**
     * Checks whether the inputted student name is in the correct format.
     *
     * @param studentName The inputted student name.
     * @return boolean indicates whether the student person name is valid.
     */
    public static boolean checkValidStudentNameInput(String studentName) {
        boolean valid = RegexValidator.checkStringRegexFormat(studentName, STUDENT_NAME_REGEX);
        if (!valid) {
            System.out.println("Wrong format of name.");
        }
        return valid;
    }
}
