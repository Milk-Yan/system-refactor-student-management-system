package com.softeng306.io;

import java.util.List;

public interface IProfessorMgrIO {
    /**
     * Displays all the professors in the inputted department.
     *
     * @param professors The list of professor IDs.
     * @return A list of all the IDs of professors in the inputted department or else null.
     */
    void printAllProfIDsInDepartment(List<String> professors);
}
