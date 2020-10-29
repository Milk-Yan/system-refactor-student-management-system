package com.softeng306.validation;

import java.util.regex.Pattern;

public class RegexValidator {

    private static final String COURSE_ID_REGEX = "^[A-Z]{2}[0-9]{3,4}$";

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
}
