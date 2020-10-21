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


    /**
     * Gets all the genders as an array list.
     *
     * @return an array list of all the genders.
     */
    public static ArrayList<String> getAllGender() {
        Set<Gender> genderEnumSet = EnumSet.allOf(Gender.class);
        ArrayList<String> genderStringList = new ArrayList<String>(0);
        Iterator iter = genderEnumSet.iterator();
        while (iter.hasNext()) {
            genderStringList.add(iter.next().toString());
        }
        return genderStringList;
    }

}
