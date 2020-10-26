package com.softeng306.validation;

import com.softeng306.enums.Department;
import com.softeng306.managers.CourseMgr;

import java.util.List;
import java.util.Scanner;

public class DepartmentValidator {

    private static Scanner scanner = new Scanner(System.in);

    /**
     * Prompts the user to input an existing department.
     *
     * @return the inputted department.
     */
    public static String checkCourseDepartmentExists() {
        String courseDepartment;
        while (true) {
            System.out.println("Which department's courses are you interested? (-h to print all the departments)");
            courseDepartment = scanner.nextLine();
            while ("-h".equals(courseDepartment)) {
                Department.printAllDepartment();
                courseDepartment = scanner.nextLine();
            }
            if (Department.contains(courseDepartment)) {
                List<String> validCourseString;
                validCourseString = CourseMgr.getInstance().getCourseIdsInDepartment(Department.valueOf(courseDepartment));
                if (validCourseString.size() == 0) {
                    System.out.println("Invalid choice of department.");
                } else {
                    break;
                }
            } else {
                System.out.println("The department is invalid. Please re-enter.");
            }
        }
        return courseDepartment;
    }
}
