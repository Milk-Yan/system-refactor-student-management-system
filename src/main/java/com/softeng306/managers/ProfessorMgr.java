package com.softeng306.managers;

import com.softeng306.domain.exceptions.ProfessorNotFoundException;
import com.softeng306.domain.professor.Professor;

import com.softeng306.fileprocessing.IFileProcessor;
import com.softeng306.fileprocessing.ProfessorFileProcessor;

import com.softeng306.enums.Department;

import java.util.List;
import java.util.Optional;
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

    private final IFileProcessor<Professor> professorFileProcessor;

    /**
     * Override default constructor to implement singleton pattern
     */
    private ProfessorMgr() {
        professorFileProcessor = new ProfessorFileProcessor();
        professors = professorFileProcessor.loadFile();
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

    public static Professor getProfessorFromID(String professorID) throws ProfessorNotFoundException {
        Optional<Professor> professor = ProfessorMgr
                .getInstance()
                .getProfessors()
                .stream()
                .filter(p -> professorID.equals(p.getProfID()))
                .findFirst();

        if (!professor.isPresent()) {
            throw new ProfessorNotFoundException(professorID);
        }

        return professor.get();
    }

    /**
     * Finds if a professor with the profID is in the system.
     * @param profID The professor ID we are searching for.
     * @return If the professor exists.
     */
    public boolean checkProfessorExists(String profID){
        Optional<Professor> professor = professors
                .stream()
                .filter(p -> profID.equals(p.getProfID()))
                .findFirst();

        return professor.isPresent();
    }

}