package com.softeng306.validation;

import com.softeng306.enums.Department;
import com.softeng306.managers.CourseMgr;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

public class DepartmentValidator {
    private static Scanner scanner = new Scanner(System.in);
    private static PrintStream originalStream = System.out;
    private static PrintStream dummyStream = new PrintStream(new OutputStream() {
        public void write(int b) {
            // NO-OP
        }
    });

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
            if (checkDepartmentValidation(courseDepartment)) {
                List<String> validCourseString;
                validCourseString = CourseMgr.getInstance().getCourseIdsInDepartment(courseDepartment);
                if (validCourseString.size() == 0) {
                    System.out.println("Invalid choice of department.");
                } else {
                    break;
                }
            }
        }
        return courseDepartment;
    }
}
