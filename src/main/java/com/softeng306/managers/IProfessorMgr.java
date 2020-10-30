package com.softeng306.managers;

import com.softeng306.domain.exceptions.ProfessorNotFoundException;
import com.softeng306.domain.professor.Professor;

import java.util.List;

public interface IProfessorMgr {
    /**
     * Returns the IDs of all professors in the department.
     *
     * @param departmentName The department the professors are in.
     * @return A list of all the IDs of the professors.
     */
    List<String> getAllProfIDInDepartment(String departmentName);

    Professor getProfessorFromID(String professorID) throws ProfessorNotFoundException;

    /**
     * Finds if a professor with the profID is in the system.
     * @param profID The professor ID we are searching for.
     * @return If the professor exists.
     */
    boolean checkProfessorExists(String profID);
}
