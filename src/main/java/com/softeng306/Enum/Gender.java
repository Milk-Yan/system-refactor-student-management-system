package com.softeng306.Enum;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
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

}
