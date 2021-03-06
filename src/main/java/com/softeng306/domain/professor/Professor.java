package com.softeng306.domain.professor;

import com.softeng306.enums.Department;

/**
 * Concrete implementation of a professor.
 * This class implements {@code IProfessor}.
 */
public class Professor implements IProfessor {

    private String professorId;
    private String name;
    private Department department;

    /**
     * Default constructor. Required for Jackson serialization.
     */
    public Professor() {

    }

    /**
     * Creates professor with professor ID and name.
     *
     * @param professorId The ID of the professor.
     * @param name        The name of the professor
     */
    public Professor(String professorId, String name) {
        this.professorId = professorId;
        this.name = name;
    }

    @Override
    public String getProfessorId() {
        return professorId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Department getDepartment() {
        return department;
    }

}
