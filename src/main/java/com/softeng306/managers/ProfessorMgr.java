package com.softeng306.managers;

import com.softeng306.Enum.Department;
import com.softeng306.domain.professor.Professor;
import com.softeng306.io.HelpInfoMgr;
import com.softeng306.validation.DepartmentValidator;
import com.softeng306.validation.ProfessorValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Manages all the professor related operations
 */
public class ProfessorMgr {
    private Scanner scanner = new Scanner(System.in);
    /**
     * An array list of all the professors in this school.
     */
    public static ArrayList<Professor> professors = new ArrayList<Professor>(0);

    /**
     * Adds a professor.
     *
     * @return a newly added professor
     */
    public Professor addProfessor() {
        String department, profID;
        while (true) {
            System.out.println("Give this professor an ID: ");
            profID = scanner.nextLine();
            if (ProfessorValidator.checkValidProfIDInput(profID)) {
                if (ProfessorValidator.checkProfExists(profID) == null) {
                    break;
                }
            }
        }

        String profName;
        while (true) {
            System.out.println("Enter the professor's name: ");
            profName = scanner.nextLine();
            if (ProfessorValidator.checkValidProfessorNameInput(profName)) {
                break;
            }
        }

        Professor professor = new Professor(profID, profName);
        while (true) {
            System.out.println("Enter professor's Department: ");
            System.out.println("Enter -h to print all the departments.");
            department = scanner.nextLine();
            while (department.equals("-h")) {
                Department.getAllDepartment();
                department = scanner.nextLine();
            }

            if (DepartmentValidator.checkDepartmentValidation(department)) {
                professor.setProfDepartment(department);
                break;
            }
        }


        return professor;
    }

    /**
     * Displays all the professors in the inputted department.
     *
     * @param department The inputted department.
     * @param printOut   Represents whether print out the professor information or not
     * @return A list of all the names of professors in the inputted department or else null.
     */
    public static List<String> printProfInDepartment(String department, boolean printOut) {
        if (DepartmentValidator.checkDepartmentValidation(department)) {
            List<String> validProfString = ProfessorMgr.professors.stream().filter(p -> String.valueOf(department).equals(p.getProfDepartment())).map(p -> p.getProfID()).collect(Collectors.toList());

            if (printOut) {
                validProfString.forEach(System.out::println);
            }
            return validProfString;
        }
        System.out.println("None.");
        return null;

    }

}
