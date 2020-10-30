package com.softeng306.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * Enumerated type for genders that a student can identify as.
 */
public enum Gender {
    FEMALE, MALE, UNKNOWN;

    /**
     * Finds whether the string exists as a gender type.
     */
    public static boolean contains(String possibleGender) {
        for (Gender gender : Gender.values()) {
            if (gender.toString().equals(possibleGender)) {
                return true;
            }
        }

        return false;
    }

    /**
     * @return A list of the names of all the genders.
     */
    public static List<String> getListOfAllGenderNames(){
        List<String> allGenderNames = new ArrayList<>();

        for(Gender gender: Gender.values()){
            allGenderNames.add(gender.toString());
        }

        return allGenderNames;
    }

}
