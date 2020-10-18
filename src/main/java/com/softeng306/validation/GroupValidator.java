package com.softeng306.validation;

public class GroupValidator {
    private static final String GROUP_NAME_REGEX = "^[a-zA-Z0-9]+$";

    /**
     * Checks whether the inputted group name is in the correct format.
     * @param groupName The inputted group name.
     * @return boolean indicates whether the inputted group name is valid.
     */
    public static boolean checkValidGroupNameInput(String groupName){
        return RegexValidator.checkStringRegexFormat(groupName, GROUP_NAME_REGEX);
    }
}
