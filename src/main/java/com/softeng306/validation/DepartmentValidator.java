package com.softeng306.validation;

import com.softeng306.enums.Department;

public class DepartmentValidator {

    /**
     * Checks whether the inputted department is valid.
     *
     * @param department The inputted department.
     * @return boolean indicates whether the inputted department is valid.
     */
    public static boolean checkDepartmentValidation(String department) {
        if (Department.getAllDepartment().contains(department)) {
            return true;
        }
        System.out.println("The department is invalid. Please re-enter.");
        return false;
    }

}
