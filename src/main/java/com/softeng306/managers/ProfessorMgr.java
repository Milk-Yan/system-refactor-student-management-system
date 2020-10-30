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
public class ProfessorMgr implements IProfessorMgr {
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
     * Return the IProfessorMgr singleton, if not initialised already, create an instance.
     *
     * @return IProfessorMgr the singleton instance
     */
    public static ProfessorMgr getInstance() {
        if (singleInstance == null) {
            singleInstance = new ProfessorMgr();
        }

        return singleInstance;
    }

    @Override
    public List<String> getAllProfIDInDepartment(String departmentName) {
        Department department = Department.valueOf(departmentName);
        return professors
                .stream()
                .filter(p -> department.equals(p.getProfDepartment()))
                .map(p -> p.getProfID())
                .collect(Collectors.toList());
    }

    @Override
    public Professor getProfessorFromID(String professorID) throws ProfessorNotFoundException {
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

    @Override
    public boolean checkProfessorExists(String profID){
        Optional<Professor> professor = professors.stream()
                .filter(p -> profID.equals(p.getProfID()))
                .findFirst();

        return professor.isPresent();
    }

    /**
     * Return the list of all professors in the system.
     *
     * @return An list of all professors.
     */
    private List<Professor> getProfessors() {
        return professors;
    }

}