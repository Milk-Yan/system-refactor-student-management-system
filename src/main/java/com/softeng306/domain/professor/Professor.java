package com.softeng306.domain.professor;

import com.softeng306.enums.Department;

/**
 * Represents a professor at school
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
     * Creates professor with professor ID and professor name.
     *
     * @param professorId the ID of the professor
     * @param name        the name of the professor
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
