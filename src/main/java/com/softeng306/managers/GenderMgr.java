package com.softeng306.managers;

import com.softeng306.enums.Gender;

public class GenderMgr {
    /**
     * Displays a list of all the genders.
     */
    public void printAllGender() {
        int index = 1;
        for (Gender gender : Gender.values()) {
            System.out.println(index + ": " + gender);
            index++;
        }

    }


    /**
     * Finds whether the string exists as a gender type.
     */
    public boolean contains(String possibleGender) {
        for (Gender gender : Gender.values()) {
            if (gender.toString().equals(possibleGender)) {
                return true;
            }
        }

        return false;
    }
}
