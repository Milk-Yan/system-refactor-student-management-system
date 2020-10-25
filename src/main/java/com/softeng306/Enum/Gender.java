package com.softeng306.Enum;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public enum Gender {
    FEMALE, MALE, UNKNOWN;


    /**
     * Displays a list of all the genders.
     */
    public static void printAllGender() {
        int index = 1;
        for (Gender gender : Gender.values()) {
            System.out.println(index + ": " + gender);
            index++;
        }

    }


    /**
     * Gets all the genders as a list.
     *
     * @return a list of all the genders.
     */
    public static List<String> getAllGender() {
        Set<Gender> genderEnumSet = EnumSet.allOf(Gender.class);
        List<String> genderStringList = new ArrayList<>(0);
        for (Gender gender : genderEnumSet) {
            genderStringList.add(gender.toString());
        }
        return genderStringList;
    }

}
