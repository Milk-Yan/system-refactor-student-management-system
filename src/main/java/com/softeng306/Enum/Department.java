package com.softeng306.Enum;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public enum Department {
    ECSE, CS, ChemENG;


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
     * Gets all the departments as a list.
     *
     * @return a list of all the departments.
     */
    public static List<String> getAllDepartment() {
        Set<Department> departmentEnumSet = EnumSet.allOf(Department.class);
        List<String> departmentStringList = new ArrayList<>(0);
        for (Department department : departmentEnumSet) {
            departmentStringList.add(department.toString());
        }
        return departmentStringList;

    }
}
