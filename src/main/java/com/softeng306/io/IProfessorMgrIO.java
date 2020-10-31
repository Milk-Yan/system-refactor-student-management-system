package com.softeng306.io;

import java.util.List;

/**
 * Interface for ProfessorMgrIO.
 * CourseMgrIO acts as a central module to manipulate professors.
 * Provides methods to manipulate professors.
 */
public interface IProfessorMgrIO {

    /**
     * Displays all the professors in the inputted department.
     *
     * @param professors The list of professor IDs.
     * @return A list of all the IDs of professors in the inputted department or else null.
     */
    void printAllProfIDsInDepartment(List<String> professors);
}
