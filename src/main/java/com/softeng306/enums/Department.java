package com.softeng306.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * Enumerated type to represent the different departments within the academic institute.
 */
public enum Department {
    ECSE, CS, CHEM_ENG;

    /**
     * Finds whether the string exists as a department type.
     */
    public static boolean contains(String possibleDepartment) {
        for (Department department : Department.values()) {
            if (department.toString().equals(possibleDepartment)) {
                return true;
            }
        }
        
        return false;
    }

    /**
     * @return A list of the names of all the departments.
     */
    public static List<String> getListOfAllDepartmentNames(){
        List<String> allDepartmentNames = new ArrayList<>();

        for(Department department : Department.values()){
            allDepartmentNames.add(department.toString());
        }

        return allDepartmentNames;
    }
}
