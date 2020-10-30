package com.softeng306.managers;

import com.softeng306.domain.exceptions.ProfessorNotFoundException;
import com.softeng306.domain.professor.IProfessor;

import com.softeng306.fileprocessing.IFileProcessor;
import com.softeng306.fileprocessing.ProfessorFileProcessor;

import com.softeng306.enums.Department;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Concrete implementation of {@code IProfessorMgr}.
 * Manages the operations on professors within the academic institute.
 */
public class ProfessorMgr implements IProfessorMgr {
    /**
     * A list of all the professors in this school.
     */
    private List<IProfessor> professors;

    /**
     * Singleton instance of this professor manager.
     */
    private static IProfessorMgr singleInstance = null;

    /**
     * The file processor to use for persisting professor data.
     */
    private final IFileProcessor<IProfessor> professorFileProcessor;

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
    public static IProfessorMgr getInstance() {
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
                .filter(p -> department.equals(p.getDepartment()))
                .map(p -> p.getProfessorId())
                .collect(Collectors.toList());
    }

    @Override
    public IProfessor getProfessorFromID(String professorID) throws ProfessorNotFoundException {
        Optional<IProfessor> professor = professors
                .stream()
                .filter(p -> professorID.equals(p.getProfessorId()))
                .findFirst();

        if (!professor.isPresent()) {
            throw new ProfessorNotFoundException(professorID);
        }

        return professor.get();
    }

    @Override
    public boolean checkProfessorExists(String profID) {
        Optional<IProfessor> professor = professors.stream()
                .filter(p -> profID.equals(p.getProfessorId()))
                .findFirst();

        return professor.isPresent();
    }

}