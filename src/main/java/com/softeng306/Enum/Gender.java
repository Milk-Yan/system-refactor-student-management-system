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
     * Finds whether the string exists as a gender type.
     */
    public static boolean contains(String possibleGender) {
        for (Gender gender: Gender.values()) {
            if (gender.toString().equals(possibleGender)) {
                return true;
            }
        }

        return false;
    }

}
