package com.softeng306.managers;

import com.softeng306.domain.professor.Professor;
import com.softeng306.validation.DepartmentValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Manages all the professor related operations
 */
public class ProfessorMgr {
    /**
     * An array list of all the professors in this school.
     */
    public static ArrayList<Professor> professors = new ArrayList<Professor>(0);

    private static ProfessorMgr singleInstance = null;

    /**
     * Override default constructor to implement singleton pattern
     */
    private ProfessorMgr() {
    }

    /**
     * Return the ProfessorMgr singleton, if not initialised already, create an instance.
     *
     * @return ProfessorMgr the singleton instance
     */
    public static ProfessorMgr getInstance() {
        if (singleInstance == null) {
            singleInstance = new ProfessorMgr();
        }

        return singleInstance;
    }

    // TODO: fix name of this method

    /**
     * Displays all the professors in the inputted department.
     *
     * @param department The inputted department.
     * @param printOut   Represents whether print out the professor information or not
     * @return A list of all the names of professors in the inputted department or else null.
     */
    public List<String> printProfInDepartment(String department, boolean printOut) {
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
