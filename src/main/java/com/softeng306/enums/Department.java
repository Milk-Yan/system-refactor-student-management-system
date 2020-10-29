package com.softeng306.enums;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public enum Department {
    ECSE, CS, CHEM_ENG;

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

    public static List<String> getListOfDepartments(){
        List<String> listDepartmentStrings = new ArrayList<>();
        for(Department department : Department.values()){
            listDepartmentStrings.add(department.toString());
        }
        return listDepartmentStrings;
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

}
