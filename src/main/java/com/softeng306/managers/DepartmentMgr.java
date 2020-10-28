package com.softeng306.managers;

import com.softeng306.enums.Department;

public class DepartmentMgr {

    /**
     * Displays a list of all the departments.
     */
    public void printAllDepartment() {
        int index = 1;
        for (Department department : Department.values()) {
            System.out.println(index + ": " + department);
            index++;
        }

    }

    /**
     * Finds whether the string exists as a department type.
     */
    public boolean contains(String possibleDepartment) {
        for (Department department : Department.values()) {
            if (department.toString().equals(possibleDepartment)) {
                return true;
            }
        }

        return false;
    }
}
