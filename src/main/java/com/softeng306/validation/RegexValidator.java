package com.softeng306.validation;

import java.util.regex.Pattern;

public class RegexValidator {
    /**
     * Checks whether the inputted string is in the correct format.
     * @param stringValue The string to check the format of.
     * @param regexFormat The regex format to use
     * @return boolean indicates whether the inputted stringValue is valid.
     */
    public static boolean checkStringRegexFormat(String stringValue, String regexFormat){
        return Pattern.compile(regexFormat).matcher(stringValue).matches();
    }
}
