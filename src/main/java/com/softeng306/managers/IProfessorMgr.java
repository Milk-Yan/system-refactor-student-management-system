package com.softeng306.managers;

import com.softeng306.domain.exceptions.ProfessorNotFoundException;
import com.softeng306.domain.professor.IProfessor;

import java.util.List;

/**
 * Interface for defining professor operations.
 * Defines the responsibilities of what functions should be done with professors.
 */
public interface IProfessorMgr {
    /**
     * Returns the IDs of all professors in the department.
     *
     * @param departmentName The department the professors are in.
     * @return A list of all the IDs of the professors.
     */
    List<String> getAllProfIDInDepartment(String departmentName);

    /**
     * Gets professor from specified professor ID
     *
     * @param professorID The ID of the specified professor
     * @return the professor specified by ID
     * @throws ProfessorNotFoundException
     */
    IProfessor getProfessorFromID(String professorID) throws ProfessorNotFoundException;

    /**
     * Finds if a professor with the profID is in the system.
     * @param profID The professor ID we are searching for.
     * @return If the professor exists.
     */
    boolean checkProfessorExists(String profID);
}
