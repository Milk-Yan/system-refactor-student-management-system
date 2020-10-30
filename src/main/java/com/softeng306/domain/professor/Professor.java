package com.softeng306.domain.professor;

import com.softeng306.enums.Department;

/**
 * Represents a professor at school
 */

public class Professor implements IProfessor {

    private String profID;
    private String profName;

    private Department profDepartment;

    /**
     * Default constructor. Required for Jackson serialization.
     */
    public Professor() {

    }

    /**
     * Creates professor with professor ID and professor name.
     *
     * @param profID   the ID of the professor
     * @param profName the name of the professor
     */
    public Professor(String profID, String profName) {
        this.profID = profID;
        this.profName = profName;
    }

    @Override
    public String getProfID() {
        return profID;
    }

    @Override
    public String getProfName() {
        return profName;
    }

    @Override
    public Department getProfDepartment() {
        return profDepartment;
    }

    @Override
    public void setProfDepartment(Department profDepartment) {
        this.profDepartment = profDepartment;
    }

}
