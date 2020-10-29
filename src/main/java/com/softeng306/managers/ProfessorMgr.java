package com.softeng306.managers;

import com.softeng306.domain.professor.Professor;
import com.softeng306.enums.Department;
import com.softeng306.io.FILEMgr;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Manages all the professor related operations
 */
public class ProfessorMgr {
    /**
     * A list of all the professors in this school.
     */
    private List<Professor> professors;

    private static ProfessorMgr singleInstance = null;

    /**
     * Override default constructor to implement singleton pattern
     */
    private ProfessorMgr(List<Professor> professors) {
        this.professors = professors;
    }

    /**
     * Return the ProfessorMgr singleton, if not initialised already, create an instance.
     *
     * @return ProfessorMgr the singleton instance
     */
    public static ProfessorMgr getInstance() {
        if (singleInstance == null) {
            singleInstance = new ProfessorMgr(FILEMgr.loadProfessors());
        }

        return singleInstance;
    }

    /**
     * Returns the IDs of all professors in the department.
     *
     * @param departmentName The department the professors are in.
     * @return A list of all the IDs of the professors.
     */
    public List<String> getAllProfIDInDepartment(String departmentName) {
        Department department = Department.valueOf(departmentName);
        return professors
                .stream()
                .filter(p -> department.equals(p.getProfDepartment()))
                .map(p -> p.getProfID())
                .collect(Collectors.toList());
    }

    /**
     * Return the list of all professors in the system.
     *
     * @return An list of all professors.
     */
    public List<Professor> getProfessors() {
        return professors;
    }


    public static Professor getProfessorFromID(String profID) {
        List<Professor> anyProf = ProfessorMgr.getInstance().getProfessors().stream().filter(p -> profID.equals(p.getProfID())).collect(Collectors.toList());
        if (anyProf.isEmpty()) {
            return null;
        }
        return anyProf.get(0);
    }


}
