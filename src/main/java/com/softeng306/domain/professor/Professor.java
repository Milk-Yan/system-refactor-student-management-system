package com.softeng306.domain.professor;

import com.softeng306.enums.Department;

/**
 * Represents a professor at school
 */

public class Professor {

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
     * @param professorId   the ID of the professor
     * @param name the name of the professor
     */
    public Professor(String professorId, String name) {
        this.professorId = professorId;
        this.name = name;
    }

    /**
     * Gets this professor's ID
     *
     * @return the ID of this professor
     */

    public String getProfessorId() {
        return professorId;
    }

    /**
     * Gets this professor's name
     *
     * @return the name of this professor
     */
    public String getName() {
        return name;
    }

    /**
     * Gets this professor's department
     *
     * @return the department of this professor
     */
    public Department getDepartment() {
        return department;
    }

    /**
     * Sets the department of the professor
     *
     * @param department this professor's department
     */
    public void setDepartment(Department department) {
        this.department = department;
    }

}
