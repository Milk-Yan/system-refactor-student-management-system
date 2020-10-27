package com.softeng306.enums;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public enum Department {
    ECSE("ECSE"), CS("CS"), ChemENG("ChemENG");

    private String menuStringFormat;

    Department(String menuStringFormat) { this.menuStringFormat = menuStringFormat; }

    /**
     * Displays a list of all the departments.
     */
    public static void printAllDepartment() {
        int index = 1;
        for (Department department : Department.values()) {
            System.out.println(index + ": " + department);
            index++;
        }

    }

    /**
     * Finds whether the string exists as a department type.
     */
    public static boolean contains(String possibleDepartment) {
        for (Department department: Department.values()) {
            if (department.toString().equals(possibleDepartment)) {
                return true;
            }
        }

        return false;
    }


    /**
     * Returns the string value of the enum type with all lower cases.
     */
    @Override
    public String toString() {
        return menuStringFormat;
    }

}
