package com.softeng306.managers;

import com.softeng306.enums.Department;

import java.util.ArrayList;
import java.util.List;

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

    public static List<String> getListOfDepartments(){
        List<String> listDepartmentStrings = new ArrayList<>();
        for(Department department : Department.values()){
            listDepartmentStrings.add(department.toString());
        }
        return listDepartmentStrings;
    }

}
